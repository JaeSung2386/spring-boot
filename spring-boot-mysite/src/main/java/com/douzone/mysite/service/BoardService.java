package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardDao;
import com.douzone.mysite.repository.CommentDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.CommentVo;
import com.douzone.mysite.vo.PageVo;


@Service
public class BoardService {
	
	@Autowired
	private BoardDao boardDao;
	
	@Autowired
	private CommentDao commentDao;
	
	// (7) 게시글 쓰기
	public void insert(BoardVo boardVo) {
		boardDao.insert(boardVo);
	}
	
	// (8) 게시글 수정
	public void boardModify(BoardVo boardVo) {
		boardDao.update(boardVo);
	}
	
	// (9) 게시글 삭제
	public void delete(BoardVo boardVo) {
		CommentVo vo = new CommentVo();
		vo.setBoard_no(boardVo.getNo());
		new CommentDao().deleteAll(vo);
		
		boardDao.delete(boardVo);
	}
	
	// (10-1) 댓글 작성
	public void commentInsert(CommentVo commentVo) {
		commentDao.insert(commentVo);
	}
	
	// (10-2) 댓글 수정
	public void commentModify(CommentVo commentVo) {
		commentDao.Update(commentVo);
	}
	
	// (10-3) 댓글의 답글 작성
	public void commentReply(CommentVo commentVo) {
		commentDao.replyinsert(commentVo);
	}
	
	// (10-4) 댓글의 답글 삭제
	public void commentDelete(CommentVo commentVo) {
		commentDao.delete(commentVo);
	}	
	
	// (11) 게시글 목록&검색
	
	public List<BoardVo> list(String kwd, String search, int page) {
		
		List<BoardVo> list = null;
			
		PageVo pagevo = new PageVo();
		pagevo.setPageNo(page);
		pagevo.setPageSize(10);
		if(kwd.isEmpty() && search.isEmpty()) {
			page = (page - 1) * 10;
			list = boardDao.getList(page, pagevo.getPageSize());
		} else {
			pagevo.setTotalCount(boardDao.getSearchTotalCount(kwd, search));
			page = (page - 1) * 10;
			return list = boardDao.getSearch(kwd, search, page, pagevo.getPageSize());
		}
		return list;
	}
	
	public PageVo page(String kwd, String search, int page) {
			
		PageVo pagevo = new PageVo();
		pagevo.setPageNo(page);
		pagevo.setPageSize(10);
		
		if(kwd.isEmpty() && search.isEmpty()) {
			pagevo.setTotalCount(boardDao.getTotalCount());
			page = (page - 1) * 10;
			
		} else {
			pagevo.setKwd(kwd);
			pagevo.setSearch(search);
			pagevo.setTotalCount(boardDao.getSearchTotalCount(kwd, search));
			page = (page - 1) * 10;
		}
		return pagevo;
	}	
	
	// (12) 게시글 읽기
	public BoardVo view(long no) {
		System.out.println("service: " + no);
		BoardVo vo = new BoardDao().get(no);
		// 조회수 증가
		new BoardDao().hitUpdate(no);
		
		return vo;
	}
	
	public List<CommentVo> commentView(long no) {
		CommentDao dao = new CommentDao();
		List<CommentVo> list = dao.getList(no);
		
		return list;
	}
	
	// (13) 게시글의 답글 작성
	public void replyInsert(BoardVo boardVo) {
		boardDao.replyinsert(boardVo);
	}
	
	public BoardVo get(long no) {
		return boardDao.get(no);
	}
	
	public CommentVo commentGet(long no) {
		return commentDao.get(no);
	}
}
