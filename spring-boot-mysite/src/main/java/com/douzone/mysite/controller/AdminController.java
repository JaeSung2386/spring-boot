package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.service.FileuploadService;
import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.SiteVo;
import com.douzone.security.Auth;
import com.douzone.security.Auth.Role;

@Auth(Role.ADMIN)
@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private FileuploadService fileuploadService;
	
	@Autowired
	private SiteService siteService;
	
	@RequestMapping({"", "/main"})
	public String main(Model model) {
		model.addAttribute("siteVo", siteService.get());
		return "admin/main";
	}
	
	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}
	
	@RequestMapping(value="/main/update", method=RequestMethod.POST)
	public String update(
			@ModelAttribute SiteVo siteVo,
			@RequestParam(value="upload-profile") MultipartFile multipartFile) {
		System.out.println("AdminController update 실행");
		String profile = fileuploadService.restore(multipartFile);
		System.out.println(profile);
		System.out.println(siteVo);
		siteVo.setProfile(profile);
		siteService.update(siteVo);
		
		return "redirect:/admin?result=success";
	}
}
