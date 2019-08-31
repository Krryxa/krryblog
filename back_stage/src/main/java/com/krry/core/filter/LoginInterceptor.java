package com.krry.core.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.krry.entity.ResponseVal;
import com.krry.entity.User;

/**
 * 登陆拦截器
 * LoginInterceptor
 * @author krry
 * @version 1.0.0
 *
 */
public class LoginInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 获取 session 的 user
		User user = (User) request.getSession().getAttribute("user");
		String requestType = request.getHeader("X-Requested-With");

		// 如果 user 为空，说明没有登陆，则重定向到首页，返回 false
		if(user == null) {
			// 如果是 ajax 输入标志位
			if (requestType != null && requestType.equals("XMLHttpRequest")) {
				// 用 response 的流进行输出
				response.setContentType("application/json;charset=UTF-8");
				ResponseVal res = new ResponseVal();
				res.setMessage("请先登录");
				res.setCode(401);
				response.getWriter().print(res);
			} else {
				// 定位到登录页
				response.sendRedirect("/backstage");
			}
			return false;
		} else {
			return true;
		}
	}
	
	

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}
}
