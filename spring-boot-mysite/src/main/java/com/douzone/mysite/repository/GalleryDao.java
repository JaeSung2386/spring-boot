package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GalleryVo;

@Repository
public class GalleryDao {

	@Autowired
	private SqlSession sqlSession;
	
	public List<GalleryVo> getList() {
		Map<String, Object> map = new HashMap<String, Object>();
		return sqlSession.selectList("gallery.getList", map);
	}
	
	public int insert(GalleryVo galleryVo) {
		return sqlSession.insert("gallery.insert", galleryVo);
	}
	
	public int delete(long no) {
		return sqlSession.delete("gallery.delete", no);
	}
}
