package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GuestbookVo;

@Repository
public class GuestbookDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public GuestbookVo get(long no) {
		GuestbookVo vo = sqlSession.selectOne("guestbook.get", no);
		return vo;
	}
	
	public long delete( GuestbookVo vo ) {
		System.out.println(vo.getNo());
		int count = sqlSession.delete( "guestbook.delete", vo );
		System.out.println(vo.getNo());
		if(count != 0) {
			return vo.getNo();
		} else {
			return -1;
		}
	}
	
	public long insert(GuestbookVo vo) {
		return sqlSession.insert("guestbook.insert", vo);
	}
	
	public List<GuestbookVo> getList() {
		Map<String, Object> map = new HashMap<String, Object>();
		return sqlSession.selectList("guestbook.getList", map);
	}
	
	public List<GuestbookVo> getList(int page) {
		page = (page-1) * 5;
		return sqlSession.selectList("guestbook.getList2", page);
	}
}