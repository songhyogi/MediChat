package kr.spring.health.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import kr.spring.health.service.HealthyService;
import kr.spring.health.vo.HealthyBlogVO;
import kr.spring.member.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HealthController {
 
	@Autowired
	private HealthyService service;
	
	@ModelAttribute
	public HealthyBlogVO initCommand() {
		return new HealthyBlogVO();
	}
	@GetMapping("/health/healthBlog")
	 public String getHeathList() {
		 return"healthy_Blog";
	 }
	
	@GetMapping("/heath/healWrite")
	public String getHealthWriteForm(HttpServletRequest request,HttpSession session,Model model) {
		MemberVO user = (MemberVO) session.getAttribute("user");log.debug("SSSSSSS"+request.getRequestURI());
		if(user == null) {
			model.addAttribute("message","로그인 후 사용가능합니다.");
			model.addAttribute("url",request.getContextPath()+"/member/login");
			session.setAttribute("preURL", request.getContextPath()+"/heath/healWrite");
		}else if(user.getMem_auth() >2 && user!= null) {
			return "healthy_writeForm";
		}else {
			model.addAttribute("message","쓰기 권한이 없습니다.");
			model.addAttribute("url",request.getContextPath()+"/heath/healWrite");
		}
		return "common/resultAlert";
	}
	@PostMapping("/heath/healWrite")
	public String getHealthWrite(HealthyBlogVO vo,HttpServletRequest request,HttpSession session,Model model) {
		
		MemberVO user = (MemberVO) session.getAttribute("user");
		
		if(user == null) {
			model.addAttribute("message","로그인 후 사용가능합니다.");
			model.addAttribute("url",request.getContextPath()+"/member/login");
			session.setAttribute("preURL", request.getContextPath()+"/heath/healWrite");
		}else if(user.getMem_auth() >2 && user!= null) {
			return getHeathList();
		}else {
			model.addAttribute("message","쓰기 권한이 없습니다.");
			model.addAttribute("url",request.getContextPath()+"/heath/healWrite");
		}
		return  getHeathList();
	}
}
