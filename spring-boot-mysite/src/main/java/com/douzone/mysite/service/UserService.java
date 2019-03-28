package com.douzone.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.UserDao;
import com.douzone.mysite.vo.UserVo;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	public boolean existEmail( String email ) {
		UserVo userVo = userDao.get( email );
		System.out.println(userVo);
		return userVo != null;
	}
	
	// 1. 회원가입
	public void join(UserVo userVo) {
		// 1. DB에 가입 회원 정보 insert 하기
		userDao.insert(userVo);

		// 2. email 주소 확인하는 메일 보내기
	}
	
	// 6. 수정
	public boolean modify(UserVo userVo) {
		return userDao.update(userVo) == 1;
	}
	
	// 6. 수정
	public boolean drop(UserVo userVo) {
		return userDao.delete(userVo) == 1;
	}
	
	public UserVo getUser( String email, String password ) {
		return userDao.get( email, password );
	}
	
	public UserVo getUser(long no) {
		return userDao.get(no);
	}
}
