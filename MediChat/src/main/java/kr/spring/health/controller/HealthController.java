package kr.spring.health.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.spring.health.service.HealthyService;
import kr.spring.health.vo.HealthyBlogVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.FileUtil;
import kr.spring.util.PagingUtil;
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
	 public String getHeathList(@RequestParam(defaultValue="1") int pageNum,String keyword, String keyfield,Model model) {
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		int count = service.selectHealCount(map);
		
		PagingUtil page = new PagingUtil(keyfield,keyword,pageNum,count,10,10,"healthyBlog");
		
		map.put("start", page.getStartRow());
		map.put("end", page.getEndRow());
		
		List<HealthyBlogVO> list = service.selectHealList(map);
		
		model.addAttribute("list",list);
		model.addAttribute("count",count);
		model.addAttribute("page",page.getPage());
		
		return"healthy_Blog";
	 }
	
	@GetMapping("/heath/healWrite")
	public String getHealthWriteForm(HttpServletRequest request,HttpSession session,Model model) {
		MemberVO user = (MemberVO) session.getAttribute("user");
		if(user == null) {
			model.addAttribute("message","로그인 후 사용가능합니다.");
			model.addAttribute("url",request.getContextPath()+"/member/login");
			session.setAttribute("preURL", request.getContextPath()+"/heath/healWrite");
		}else if(user.getMem_auth() >2 && user!= null) {
			return "healthy_writeForm";
		}else {
			model.addAttribute("message","쓰기 권한이 없습니다.");
			model.addAttribute("url",request.getContextPath()+"/heath/healthBlog");
		}
		
		return "common/resultAlert";
	}
	@PostMapping("/heath/healWrite")
	public String getHealthWrite(HealthyBlogVO vo,HttpServletRequest request,HttpSession session,Model model) throws IllegalStateException, IOException {
		
		MemberVO user = (MemberVO) session.getAttribute("user");
		
		if(user == null) {
			model.addAttribute("message","로그인 후 사용가능합니다.");
			model.addAttribute("url",request.getContextPath()+"/member/login");
			session.setAttribute("preURL", request.getContextPath()+"/heath/healWrite");
		}else if(user.getMem_auth() >2 && user!= null) {
			vo.setMem_num(user.getMem_num());
			vo.setH_filename(FileUtil.createFile(request, vo.getUpload()));
			
			service.insertHeal(vo);
			
			return "healthy_Blog";
		}else {
			model.addAttribute("message","쓰기 권한이 없습니다.");
			model.addAttribute("url",request.getContextPath()+"/heath/healthBlog");
		}
		return  "common/resultAlert";
	}
	
	@GetMapping("/health/healthDetail")
	public String getHealthDetail(long healthy_num,HttpSession session,Model model) {
		
		service.updateHealHit(healthy_num);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("healthy_num", healthy_num);
		MemberVO user = (MemberVO) session.getAttribute("user");
		if(user != null)
		map.put("user_num", user.getMem_num());
		else
			map.put("user_num", 0);
		HealthyBlogVO vo = service.getHealthy(map);
		
		model.addAttribute("healthy",vo);
		return "healthy_Detail";
	}
	@GetMapping("/health/healthUpdate")
	public String getHealthUpdateForm(long healthy_num,HttpServletRequest request,HttpSession session,Model model) {
		MemberVO user = (MemberVO) session.getAttribute("user");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("healthy_num", healthy_num);
		HealthyBlogVO vo = service.getHealthy(map);
		if(user == null) {
			model.addAttribute("message","로그인 후 사용가능합니다.");
			model.addAttribute("url",request.getContextPath()+"/member/login");
		}else if(user.getMem_num() == vo.getMem_num() ) {
			map.put("user_num", user.getMem_num());
			model.addAttribute("healthyBlogVO",vo);
			return "healthy_Update";
		}else {
			model.addAttribute("message","본인 작성 글만 수정 가능합니다.");
			model.addAttribute("url",request.getContextPath()+"/health/healthBlog");
		}
		
		return  "common/resultAlert";
	}
	@PostMapping("/health/healthUpdate")
	public String getHealUpdate(HealthyBlogVO vo,HttpSession session,HttpServletRequest request,Model model) throws IllegalStateException, IOException {
		MemberVO user = (MemberVO) session.getAttribute("user");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("healthy_num", vo.getHealthy_num());
	
		HealthyBlogVO db_vo = service.getHealthy(map);
		if(user == null) {
			model.addAttribute("message","로그인 후 사용가능합니다.");
			model.addAttribute("url",request.getContextPath()+"/member/login");
		}else if(user.getMem_num() == db_vo.getMem_num() ) {
			if(vo.getUpload() != null) {
				vo.setH_filename(FileUtil.createFile(request, vo.getUpload()));
				FileUtil.removeFile(request, db_vo.getH_filename());
			}
			service.updateHeal(vo);
			
			return "redirect:/health/healthDetail?healthy_num="+vo.getHealthy_num();
		}else {
			model.addAttribute("message","본인 작성 글만 수정 가능합니다.");
			model.addAttribute("url",request.getContextPath()+"/health/healthBlog");
		}
		
		
		return  "common/resultAlert";
	}
	@GetMapping("/health/healthDelete")
	public String getHealthDelete(long healthy_num,HttpServletRequest request,HttpSession session,Model model) {
		MemberVO user = (MemberVO) session.getAttribute("user");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("healthy_num", healthy_num);
		HealthyBlogVO vo = service.getHealthy(map);
		if(user == null) {
			model.addAttribute("message","로그인 후 사용가능합니다.");
			model.addAttribute("url",request.getContextPath()+"/member/login");
		}else if(user.getMem_num() == vo.getMem_num() ) {
			service.deleteHeal(healthy_num);
			model.addAttribute("message","게시글이 삭제 되었습니다.");
			model.addAttribute("url",request.getContextPath()+"/health/healthBlog");
		}else {
			model.addAttribute("message","본인 작성 글만 삭제 가능합니다.");
			model.addAttribute("url",request.getContextPath()+"/health/healthBlog");
		}
		
		return  "common/resultAlert";
	}
	
	
}
