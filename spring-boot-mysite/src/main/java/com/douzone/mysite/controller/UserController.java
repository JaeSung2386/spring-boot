package com.douzone.mysite.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;
import com.douzone.security.Auth;
import com.douzone.security.AuthUser;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/auth", method=RequestMethod.POST)
	public void auth() {
		
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public void logout() {
		
	}
	// 회원가입
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(@ModelAttribute UserVo userVo) {
		return "user/join";
	}

	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(
			@ModelAttribute @Valid UserVo userVo,
			BindingResult result,
			Model model) {
		System.out.println(result.hasErrors());
		// result의 결과를 check
		if(result.hasErrors()) {
			// 유효성이 하나라도 잘못되었을 경우
//			List<ObjectError> list = result.getAllErrors();
//			for(ObjectError error :list) {
//				System.out.println(error);
//			}
			model.addAllAttributes(result.getModel());
			return "user/join";
		}
		userService.join(userVo);
		return "redirect:/user/joinsuccess";
	}
	
	// 회원가입 성공
	@RequestMapping("/joinsuccess")
	public String joinsuccess() {
		return "user/joinsuccess";
	}
	
	// 로그인
	@RequestMapping("/login")
	public String login() {
		System.out.println("로그인 컨트롤러");
		return "user/login";
	}
	
	// 회원 수정
	@Auth
	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public String modify(
			@AuthUser UserVo authUser,
			Model model	) {
		UserVo userVo = userService.getUser(authUser.getNo());
		model.addAttribute("vo", userVo);
		return "user/modify";
	}
	
	@Auth
	@RequestMapping(value="/modify", method = RequestMethod.POST)
	public String modify(
			@AuthUser UserVo authUser,
			@ModelAttribute UserVo userVo) {
		userVo.setNo(authUser.getNo());
		userService.modify(userVo);
		
		authUser.setName(userVo.getName());
		return "redirect:/user/modify?result=success";
	}
	
	// 회원 탈퇴
	@Auth
	@RequestMapping(value="/drop", method=RequestMethod.GET)
	public String drop(
			@AuthUser UserVo authUser,
			Model model	) {
		UserVo userVo = userService.getUser(authUser.getNo());
		model.addAttribute("vo", userVo);
		return "user/drop";
	}
	
	@Auth
	@RequestMapping(value="/drop", method = RequestMethod.POST)
	public String drop(
			@AuthUser UserVo authUser,
			@ModelAttribute UserVo userVo) {
		userVo.setNo(authUser.getNo());
		System.out.println(userVo);
		userService.drop(userVo);
		
		return "redirect:/user/drop?result=success";
	}	
//	@ExceptionHandler(UserDaoException.class)
//	public String handleUserDaoException() {
//		// 1. 로깅 작업
//		// 2. 페이지 전환(사과 페이지)
//		return "error/exception";
//	}
}
