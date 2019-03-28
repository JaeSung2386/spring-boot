package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;

@Repository
public class BoardDao {

	@Autowired
	private SqlSession sqlSession;
	
	public List<BoardVo> getList(int startRow, int endRow) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		
		return sqlSession.selectList("board.getList", map);
	}
	
	public List<BoardVo> getSearch(String krd, String search, int startRow, int endRow) {

		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("krd", krd);
		map.put("search", search);
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		
		return sqlSession.selectList("board.getSearch", map);
	}
	
	public int getSearchTotalCount(String kwd, String search) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("kwd", kwd);
		map.put("search", search);
		
		return sqlSession.selectOne("board.getSearchTotalCount", map);
	}
	
	public int getTotalCount() {
		return sqlSession.selectOne("board.getTotalCount");
	}
	/*
	public BoardVo get(long no) {
		System.out.println("no: " + no);
		BoardVo vo = sqlSession.selectOne("board.get", no);
		System.out.println(vo);
		return vo;
	}
	*/
	public BoardVo get(long no) {
		BoardVo vo = new BoardVo();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();

			String sql = 
				" select title, contents, user_no, g_no, o_no, depth" + 
				"   from board" + 
				"  where no=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String title = rs.getString(1);
				String contents = rs.getString(2);
				long user_no = rs.getLong(3);
				int g_no = rs.getInt(4);
				int o_no = rs.getInt(5);
				int depth = rs.getInt(6);
				
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setUser_no(user_no);
				vo.setG_no(g_no);
				vo.setO_no(o_no);
				vo.setDepth(depth);
				
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
		return vo;
	}
	
	public int insert(BoardVo boardVo) {
		return sqlSession.insert("board.insert", boardVo);
	}
	
	public int replyinsert(BoardVo boardVo) {
		return sqlSession.insert("board.replyinsert", boardVo);
	}
	
	public int update(BoardVo boardVo) {
		return sqlSession.update("board.update", boardVo);
	}
	
	public int replyUpdate(BoardVo boardVo) {
		return sqlSession.update("boardVo.replyupdate", boardVo);
	}
	/*
	public int hitUpdate(long no) {
		return sqlSession.update("boardVo.hitupdate", no);
	}
	*/
	public void hitUpdate(long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql =
					"update board " + 
					"set hit = hit + 1 " + 
					"where no = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);
			pstmt.executeUpdate();
			
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
	}
	
	public int delete( BoardVo boardVo ) {
		return sqlSession.delete("board.delete", boardVo);
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
