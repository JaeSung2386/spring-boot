package com.douzone.mysite.vo;

public class CommentVo {
	private long no;
	private String contents;
	private String write_date;
	private int g_no;
	private int o_no;
	private int depth;
	private long board_no;
	private long user_no;
	private String user_name;
	private String password;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public long getNo() {
		return no;
	}
	public void setNo(long no) {
		this.no = no;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getWrite_date() {
		return write_date;
	}
	public void setWrite_date(String write_date) {
		this.write_date = write_date;
	}
	public int getG_no() {
		return g_no;
	}
	public void setG_no(int g_no) {
		this.g_no = g_no;
	}
	public int getO_no() {
		return o_no;
	}
	public void setO_no(int o_no) {
		this.o_no = o_no;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public long getBoard_no() {
		return board_no;
	}
	public void setBoard_no(long board_no) {
		this.board_no = board_no;
	}
	public long getUser_no() {
		return user_no;
	}
	public void setUser_no(long user_no) {
		this.user_no = user_no;
	}
	@Override
	public String toString() {
		return "CommentVo [no=" + no + ", contents=" + contents + ", write_date=" + write_date + ", g_no=" + g_no
				+ ", o_no=" + o_no + ", depth=" + depth + ", board_no=" + board_no + ", user_no=" + user_no
				+ ", user_name=" + user_name + ", password=" + password + "]";
	}	
}
