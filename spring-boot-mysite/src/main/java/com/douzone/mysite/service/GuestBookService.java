package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GuestbookDao;
import com.douzone.mysite.vo.GuestbookVo;

@Service
public class GuestBookService {
	@Autowired
	private GuestbookDao guestbookDao;
	
	public List<GuestbookVo> getMessageList() {
		return guestbookDao.getList();
	}
	
	public List<GuestbookVo> getMessageList(int page) {
		return guestbookDao.getList(page);
	}
	
	public GuestbookVo insert(GuestbookVo vo) {
		long no = guestbookDao.insert(vo);
		if(no == 0) {
			return null;
		} else {
			System.out.println(guestbookDao.get(vo.getNo()).toString());
			return guestbookDao.get(vo.getNo());
		}
	}
	
	public long delete(GuestbookVo vo) {
		long result = guestbookDao.delete(vo);
		
		// 삭제 실패
		if(result == -1) {
			return -1;
		} else {
			return result;
		}
	}
}
