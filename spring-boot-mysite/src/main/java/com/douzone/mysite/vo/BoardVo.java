package com.douzone.mysite.vo;

public class BoardVo {
	private long no;
	private String title;
	private String contents;
	private String write_date;
	private int hit;
	private int g_no;
	private int o_no;
	private int depth;
	private long user_no;
	private String name;
	private String password;
	private int count_comment;
	
	public int getCount_comment() {
		return count_comment;
	}
	public void setCount_comment(int count_comment) {
		this.count_comment = count_comment;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getNo() {
		return no;
	}
	public void setNo(long no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public long getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public long getG_no() {
		return g_no;
	}
	public void setG_no(int g_no) {
		this.g_no = g_no;
	}
	public long getO_no() {
		return o_no;
	}
	public void setO_no(int o_no) {
		this.o_no = o_no;
	}
	public long getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public long getUser_no() {
		return user_no;
	}
	public void setUser_no(long user_no) {
		this.user_no = user_no;
	}
	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", contents=" + contents + ", write_date=" + write_date
				+ ", hit=" + hit + ", g_no=" + g_no + ", o_no=" + o_no + ", depth=" + depth + ", user_no=" + user_no
				+ ", name=" + name + "]";
	}
	
	
}
