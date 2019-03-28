package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GalleryDao;
import com.douzone.mysite.vo.GalleryVo;

@Service
public class GalleryService {

	@Autowired
	private GalleryDao galleryDao;
	
	public List<GalleryVo> getgGalleryList() {
		return galleryDao.getList();
	}
	
	public int insert(GalleryVo galleryVo) {
		return galleryDao.insert(galleryVo);
	}
	
	public int delete(long no) {
		return galleryDao.delete(no);
	}
}
