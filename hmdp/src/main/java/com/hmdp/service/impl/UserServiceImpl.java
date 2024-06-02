package com.hmdp.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.dto.LoginFormDTO;
import com.hmdp.dto.Result;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.User;
import com.hmdp.mapper.UserMapper;
import com.hmdp.service.IUserService;
import com.hmdp.constant.RedisConstants;
import com.hmdp.utils.RegexUtils;
import com.hmdp.constant.SystemConstants;
import com.hmdp.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
	@Resource
	private UserMapper userMapper;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 通过手机号返回用户信息
	 * @param phone
	 * @return
	 */
	@Override
	public User getUserByPhone(String phone) {
		QueryWrapper wrapper = new QueryWrapper();
		wrapper.eq("phone", phone);

		return userMapper.selectOne(wrapper);
	}

	/**
	 * 发送验证码
	 * @param phone
	 * @param session
	 * @return
	 */
	@Override
	public Result sendCode(String phone, HttpSession session) {
		// 验证手机号合法
		if (phone == null || phone.length() == 0) {
			return Result.fail("手机号格式错误");
		}
		if (RegexUtils.isPhoneInvalid(phone)) {
			return Result.fail("手机号格式错误");
		}

		// 生成验证码，保存到session
		String veriyCode = RandomUtil.randomString(6);
		session.setAttribute(SystemConstants.VERIFYCODE, veriyCode);

		// 验证码保存到redis, 并设置过期时间
		ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
		operations.set(RedisConstants.LOGIN_CODE_KEY + phone, veriyCode, RedisConstants.LOGIN_CODE_TTL, TimeUnit.MINUTES);

		// 发送验证码
		log.info("已发送验证码:" + veriyCode);
		return Result.ok();
	}

	/**
	 * 登录
	 * @param loginForm
	 * @param session
	 * @return
	 */
	@Override
	public Result login(LoginFormDTO loginForm, HttpSession session) {
		if (loginForm == null) {
			return Result.fail("登录失败");
		}

		// 校验手机号合法
		String phone = loginForm.getPhone();
		if (phone == null || phone.length() == 0) {
			return Result.fail("手机号为空");
		}
		if (RegexUtils.isPhoneInvalid(phone)) {
			return Result.fail("手机号不合法");
		}

		// 校验验证码合法
		String code = loginForm.getCode();
		if (code == null || code.length() == 0) {
			return Result.fail("验证码为空");
		}

		// 校验验证码
		String codeKey = RedisConstants.LOGIN_CODE_KEY + phone;
		String verifyCode = stringRedisTemplate.opsForValue().get(codeKey);
		if (verifyCode == null || verifyCode.length() == 0) {
			return Result.fail("手机号前后不一致");
		}
		if (!verifyCode.equals(code)) {
			return Result.fail("验证码错误");
		}
		// 使验证码过期
		stringRedisTemplate.expire(codeKey, -2, TimeUnit.MILLISECONDS);

		// 如果用户存在直接登录，用户不存在直接注册用户
		User user = getUserByPhone(phone);
		if (user == null) {
			user = createUserWithPhone(phone);
			userMapper.insert(user);
		}

		// 随机生成token，作为登录令牌
		String token = UUID.randomUUID().toString().replace("-", "");

		// 保存用户信息到redis
		String tokenKey = RedisConstants.LOGIN_USER_KEY + token;
		Map<String, String> userMap = getUserMap(getSafeUser(user));

		stringRedisTemplate.opsForHash().putAll(tokenKey , userMap);
		stringRedisTemplate.expire(tokenKey, RedisConstants.LOGIN_USER_TTL, TimeUnit.MINUTES);

		return Result.ok(token);
	}

	/**
	 * 签到
	 * @return
	 */
	@Override
	public Result sign() {
		// 获取用户id
		Long userId = UserHolder.getUser().getId();
		// 获取日期
		LocalDateTime localDateTime = LocalDateTime.now();
		String keySuffix = localDateTime.format(DateTimeFormatter.ofPattern("yyyy:MM"));
		// 这个月的第几天
		int dayOfMonth = localDateTime.getDayOfMonth();

		// 签到key
		String key = RedisConstants.USER_SIGN_KEY + userId + ":" + keySuffix;
		// 签到
		ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
		operations.setBit(key, dayOfMonth-1, true);

		return Result.ok(true);
	}

	/**
	 * 连续签到统计
	 * @return
	 */
	@Override
	public Result signCount() {
		// 获取用户id
		Long userId = UserHolder.getUser().getId();
		// 获取日期
		LocalDateTime localDateTime = LocalDateTime.now();
		String keySuffix = localDateTime.format(DateTimeFormatter.ofPattern("yyyy:MM"));
		// 这个月的第几天
		int dayOfMonth = localDateTime.getDayOfMonth();

		// 签到key
		String key = RedisConstants.USER_SIGN_KEY + userId + ":" + keySuffix;
		// 连续签到统计
		ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
		// 获取该用户这个月的签到情况（十进制）
		// 签到情况使用BitMap存储
		// 0代表未签到 1代表已签到
		List<Long> longs = operations.bitField(key,
				BitFieldSubCommands.create()
						// u(dayOfMonth)
						// 1-dayOfMonth号的签到情况
						.get(BitFieldSubCommands.BitFieldType.unsigned(dayOfMonth))
						// 不偏移
						.valueAt(0));
		if (longs == null || longs.isEmpty()) {
			return Result.ok(0);
		}

		Long signDecimal = longs.get(0);
		if (signDecimal == 0) {
			return Result.ok(0);
		}

		// dayOfMonth-1的连续签到情况
		// 如果中间出现断签的情况，直接终止统计
		int count = 0;
		while (signDecimal > 0) {
			if ((signDecimal & 1) == 1) {
				count++;
			} else {
				break;
			}

			signDecimal >>= 1;
		}

		return Result.ok(count);
	}

	/**
	 * 根据手机号创建User对象
	 * @param phone
	 * @return
	 */
	private User createUserWithPhone(String phone) {
		User user = new User();
		user.setPhone(phone);
		user.setNickName(SystemConstants.USER_NICK_NAME_PREFIX + RandomUtil.randomString(10));

		return user;
	}

	/**
	 * 用户信息脱敏
	 * @param user
	 * @return
	 */
	private UserDTO getSafeUser(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setNickName(user.getNickName());
		userDTO.setIcon(user.getIcon());

		return userDTO;
	}

	/**
	 * 将UserDTO映射为Map
	 * @param userDTO
	 * @return
	 */
	private Map<String, String> getUserMap(UserDTO userDTO) {
		Map<String, String> userMap = new HashMap<>();

		userMap.put("id", String.valueOf(userDTO.getId()));
		userMap.put("nickName", userDTO.getNickName());
		userMap.put("icon", userDTO.getIcon());

		return userMap;
	}
}

