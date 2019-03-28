package com.douzone.mysite.exception;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.douzone.dto.JSONResult;
import com.fasterxml.jackson.databind.ObjectMapper;

// 모든 컨트롤러의 메소드가 exception이 발생했을 때, 동작하는 클래스
// 예외가 동일하므로 전역으로 예외를 처리하는 클래스를 만듦
// AOP 코드가 작동하여 컨트롤러 메소드가 작동하는 시점을 기록해놓는다.
// @ControllerAdvice도 스캐닝 동작을 하므로 스캐닝이 이루어지게 해야한다.
@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Log LOG = LogFactory.getLog(GlobalExceptionHandler.class);
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public void handlerNoHandlerFoundException(
			HttpServletRequest request,
			HttpServletResponse response,
			NoHandlerFoundException ex) throws ServletException, IOException {
		System.out.println("handlerNoHandlerFoundException");
		request.
		getRequestDispatcher("/WEB-INF/views/error/404.jsp").
		forward(request, response);
	}
	
	@ExceptionHandler(Exception.class)
	public void handlerException(
			HttpServletRequest request,
			HttpServletResponse response,
			Exception e) throws Exception {
		// 1. 로깅 작업
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		
//		// 2.시스템 오류 안내 페이지 전환
//		ModelAndView mav = new ModelAndView();
//		// 에러 로그를 화면에 출력
//		mav.addObject("uri", request.getRequestURI());
//		mav.addObject("errors", errors.toString());
//		
//		mav.setViewName("error/exception");
//		return mav;
		
		String accept = request.getHeader("accept");
		// 정규 표현식으로 앞, 뒤에 어떠한 문자열이 있더라도 application/json이 있다면 json으로 응답
		if(accept.matches(".*application/json.*")) {
			response.setStatus(HttpServletResponse.SC_OK);
			
			// byte로 출력
			OutputStream out = response.getOutputStream();
			out.write(new ObjectMapper().writeValueAsString(JSONResult.fail(errors.toString())).getBytes("utf-8"));
			out.flush();
			out.close();
		}
		// html로 응답
		else {
			// uri는 어디에서 에러가 발생했는지
			request.setAttribute("uri", request.getRequestURI());
			request.setAttribute("exception", errors.toString());
			request.getRequestDispatcher("/WEB-INF/views/error/exception.jsp").forward(request, response);
		}
	}
}