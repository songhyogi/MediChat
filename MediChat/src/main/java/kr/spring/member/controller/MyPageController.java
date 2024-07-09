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
import kr.spring.member.vo.DoctorVO;
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
			public DoctorVO doctorVO() {
				return new DoctorVO();
			}
			 // 비대면 진료 신청 폼 호출
		    @GetMapping("/member/registerTreat")
		    public String form(Model model, HttpSession session) {
		        MemberVO user = (MemberVO) session.getAttribute("user");
		        DoctorVO doctorVO = new DoctorVO();
		        if (user != null) {
		            doctorVO.setMem_name(user.getMem_name());
		            doctorVO.setDoc_email(user.getMem_email());
		        }
		        model.addAttribute("doctorVO", doctorVO);
		        return "registerTreat";
		    }
			@PostMapping("/member/registerTreat")
			public String submit(@Valid DoctorVO doctorVO, BindingResult result, HttpServletRequest request, Model model, HttpSession session) {
			    log.debug("<비대면 진료 신청>" + doctorVO);

			    // 유효성 체크 결과 오류가 있다면 다시 폼으로
			    if (result.hasErrors()) {
			        return form(model,session);
			    }

			    // 현재 로그인한 사용자의 정보 가져오기
			    MemberVO user = (MemberVO) session.getAttribute("user");
			    
			    DoctorVO loggedInDoctor = doctorService.selectDoctor(user.getMem_num());
			    

			    // 입력한 비밀번호와 현재 로그인한 사용자의 비밀번호 비교
			    if (!loggedInDoctor.getDoc_passwd().equals(doctorVO.getDoc_passwd())) {
			        result.rejectValue("doc_passwd", "password.notMatch", "비밀번호가 일치하지 않습니다.");
			        return form(model,session);
			    }

			    // 비밀번호가 일치하면 처리 로직 추가

			    return "redirect:/member/docView"; // 성공적으로 처리된 경우 다른 페이지로 리다이렉트
			}
}








