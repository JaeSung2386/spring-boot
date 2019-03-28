package com.douzone.mysite.vo;

public class GalleryVo {
	private long no;
	private String comment;
	private String image_url;
	public long getNo() {
		return no;
	}
	public void setNo(long no) {
		this.no = no;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	@Override
	public String toString() {
		return "GalleryVo [no=" + no + ", comment=" + comment + ", image_url=" + image_url + "]";
	}
	
}
