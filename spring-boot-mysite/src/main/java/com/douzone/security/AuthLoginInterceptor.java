package com.douzone.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;

@Component
public class AuthLoginInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
//		ApplicationContext ac = 
//				WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
//		UserService userService = ac.getBean( UserService.class );
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		UserVo userVo = userService.getUser(email, password);
		System.out.println(userVo);
		if(userVo == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		// 로그인 처리
		HttpSession session = request.getSession(true);		
		session.setAttribute("authuser", userVo);
		
		response.sendRedirect(request.getContextPath() + "/");
		
		return false;
	}

}
