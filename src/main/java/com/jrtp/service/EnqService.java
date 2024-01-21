package com.jrtp.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.jrtp.binding.SearchCreateria;
import com.jrtp.entity.Enquiry;
import com.jrtp.repo.EnquiryRepo;

@Service
public class EnqService implements IEnqService{

	
	@Autowired EnquiryRepo er;
	
	
	@Override
	public List<Enquiry> getEnq(Integer cid) {
		List<Enquiry> enqList = er.findByCid(cid);
		return enqList;
	}
	@Override
	public String addEnq(Enquiry e) {
		System.out.println(e);
//		why Generation required withOut this exception show
		Random random =new Random();
	    int eid=random.nextInt(50);
		e.setEid(eid+"");
	/**
	 * this Exception come
	 * java.sql.SQLException: Field 'eid' doesn't have a default value
	 */
		Enquiry saveEnq = er.save(e);
		if (saveEnq == null)
			return "Enquiry Not Added Something Issue";

		return "Enquiry Successfully Added";
		
	}
	@Override
	public List<Enquiry> getEnqs(Integer cid,SearchCreateria sc) {
		Enquiry e=new Enquiry();
		
		e.setCid(cid);
	// I develop wrong 
		//List<Enquiry> filterList = er.findByCidAndModeAndCourseAndStatus(cid, sc.getMode(), sc.getCourse(), sc.getStatus());
	//	List<Enquiry> filterList2 = er.findByCidAndModeAndCourseAndStatus(e);
		if((sc.getCourse()!=null)&&!sc.getCourse().equals(""))
			e.setCourse(sc.getCourse());
		if((sc.getMode()!=null)&&!sc.getMode().equals(""))
			e.setMode(sc.getMode());
		if((sc.getStatus()!=null)&&!sc.getStatus().equals(""))
			e.setStatus(sc.getStatus());
		System.out.println(e);
		Example<Enquiry> of = Example.of(e);
		List<Enquiry> listEnq = er.findAll(of);
		return listEnq;
	}

	
	
}
