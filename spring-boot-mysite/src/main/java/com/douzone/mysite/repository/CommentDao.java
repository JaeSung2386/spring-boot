package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.CommentVo;

@Repository
public class CommentDao {
	
	public int replyinsert(CommentVo vo) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = 
				" insert" + 
				"   into comment" + 
				" values ( "+
				//no
				" 		null, " +
				//contents
				"		?, " + 
				//글작성시간
				"		now(), " +
				//g_no(부모의 g_no)
				"		(select c.g_no FROM comment c where c.no = ?), " +
				//o_no(부모의 o_no + 1)
				"		(select c.o_no+1 FROM comment c where c.no = ?), " +
				//depth(부모의 depth + 1)
				"		(select c.depth+1 FROM comment c where c.no = ?), " +
				//외래키(게시물 번호)
				"		?, " + 
				//외래키(유저 번호)
				" 		? )";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getContents());
			pstmt.setLong(2, vo.getNo());
			pstmt.setLong(3, vo.getNo());
			pstmt.setLong(4, vo.getNo());
			pstmt.setLong(5, vo.getBoard_no());
			pstmt.setLong(6, vo.getUser_no());

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
	
	public void replyUpdate(CommentVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			
			String sql =
					"update comment  " + 
					"set o_no = o_no + 1 " + 
					"where g_no = ? " +
					"and o_no > ? " +
					"and write_date < (select * from (select max(c.write_date) from comment c) tmp)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, vo.getG_no());
			pstmt.setLong(2, vo.getO_no());
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
	
	public int deleteAll( CommentVo vo ) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = 
				" delete comment" + 
				"  from comment" +
				"  where comment.board_no = ?";
			
			pstmt = conn.prepareStatement( sql );

			pstmt.setLong( 1, vo.getBoard_no() );
			
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
	
	public int delete( CommentVo vo ) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = 
				" delete comment" + 
				"  from comment" + 
				"  join user " +
				"  on comment.user_no = ?" +
				"  join board " +
				"  on comment.board_no = ?" +
				"  where comment.no = ?" +
				"  and user.password = ?";
			pstmt = conn.prepareStatement( sql );

			pstmt.setLong( 1, vo.getUser_no() );
			pstmt.setLong( 2, vo.getBoard_no() );
			pstmt.setLong( 3, vo.getNo());
			pstmt.setString(4, vo.getPassword());
			
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
	
	public int insert(CommentVo vo) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = 
				" insert" + 
				"   into comment " + 
				" values ( " +
				"		null, " +
				"		?, " +
				"		now(), " + 
				"		(select if(count(*)=0, 1, MAX(g_no)+1) FROM comment c), " +
				"		1, " + 
				"		0, " +
				"		?, " +
				"		?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getContents());
			pstmt.setLong(2, vo.getBoard_no());
			pstmt.setLong(3, vo.getUser_no());
			
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
	
	public CommentVo get(long no) {
		CommentVo vo = new CommentVo();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();

			String sql = 
				" select contents, g_no, o_no, depth, board_no, user_no" + 
				"   from comment" + 
				"  where no=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String contents = rs.getString(1);
				int g_no = rs.getInt(2);
				int o_no = rs.getInt(3);
				int depth = rs.getInt(4);
				long board_no = rs.getLong(5);
				long user_no = rs.getLong(6);
				
				vo.setNo(no);
				vo.setContents(contents);
				vo.setG_no(g_no);
				vo.setO_no(o_no);
				vo.setDepth(depth);
				vo.setBoard_no(board_no);
				vo.setUser_no(user_no);
				
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
	
	public int Update(CommentVo vo) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();

			String sql =
					"update comment " + 
					"set contents = ?, write_date = now() " + 
					"where no = ? " +
					"and user_no = ? " +
					"and board_no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getContents());
			pstmt.setLong(2, vo.getNo());
			pstmt.setLong(3, vo.getUser_no());
			pstmt.setLong(4, vo.getBoard_no());
			
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
	
	public List<CommentVo> getList(long b_no) {
		List<CommentVo> list = new ArrayList<CommentVo>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			//현재 게시물의 no와 댓글의 no와 일치
			//댓글의 user_no와 user의 no와 일치
			String sql = 
					"select c.no, " +
					"		c.contents, " + 
					"		c.write_date, " +
					"		c.g_no, " +
					"		c.o_no, " +
					"		c.depth, " +
					"		u.name, " + 
					"		u.no, " +
					"		b.no " +
					"from comment c, board b, user u " + 
					"where c.board_no = b.no " + 
					"and c.user_no = u.no " + 
					"and c.board_no = ? " +
					"order by c.g_no desc, " +
					"c.o_no asc";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, b_no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				long no = rs.getLong(1);
				String contents = rs.getString(2);
				String write_date = rs.getString(3);
				int g_no = rs.getInt(4);
				int o_no = rs.getInt(5);
				int depth = rs.getInt(6);
				String user_name = rs.getString(7);
				long user_no = rs.getLong(8);
				long board_no = rs.getLong(9);
				
				CommentVo vo = new CommentVo();
				vo.setNo(no);
				vo.setContents(contents);
				vo.setWrite_date(write_date);
				vo.setG_no(g_no);
				vo.setO_no(o_no);
				vo.setDepth(depth);
				vo.setUser_name(user_name);
				vo.setUser_no(user_no);
				vo.setBoard_no(board_no);
				
				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
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
		
		return list;
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
