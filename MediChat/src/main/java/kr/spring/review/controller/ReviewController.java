package kr.spring.review.controller;

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

import kr.spring.hospital.service.HospitalService;
import kr.spring.member.vo.MemberVO;
import kr.spring.review.service.ReviewService;
import kr.spring.review.vo.ReviewVO;
import kr.spring.util.PagingUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ReviewController {
	
	@Autowired
	private ReviewService service;
	
	@Autowired
	private HospitalService hospitalService;
	
	@ModelAttribute
	public ReviewVO initCommand() {
		return new ReviewVO();
	}
	
	@GetMapping("/review/reviewMemList")
	public String myReviewList(@RequestParam(defaultValue="1") int pageNum,@RequestParam(defaultValue="1") String keyfield, HttpServletRequest request,HttpSession session,Model model) {
		MemberVO user= (MemberVO) session.getAttribute("user");
		if(user == null) {
			model.addAttribute("message","로그인 후 이용해주세요");
			model.addAttribute("url",request.getContextPath()+"/member/login");
		}else {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("mem_num", user.getMem_num());
			int count = service.selectCountByMem(map);
			map.put("keyfield", keyfield);
			PagingUtil page = new PagingUtil(keyfield,null,pageNum,count,5,10,"reviewMemList");
			map.put("start",page.getStartRow());
			map.put("end", page.getEndRow());
			List<ReviewVO> list = service.selectReviewListByMem(map);
			
			model.addAttribute("count",count);
			model.addAttribute("list",list);
			model.addAttribute("page",page.getPage());
			
			return"myReviewList";
		}
		return "common/resultAlert";
	}
	
	@GetMapping("/review/writeReview")
	public String writeReviewForm(ReviewVO vo, HttpSession session, HttpServletRequest request,Model model) {
		MemberVO user= (MemberVO) session.getAttribute("user");
		if(user == null) {
			model.addAttribute("message","로그인 후 이용해주세요");
			model.addAttribute("url",request.getContextPath()+"/member/login");
		}else {
			model.addAttribute("reviewVO",vo);
			model.addAttribute("hos_name",hospitalService.selectHospital(vo.getHos_num()).getHos_name());
			return "myReviewWrite";
		}
		
		return "common/resultAlert";
	}
	
	@PostMapping("/review/writeReview")
	public String writeReview(ReviewVO vo,HttpSession session, HttpServletRequest request,Model model) {
		MemberVO user= (MemberVO) session.getAttribute("user");
		if(user == null) {
			model.addAttribute("message","로그인 후 이용해주세요");
			model.addAttribute("url",request.getContextPath()+"/member/login");
		}else {
			vo.setMem_num(user.getMem_num());
			service.insertReview(vo);
			return "redirect:/review/reviewMemList";
		}
		
		return "common/resultAlert";
	}
	
	@GetMapping("/review/updateReview")
	public String writeReviewForm(long rev_num,HttpSession session, HttpServletRequest request,Model model) {
		MemberVO user= (MemberVO) session.getAttribute("user");
		if(user == null) {
			model.addAttribute("message","로그인 후 이용해주세요");
			model.addAttribute("url",request.getContextPath()+"/member/login");
		}else {
			ReviewVO vo = service.selectReviewDetail(rev_num);
			if(vo.getMem_num() != user.getMem_num()) {
				model.addAttribute("message","잘못된 접근 입니다.");
				model.addAttribute("url",request.getContextPath()+"/review/reviewMemList");
			}else {
				model.addAttribute("hos_name",hospitalService.selectHospital(vo.getHos_num()).getHos_name());
				model.addAttribute("reviewVO",vo);
				return "myModifyReviewWrite";
			}
	
		}
		
		return "common/resultAlert";
	}
	
	@PostMapping("/review/updateReview")
	public String updateReview(ReviewVO vo,HttpSession session, HttpServletRequest request,Model model) {
		MemberVO user= (MemberVO) session.getAttribute("user");
		if(user == null) {
			model.addAttribute("message","로그인 후 이용해주세요");
			model.addAttribute("url",request.getContextPath()+"/member/login");
		}else {
			ReviewVO db_vo = service.selectReviewDetail(vo.getRev_num());
			if(db_vo.getMem_num() != user.getMem_num()) {
				model.addAttribute("message","잘못된 접근 입니다.");
				model.addAttribute("url",request.getContextPath()+"/review/reviewMemList");
			}else {
				service.updateReview(vo);
				model.addAttribute("message","리뷰 수정이 완료되었습니다.");
				model.addAttribute("url",request.getContextPath()+"/review/reviewMemList");
			}
			
		}
		
		return "common/resultAlert";
	}
	
	
	@GetMapping("/review/deleteReview")
	public String deleteReviewForm(long rev_num,HttpSession session, HttpServletRequest request,Model model) {
		MemberVO user= (MemberVO) session.getAttribute("user");
		if(user == null) {
			model.addAttribute("message","로그인 후 이용해주세요");
			model.addAttribute("url",request.getContextPath()+"/member/login");
		}else {
			ReviewVO vo = service.selectReviewDetail(rev_num);
			if(vo.getMem_num() != user.getMem_num()) {
				model.addAttribute("message","잘못된 접근 입니다.");
				model.addAttribute("url",request.getContextPath()+"/review/reviewMemList");
			}else {
				service.deleteReview(rev_num);
				model.addAttribute("message","삭제되었습니다.");
				model.addAttribute("url",request.getContextPath()+"/review/reviewMemList");
			}
	
		}
		
		return "common/resultAlert";
	}
	
}
