package com.jrtp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jrtp.entity.Enquiry;


public interface EnquiryRepo extends JpaRepository<Enquiry,String>{

	List<Enquiry> findByCid(Integer cid);
//	Another Simple Way???
//	List<Enquiry> findByCidAndModeAndCourseAndStatus(Integer cid,String m,String c,String s);
	//List<Enquiry> findByCidAndModeAndCourseAndStatus(Enquiry e);
	


	

}
