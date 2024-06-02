package com.hmdp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hmdp.dto.LoginFormDTO;
import com.hmdp.dto.Result;
import com.hmdp.entity.User;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
public interface IUserService extends IService<User> {
	/**
	 * 根据手机号查找用户
	 * @param phone
	 * @return
	 */
	User getUserByPhone(String phone);

	/**
	 * 给手机号发送验证码
	 * @param phone
	 * @param session
	 * @return
	 */
	Result sendCode(String phone, HttpSession session);

	/**
	 * 登录
	 * @param loginForm
	 * @param session
	 * @return
	 */
	Result login(LoginFormDTO loginForm, HttpSession session);

	/**
	 * 签到
	 * @return
	 */
	Result sign();

	/**
	 * 连续签到统计
	 * @return
	 */
	Result signCount();
}
