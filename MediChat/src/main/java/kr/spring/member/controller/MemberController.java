package kr.spring.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberController {
	@Autowired MemberService memberService;
	//로그 처리
	private static final Logger log = LoggerFactory.getLogger(MemberController.class);
	//===========회원가입====================
	@ModelAttribute
	public MemberVO initCommand() {
		return new MemberVO();
	}
	//폼 호출
	@GetMapping("/member/registerUser")
	public String form() {
		return "memberRegister";
	}
	//회원가입 처리
	@PostMapping("/member/registerUser")
	public String submit(@Valid MemberVO member,BindingResult result,HttpServletRequest request,Model model) {
		log.debug("<회원가입>" + member);
		
		//유효성 체크 결과 오류가 있다면 다시 폼으로
		if(result.hasErrors()) {
			return form();
		}
		//========캡챠 시작=============//
		
		//========캡챠 끝=============//
		
		//회원가입
		memberService.insertMember(member);
		
		return "redirect:/main/main";
	}
}











