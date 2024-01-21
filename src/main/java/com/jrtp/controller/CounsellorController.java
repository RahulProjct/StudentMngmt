package com.jrtp.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.jrtp.binding.Dashboard;
import com.jrtp.binding.SearchCreateria;
import com.jrtp.entity.Counsellor;
import com.jrtp.entity.Enquiry;
import com.jrtp.service.CounsellorService;
import com.jrtp.service.EnqService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CounsellorController {

	private CounsellorService cService;
	private EnqService eSrvc;
	private Counsellor cns;
	private Dashboard dashboard;
	private Enquiry enq;

	private static Logger logger = LoggerFactory.getLogger(CounsellorController.class);

	@Autowired
	public void setcService(CounsellorService cService) {
		this.cService = cService;
	}

	@Autowired
	public void setDashboard(Dashboard dashboard) {
		this.dashboard = dashboard;
	}

	@Autowired
	public void setEnq(Enquiry enq) {
		this.enq = enq;
	}

	@Autowired
	public void seteSrvc(EnqService eSrvc) {
		this.eSrvc = eSrvc;
	}

	@Autowired
	public void setService(CounsellorService service) {
		this.cService = service;
	}

	@Autowired
	public void setCns(Counsellor cns) {
		this.cns = cns;
	}

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("c", new Counsellor());

		return "login";
	}
	@GetMapping("/logout")
	public String logout(Model model, HttpServletRequest request) {
		model.addAttribute("c", new Counsellor());
		HttpSession session = request.getSession(false);

		return "redirect:/";
	}
	@GetMapping("/enqs")
	public String getEnqs(Model model, HttpServletRequest request) {
		//getting Cid from session
		HttpSession session = request.getSession(false);
		Integer cid = (Integer) session.getAttribute("cid");
		String cName =cService.findCounsellorName(cid);
		model.addAttribute("name",cName);
		List<Enquiry> enqs = eSrvc.getEnq(cid);
		model.addAttribute("enqs", enqs);
		model.addAttribute("s", new SearchCreateria());
		return "viewenq";
	}
	@PostMapping("/enqs")
	public String filterEnq(@ModelAttribute ("s") SearchCreateria s, Model model, HttpServletRequest request) {
		//getting Cid from session
		HttpSession session = request.getSession(false);
		Integer cid = (Integer) session.getAttribute("cid");
		String cName =cService.findCounsellorName(cid);
		model.addAttribute("name",cName);
		List<Enquiry> enqs = eSrvc.getEnqs(cid, s);
		model.addAttribute("enqs", enqs);
		return "filterView";
	}

	@GetMapping("/dashboard")
	public String dashboard(Model model, HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		Integer cid = (Integer) session.getAttribute("cid");
		logger.warn(cid + "");
		// enq.setCid(cid);
		//retrive Cousellor Name and show dashboard page 
		String cName =cService.findCounsellorName(cid);
		model.addAttribute("name",cName);
		List<Enquiry> listEnq = eSrvc.getEnq(cid);
		model.addAttribute("enqs", listEnq);
		List<Enquiry> enrolled = listEnq.stream()
				.filter(enrolledStatus -> "Enrolled".equals(enrolledStatus.getStatus())).collect(Collectors.toList());
		System.out.println(listEnq);
		int totalEnq = listEnq.size();
		System.out.println(totalEnq);
		dashboard.setTotalEnq(totalEnq);
		int enrolledEnq = enrolled.size();
		System.out.println(enrolledEnq);
		dashboard.setEnrollEnq(enrolledEnq);
		dashboard.setLostEnq(totalEnq - enrolledEnq);
		model.addAttribute("dash", dashboard);
		return "dashboard";
	}

	@GetMapping("/forget")
	public String forget(Model model, HttpServletRequest hsr) {
		model.addAttribute("c", new Counsellor());
		HttpSession ce = hsr.getSession(false);

		return "forget";
	}

	@GetMapping("/registr")
	public String regPage(Model model) {

		model.addAttribute("c", new Counsellor());
		return "registration";
	}

	@GetMapping("/addenq")
	public String addEnq(Model model) {
		model.addAttribute("e", new Enquiry());
		return "addenq";
	}

	@PostMapping(value = "/registr")
	public String handleBRegistration(@ModelAttribute("c") Counsellor c, Model model) {

		System.out.println(c);

		String status = cService.saveCns(c);
		if (status != null)
			model.addAttribute("msg", status);
		return "registration";
	}

	@PostMapping(value = "/addenq")
	public String handleAddEnq(@ModelAttribute("e") Enquiry e, Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
		Integer cid = (Integer) session.getAttribute("cid");
		e.setCid(cid);
		System.out.println(e);
		String status = eSrvc.addEnq(e);
		if (status != null)
			model.addAttribute("msg", status);
		return "addenq";
	}

	@PostMapping(value = "/forget") // , produces="application/json",consumes="application/json")
	public String handleForget(@ModelAttribute("c") Counsellor c, Model model) {

		System.out.println(c);
		String status = cService.forgetPwd(c);

		if (status != null)
			model.addAttribute("msg", status);
		return "forget";
	}

	/*
	 * @PostMapping(value = "/t", produces = "application/json", consumes =
	 * "application/json") public ModelAndView handleLogin(@ModelAttribute("c")
	 * Counsellor c, HttpSession s) { ModelAndView mav = new ModelAndView();
	 * 
	 * logger.info("c counsellor data" + c); System.out.println(c); Counsellor cl =
	 * service.loginCns(c); logger.info("c1 counsoller data" + cl); if (cl != null)
	 * { s.setAttribute("cl", cl); System.out.println(cl);
	 * mav.setViewName("forward:dashboard"); return mav; } mav.addObject("errMsg",
	 * "Invalid Credential"); mav.setViewName("login"); return mav; }
	 */

	@PostMapping(value = "/")
	public String handleLogin(@ModelAttribute("c") Counsellor c, Model m, HttpServletRequest request) {

		logger.info("c counsellor data" + c);
		System.out.println("Enterd Email ID Or Password " + c);
		Counsellor cl = cService.loginCns(c);
		if (cl != null) {
			logger.info("c1 counsoller data" + cl);
			Integer cid = cl.getCid();
			HttpSession session = request.getSession(true);
			session.setAttribute("cid", cid);
			System.out.println(cl);
			return "redirect:dashboard";
		}
		m.addAttribute("errMsg", "Invalid Creadential");
		return "login";

	}
}
