package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.SiteVo;

@Repository
public class SiteDao {

	@Autowired
	private SqlSession sqlSession;
	
	public SiteVo get() {
		SiteVo siteVo = new SiteVo();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();

			String sql = 
				" select * from site";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				siteVo.setTitle(rs.getString(1));
				siteVo.setWelcome(rs.getString(2));
				siteVo.setProfile(rs.getString(3));
				siteVo.setDescription(rs.getString(4));
			}
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return siteVo;
	}
	/*
	public int update(SiteVo siteVo) {
		return sqlSession.update("siteVo.update", siteVo);
	}
	*/
	public int update(SiteVo siteVo) {
		System.out.println(siteVo.getTitle());
		System.out.println(siteVo.getProfile());
		System.out.println(siteVo.getWelcome());
		System.out.println(siteVo.getDescription());
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();

			String sql =
					"update site " + 
					"set title = ?, welcome = ?, profile = ?, description = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, siteVo.getTitle());
			pstmt.setString(2, siteVo.getWelcome());
			pstmt.setString(3, siteVo.getProfile());
			pstmt.setString(4, siteVo.getDescription());
			
			count = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			//1. 드라이버 로딩
			Class.forName( "com.mysql.jdbc.Driver" );
			
			//2. 연결하기
			String url="jdbc:mysql://localhost/webdb?characterEncoding=utf8&serverTimezone=UTC";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch( ClassNotFoundException e ) {
			System.out.println( "드러이버 로딩 실패:" + e );
		} 
		
		return conn;
	}	
}
