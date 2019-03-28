package com.douzone.emaillist.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.emaillist.dao.EmaillistDao;
import com.douzone.emaillist.vo.EmaillistVo;

@Controller
public class EmaillistController {
	@Autowired
	private EmaillistDao emaillistDao;
	
//	@RequestMapping("")
//	public ModelAndView list() {
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("list", emaillistDao.getList());
//		mav.setViewName("/WEB-INF/views/list.jsp");
//		return mav;
//	}
	
	@RequestMapping({"", "/list"})
	public String list(Model model) {
		model.addAttribute("list", emaillistDao.getList());
		return "list";
	}
	
	@RequestMapping("/form")
	public String form() {
		return "form";
	}
	
	@RequestMapping(value="/add", method = RequestMethod.POST)
	public String add(EmaillistVo emaillistVo) {
		System.out.println(emaillistVo.getFirstName());
		System.out.println(emaillistVo.getLastName());
		System.out.println(emaillistVo.getEmail());
		emaillistDao.insert(emaillistVo);
		return "redirect:/";
	}
}