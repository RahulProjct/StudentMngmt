package com.jrtp.binding;

import org.springframework.stereotype.Component;

@Component
public class Dashboard {

	
	private Integer totalEnq;
	private Integer enrollEnq;
	private Integer lostEnq;
	@Override
	public String toString() {
		return "Dashboard [totalEnq=" + totalEnq + ", enrollEnq=" + enrollEnq + ", lostEnq=" + lostEnq + "]";
	}
	
	public Integer getTotalEnq() {
		return totalEnq;
	}
	public void setTotalEnq(Integer totalEnq) {
		this.totalEnq = totalEnq;
	}
	public Integer getEnrollEnq() {
		return enrollEnq;
	}
	public void setEnrollEnq(Integer enrollEnq) {
		this.enrollEnq = enrollEnq;
	}
	public Integer getLostEnq() {
		return lostEnq;
	}
	public void setLostEnq(Integer lostEnq) {
		this.lostEnq = lostEnq;
	}
	
	

	
}
