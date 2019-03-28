package com.douzone.mysite.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.dto.JSONResult;
import com.douzone.mysite.service.GuestBookService;
import com.douzone.mysite.vo.GuestbookVo;
import com.fasterxml.jackson.annotation.JsonView;

@Controller("guestbookApiController")
@RequestMapping("/guestbook/api")
public class GuestbookController {
	
	@Autowired
	private GuestBookService guestbookService;
	
	@ResponseBody
	@RequestMapping("/list")
	public JSONResult list(
			@RequestParam(value="page", required=true) int page) {
		List<GuestbookVo> list = guestbookService.getMessageList(page);
		return JSONResult.success(list);
	}
	
	@ResponseBody
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public JSONResult add(
			@RequestBody GuestbookVo vo) {
		return JSONResult.success(guestbookService.insert(vo));
	}

	@ResponseBody
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public JSONResult delete(
			@RequestParam (value="no", required=true) long no,
			@RequestParam (value="password", required=true) String password) {
		GuestbookVo vo = new GuestbookVo();
		vo.setNo(no);
		vo.setPassword(password);
		return JSONResult.success(guestbookService.delete(vo));
	}
}
