package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.douzone.dto.JSONResult;
import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.UserVo;

@Controller
@SessionAttributes("siteVo")
public class MainController {
	
	@Autowired
	private SiteService siteService;
	
	// 무엇을 return할 것인가? 1.ModelAndView 2.String 3.Object
	// 1, 2는 viewname
	// ViewResolver 씨바새기 존나 어렵
	// ViewResolver한테 viewname을 전달해서 view를 return해달라고 요청함 
	// viewResolver은 view를 전달하기 위해서viewrender을 호출함
	// viewrender은 model이나 ModelAndView를 인수로 받음. - forwarding 작업이 일어나는 곳
	@RequestMapping({"", "/main"})
	public String main(Model model) {
		model.addAttribute("siteVo", siteService.get());
		// return "/WEB-INF/views/main/index.jsp";
		System.out.println("된다.");
		return "/main/index";
	}
	
	// return되는 String을 ResponseBody에 붙인다.
	@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
		return "<h1> 안녕하세요. </h1>";
	}
	
	@ResponseBody
	@RequestMapping("/hello2")
	public JSONResult hello2() {
		return JSONResult.success(new UserVo());
	}
}
