package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.CommentVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	// (7) 게시글 쓰기
	@RequestMapping("/write")
	public String boardWrite() {
		return "/board/write";
	}	
	
	@RequestMapping( value="/write", method=RequestMethod.POST )
	public String boardWrite( 
			@ModelAttribute BoardVo boardVo,
			@RequestParam (value="userno", required=true) long userno) {
		boardVo.setUser_no(userno);
		boardService.insert(boardVo);
		return "redirect:/board";
	}
	
	// (8) 게시글 수정
	@RequestMapping("/modify/{vo.no}")
	public String modify(
			@PathVariable(value="vo.no") Long no, 
			Model model) {
		model.addAttribute("vo", boardService.get(no));
		return "/board/modify";
	}
	
	@RequestMapping(value="/modify", method = RequestMethod.POST)
	public String modify(
			@ModelAttribute BoardVo boardVo,
			@RequestParam (value="userno", required=true) long userno) {
		boardVo.setUser_no(userno);
		boardService.boardModify(boardVo);
		return "redirect:/board";
	}
	
	// (9) 게시글 삭제
	@RequestMapping("/delete/{vo.no}")
	public String delete(
			@PathVariable(value="vo.no") Long no, 
			Model model) {
		model.addAttribute("vo", boardService.get(no));
		return "/board/delete";
	}
	
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public String delete(
			@ModelAttribute BoardVo boardVo,
			@RequestParam (value="userno", required=true) long userno,
			@RequestParam (value="password", required=true) String password) {
		boardVo.setPassword(password);
		boardVo.setUser_no(userno);
		boardService.delete(boardVo);
		return "redirect:/board";
	}	
	
	// (10-1) 댓글 작성
	@RequestMapping( value="/comment/write", method=RequestMethod.POST )
	public String commentWrite( 
			@ModelAttribute CommentVo commentVo,
			@RequestParam (value="userno", required=true) long user_no,
			@RequestParam (value="board_no", required=true) long board_no) {
		commentVo.setUser_no(user_no);
		boardService.commentInsert(commentVo);
		return "redirect:/board/view/" + board_no;
	}
	
	// (10-2) 댓글 수정
	@RequestMapping("/comment/modify/{no}")
	public String commentModify(
			@PathVariable(value="no") Long no, 
			Model model) {
		model.addAttribute("vo", boardService.commentGet(no));
		return "/comment/modify";
	}
	
	@RequestMapping(value="/comment/modify", method = RequestMethod.POST)
	public String commentModify(
			@ModelAttribute CommentVo commentVo,
			@RequestParam (value="board_no", required=true) long board_no) {
		boardService.commentModify(commentVo);
		return "redirect:/board/view/" + board_no;
	}
	
	// (10-3) 댓글의 답글 작성
	@RequestMapping("/comment/reply/{no}")
	public String commentReplyWrite(
			@PathVariable(value="no") Long no, 
			Model model) {
		model.addAttribute("vo", boardService.commentGet(no));
		return "/comment/reply";
	}

	@RequestMapping(value="/comment/reply", method = RequestMethod.POST)
	public String commentReplyWrite(
			@RequestParam(value="board_no") Long board_no,
			@ModelAttribute CommentVo commentVo) {
		boardService.commentReply(commentVo);
		return "redirect:/board/view/" + board_no;
	}
	
	// (10-4) 댓글의 답글 삭제
	@RequestMapping("/comment/delete/{no}")
	public String commentDelete(
			@PathVariable(value="no") Long no, 
			Model model) {
		model.addAttribute("vo", boardService.commentGet(no));
		return "/comment/delete";
	}
	
	@RequestMapping(value="/comment/delete", method = RequestMethod.POST)
	public String commentDelete(
			@ModelAttribute CommentVo commentVo,
			@RequestParam (value="board_no", required=true) long board_no) {
		boardService.commentDelete(commentVo);
		return "redirect:/board/view/" + board_no;
	}	
	
	// (11) 게시글 검색&목록
	@RequestMapping("")
	public String list(	
			@RequestParam (value="kwd", required=false, defaultValue = "") String kwd,
			@RequestParam (value="search", required=false, defaultValue = "") String search,
			@RequestParam (value="page", required=false, defaultValue = "1") Integer page,
			Model model) {
		model.addAttribute("list", boardService.list(kwd, search, page));
		model.addAttribute("pagevo", boardService.page(kwd, search, page));
		return "/board/list";
	}
	
	// (11-1) 게시글 검색
	@RequestMapping(value="", method = RequestMethod.POST)
	public String searchList(
			@RequestParam (value="kwd", required=false) String kwd,
			@RequestParam (value="search", required=false) String search,
			@RequestParam (value="page", required=false, defaultValue = "1") Integer page,
			Model model) {
		model.addAttribute("list", boardService.list(kwd, search, page));
		model.addAttribute("pagevo", boardService.page(kwd, search, page));
		return "/board/list";
	}
	
	// (12) 게시글 읽기
	@RequestMapping(value="/view/{vo.no}", method=RequestMethod.GET)
	public String view(
			@PathVariable(value="vo.no") Long no,
			Model model) {
		model.addAttribute("list", boardService.commentView(no));
		model.addAttribute("vo", boardService.view(no));
		return "/board/view";
	}
	
	@RequestMapping(value="/view/{vo.no}/{authuser.no}", method=RequestMethod.GET)
	public String view(
			@PathVariable(value="vo.no") Long no,
			@PathVariable(value="authuser.no") Long userno,
			Model model) {
		model.addAttribute("list", boardService.commentView(no));
		model.addAttribute("vo", boardService.view(no));
		return "/board/view";
	}
	
	// (13) 답글 작성
	@RequestMapping("/reply/{vo.no}")
	public String replyWrite(
			@PathVariable(value="vo.no") Long no, 
			Model model) {
		model.addAttribute("vo", boardService.get(no));
		return "/board/reply";
	}

	@RequestMapping(value="/reply", method = RequestMethod.POST)
	public String replyWrite(
			@RequestParam(value="userno") Long userno,
			@RequestParam(value="no") Long no,
			@ModelAttribute BoardVo boardVo) {
		boardVo.setNo(no);
		boardVo.setUser_no(userno);
		boardService.replyInsert(boardVo);
		return "redirect:/board";
	}
		
}
