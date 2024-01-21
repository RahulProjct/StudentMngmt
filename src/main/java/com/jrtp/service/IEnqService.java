package com.jrtp.service;

import java.util.List;

import com.jrtp.binding.SearchCreateria;
import com.jrtp.entity.Enquiry;

public interface IEnqService {

	
	public List<Enquiry> getEnq(Integer e);

	public String addEnq(Enquiry e);
	
	public List<Enquiry> getEnqs(Integer ci,SearchCreateria sc);
	
}
