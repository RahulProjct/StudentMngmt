package com.jrtp.binding;

import org.springframework.stereotype.Component;

@Component
public class SearchCreateria {

	private String mode;
	private String course;
	private String status;
	
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
