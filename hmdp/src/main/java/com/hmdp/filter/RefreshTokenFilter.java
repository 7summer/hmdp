package com.hmdp.filter;

import cn.hutool.core.bean.BeanUtil;
import com.hmdp.dto.UserDTO;
import com.hmdp.constant.RedisConstants;
import com.hmdp.utils.UserHolder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RefreshTokenFilter implements HandlerInterceptor {
	private StringRedisTemplate stringRedisTemplate;

	public RefreshTokenFilter(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}

	/**
	 * 请求前拦截
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 获取请求头的token
		String token = request.getHeader("authorization");
		if (token == null || token.length() == 0) {
			return true;
		}

		// 使用token从redis中获取用户
		String tokenKey = RedisConstants.LOGIN_USER_KEY + token;
		Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(tokenKey);
		if (userMap == null || userMap.isEmpty()) {
			return true;
		}

		UserDTO user = BeanUtil.fillBeanWithMap(userMap, new UserDTO(), false);
		if (user == null) {
			return true;
		}

		// 保存用户信息到TreadLocal
		// TreadLocal是线程安全的
		UserHolder.saveUser(user);

		// 刷新token有效期
		stringRedisTemplate.expire(tokenKey, RedisConstants.LOGIN_USER_TTL, TimeUnit.MINUTES);

		// 放行
		return true;
	}

	/**
	 * 渲染后的操作（清除资源）
	 * @param request
	 * @param response
	 * @param handler
	 * @param ex
	 * @throws Exception
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		UserHolder.removeUser();
	}
}
