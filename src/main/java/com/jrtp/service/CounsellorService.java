package com.jrtp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jrtp.entity.Counsellor;
import com.jrtp.repo.CounsellorRepo;

@Service
public class CounsellorService implements ICnsService {

	@Autowired
	CounsellorRepo cr;

	@Override
	public String saveCns(Counsellor c) {
		System.err.println(c);
		String email = c.getEmail();
		Counsellor ce = cr.findByEmail(email);
		System.out.println();
		if (ce != null)
			return "Duplicate Email Id";
		Counsellor sc = cr.save(c);
		System.out.println(sc);
		if (sc == null)
			return "Counsellor Not Register Something Issue";

		return "Counsellor Successfully Registered";
	}

	@Override
	public Counsellor loginCns(Counsellor c) {
		String email = c.getEmail();
		String pwd = c.getPwd();
		System.out.println(email + ", " + pwd);
		Counsellor clogin = cr.findByEmailAndPwd(email, pwd);
		System.out.println(clogin);

		return clogin;
	}

	public String forgetPwd(Counsellor c) {
		String email = c.getEmail();
		System.out.println(email);
		Counsellor rEmail = cr.findByEmail(email);
		if (rEmail != null)
			return "Please Check Your Email Pwd Sended";
		return "User Not Available";
	}

	public String findCounsellorName(Integer cid) {
		Counsellor c = cr.findById(cid).get();
		return c.getCname();
	}
}
