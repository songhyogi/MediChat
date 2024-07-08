package kr.spring.member.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.json.JSONObject;
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
import kr.spring.util.AuthCheckException;
import kr.spring.util.CaptchaUtil;
import kr.spring.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MyPageController {
	@Autowired
	private MemberService memberService;
	

	/*==============================
	 * MY페이지
	 *==============================*/	
	@GetMapping("/member/myPage")
	public String process(HttpSession session,Model model) {
		MemberVO user = 
				(MemberVO)session.getAttribute("user");
		//회원정보
		MemberVO member = 
				memberService.selectMember(user.getMem_num());
		log.debug("<<MY페이지>> : " + member);
		
		model.addAttribute("member", member);
		  
		return "myPage";
	}


	/*==============================
	 * 프로필 사진 출력
	 *==============================*/	
	//프로필 사진 출력(로그인 전용)
	@GetMapping("/member/photoView")
	public String getProfile(HttpSession session,
			                 HttpServletRequest request,
			                 Model model) {
		MemberVO user = (MemberVO)session.getAttribute("user");
		log.debug("<<프로필 사진 출력>> : " + user);
		if(user==null) {//로그인이 되지 않은 경우
			getBasicProfileImage(request, model);
		}else {//로그인이 된 경우
			MemberVO memberVO = memberService.selectMember(
					                       user.getMem_num());
			viewProfile(memberVO, request, model);
		}		
		return "imageView";
	}
	
	//프로필 사진 출력(회원번호 지정)
	@GetMapping("/member/viewProfile")
	public String getProfileByMem_num(long mem_num,
			                 HttpServletRequest request,
			                 Model model) {
		MemberVO memberVO = 
				memberService.selectMember(mem_num);
		
		viewProfile(memberVO,request,model);
		
		return "imageView";
	}
	
	//프로필 사진 처리를 위한 공통 코드
	public void viewProfile(MemberVO memberVO,
			                HttpServletRequest request,
			                Model model) {
		if(memberVO==null || memberVO.getMem_photoname()==null) {
			//DB에 저장된 프로필 이미지가 없기 때문에 기본 이미지 호출
			getBasicProfileImage(request, model);
		}else {
			//업로드한 프로필 이미지 읽기
			model.addAttribute("imageFile", memberVO.getMem_photo());
			model.addAttribute("filename", memberVO.getMem_photoname());
		}
	}
	
	//기본 이미지 읽기
	public void getBasicProfileImage(HttpServletRequest request,
			                         Model model) {
		byte[] readbyte = FileUtil.getBytes(
				request.getServletContext().getRealPath(
						        "/image_bundle/face.png"));
		model.addAttribute("imageFile", readbyte);
		model.addAttribute("filename", "face.png");
	}
	//비대면 신청
		//로그 처리
			private static final Logger log = LoggerFactory.getLogger(MemberController.class);
			
			@ModelAttribute
			public MemberVO initCommand() {
				return new MemberVO();
			}
			//폼 호출
			@GetMapping("/member/registerTreat")
			public String form() {
				return "registerTreat";
			}
			@PostMapping("/member/registerTreat")
			public String submit(@Valid MemberVO member,BindingResult result,HttpServletRequest request,Model model,
											HttpSession session) {
				log.debug("<비대면 진료 신청>" + member);
				
				//유효성 체크 결과 오류가 있다면 다시 폼으로
				if(result.hasErrors()) {
					return form();
				}
				
				return "redirect:/member/detail";
			}

}








