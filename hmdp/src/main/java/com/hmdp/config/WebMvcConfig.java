package com.hmdp.config;

import com.hmdp.filter.LoginFilter;
import com.hmdp.filter.RefreshTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 添加登录过滤器
		registry.addInterceptor(new LoginFilter())
				.excludePathPatterns(
						"/shop/**",
						"/voucher/**",
						"/shop-type/**",
						"/blog/hot",
						"/user/code",
						"/user/login",
						"/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**",
						"/api", "/api-docs", "/api-docs/**", "/doc.html/**"
				).order(1);
		// 添加RefreshTokenFilter过滤器
		registry.addInterceptor(new RefreshTokenFilter(stringRedisTemplate)).addPathPatterns("/**")
				.excludePathPatterns(
						"/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**",
						"/api", "/api-docs", "/api-docs/**", "/doc.html/**"
				).order(0);
	}
}
