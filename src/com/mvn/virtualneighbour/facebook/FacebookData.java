package com.mvn.virtualneighbour.facebook;

public class FacebookData {

	private String id;
	private String name;
	private String gender;
	private String username;
	private PictureDetail picture;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public PictureDetail getPicture() {
		return picture;
	}

	public void setPicture(PictureDetail picture) {
		this.picture = picture;
	}

}
