package kr.spring.member.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class MemberController {
	@Autowired 
	MemberService memberService;
 
	@Value("${API.KCY.X-Naver-Client-Id}")
	private String X_Naver_Client_Id;
	@Value("${API.KCY.X-Naver-Client-Secret}")
	private String X_Naver_Client_Secret;

	//로그 처리
	private static final Logger log = LoggerFactory.getLogger(MemberController.class);
	//===========회원가입===========
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
	public String submit(@Valid MemberVO member,BindingResult result,HttpServletRequest request,Model model,
									HttpSession session) {
		log.debug("<회원가입>" + member);

		//유효성 체크 결과 오류가 있다면 다시 폼으로
		if(result.hasErrors()) {
			return form();
		}
		//========캡챠 시작=============//
		String code = "1";//키 발급 0, 캡챠 이미지 비교시 1로 세팅
		//캡챠 키 발급시 받은 키값
		String key = (String)session.getAttribute("captcha_key");
		//사용자가 입력한 캡챠 이미지 글자값
		String value = member.getCaptcha_chars();
		String apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=" + code + "&key=" + key + "&value=" + value;

		Map<String, String> requestHeaders = new HashMap<String, String>();

		requestHeaders.put("X-Naver-Client-Id", X_Naver_Client_Id);
		requestHeaders.put("X-Naver-Client-Secret", X_Naver_Client_Secret);

		String responseBody = CaptchaUtil.get(apiURL, requestHeaders);

		log.debug("<<캡챠 결과>> : " + responseBody);

		//변환 작업
		JSONObject jObject = new JSONObject(responseBody);
		boolean captcha_result = jObject.getBoolean("result");
		if(!captcha_result) {
			result.rejectValue("captcha_chars", "invalidCaptcha");
			return form();
		}
		//========캡챠 끝=============//

		//회원가입
		memberService.insertMember(member);

		return "redirect:/main/main";
	}
	//===========로그인===========
	@GetMapping("/member/login")
	public String loginForm() {
		return "memberLogin";
	}
	//전송된 데이터 처리
	@PostMapping("/member/login")
	public String loginSubmit(@Valid MemberVO memberVO,BindingResult result,
											HttpServletRequest request,HttpSession session) {
		log.debug("<로그인 정보> : " +memberVO);
		
		//아이디와 비밀번호만 유효성 체크하여 오류가 있다면 폼으로
		if(result.hasFieldErrors("mem_id") || result.hasFieldErrors("mem_passwd")) {
			return loginForm();
		}
		MemberVO member = null;
		try {
			member = memberService.checkId(memberVO.getMem_id());
			boolean check = false;
			if(member!=null && member.getMem_passwd() != null) {
				//비밀번호 일치확인
				check = member.checkPasswd(memberVO.getMem_passwd());
			}else {
				result.reject("notFoundUser");
				return loginForm();
			}
			if(check) {
				//=====자동 로그인 시작=====
				//=====자동 로그인 끝=====
				//로그인 처리
				session.setAttribute("user", member);

				log.debug("<인증 성공> : "+ member);

				return "redirect:/main/main";
			}
			//인증 실패
			throw new AuthCheckException();
		}catch(AuthCheckException e) {
			//인증 실패
			if(member!=null && member.getMem_auth()==1) {
				result.reject("noAuthority");
			}else {
				result.reject("invalidIdOrPassword");
			}
			log.debug("<인증 실패>");

			return loginForm();
		}
	}
	/*=============================
	 * 로그아웃
	 ============================*/
	@GetMapping("/member/logout")
	public String processLogout(HttpSession session) {	
		//로그아웃
		session.removeAttribute("user");
		//====== 자동로그인 체크 시작 =======//
		//====== 자동로그인 체크 끝 =======//

		return "redirect:/main/main";
	}
	/*=============================
	 * 회원정보 수정
	 ============================*/
	//회정정보 수정 폼 호출
	@GetMapping("/member/modifyUser")
	public String updateForm(HttpSession session,Model model) {
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user == null) {
	         model.addAttribute("message","로그인이 필요합니다.");
	         model.addAttribute("url","/member/login");
	         return "/common/resultAlert";
	    }
		MemberVO memberVO = memberService.selectMember(user.getMem_num());

		model.addAttribute("memberVO",memberVO);

		return "memberModify";
	}
	//회정정보 수정 폼에서 전송된 데이터 처리
	@PostMapping("member/modifyUser")
	public String updateSubmit(@Valid MemberVO memberVO,BindingResult result,HttpSession session) {
		log.debug("<회원정보 수정> : " + memberVO);
		//유효성 체크
		if(result.hasErrors()) {
			return "memberModify";
		}
		MemberVO user = (MemberVO)session.getAttribute("user");
		memberVO.setMem_num(user.getMem_num());
		//회원정보 수정
		memberService.updateMember(memberVO);
		//세선에 저장된 정보 변경
		user.setMem_email(memberVO.getMem_email());

		return "myPage";
	}

	/*=============================
	 * 비밀번호 변경
	 ============================*/
	@GetMapping("/member/changePasswd")
	public String changePasswdForm(HttpSession session,Model model) {
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user == null) {
	         model.addAttribute("message","로그인이 필요합니다.");
	         model.addAttribute("url","/member/login");
	         return "/common/resultAlert";
	    }
		return "memberChangePasswd";
	}
	@PostMapping("/member/changePasswd")
	public String changePasswdSubmit(MemberVO memberVO,BindingResult result,HttpSession session,
										Model model,HttpServletRequest request) {
		if(result.hasFieldErrors("now_passwd")||result.hasFieldErrors("mem_passwd")) {
			return "memberChangePasswd";
		}
		//========캡챠 시작=============//
		String code = "1";//키 발급 0, 캡챠 이미지 비교시 1로 세팅
		//캡챠 키 발급시 받은 키값
		String key = (String)session.getAttribute("captcha_key");
		//사용자가 입력한 캡챠 이미지 글자값
		String value = memberVO.getCaptcha_chars();
		String apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=" + code + "&key=" + key + "&value=" + value;

		Map<String, String> requestHeaders = new HashMap<String, String>();

		requestHeaders.put("X-Naver-Client-Id", X_Naver_Client_Id);
		requestHeaders.put("X-Naver-Client-Secret", X_Naver_Client_Secret);

		String responseBody = CaptchaUtil.get(apiURL, requestHeaders);

		log.debug("<<캡챠 결과>> : " + responseBody);

		//변환 작업
		JSONObject jObject = new JSONObject(responseBody);
		boolean captcha_result = jObject.getBoolean("result");
		if(!captcha_result) {
			result.rejectValue("captcha_chars", "invalidCaptcha");
			return "memberChangePasswd";
		}
		//========캡챠 끝=============//
		MemberVO user = (MemberVO)session.getAttribute("user");
		memberVO.setMem_num(user.getMem_num());
		
		MemberVO db_member = memberService.selectMember(memberVO.getMem_num());
		
		//비밀번호 일치 여부 확인
		if(!db_member.getMem_passwd().equals(memberVO.getNow_passwd())) {
			result.rejectValue("now_passwd", "invalidPasswd");
			return "memberChangePasswd";
		}
		//비밀번호 수정
		memberService.updatePasswd(memberVO);
		
		//==================자동 로그인 해제 시작=====================
		//==================자동 로그인 해제 끝 =====================

		return "redirect:/member/myPage";
	}
	/*=============================
	 * 회원탈퇴
	 ============================*/
	@GetMapping("/member/deleteUser")
	public String deleteForm(HttpSession session,Model model) {
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user == null) {
	         model.addAttribute("message","로그인이 필요합니다.");
	         model.addAttribute("url","/member/login");
	         return "/common/resultAlert";
	    }
		return "memberDelete";
	}
	//탈퇴 폼에서 전송된 데이터 처리
	@PostMapping("/member/deleteUser")
	public String deleteSubmit(@Valid MemberVO memberVO,BindingResult result,HttpSession session) {
		
		//아이디와 비밀번호만 유효성 체크하여 오류가 있다면 폼으로
		if(result.hasFieldErrors("mem_id") || result.hasFieldErrors("mem_passwd")
												||result.hasFieldErrors("mem_email")) {
			return "memberDelete";
		}
		
		//========캡챠 시작=============//
		String code = "1";//키 발급 0, 캡챠 이미지 비교시 1로 세팅
		//캡챠 키 발급시 받은 키값
		String key = (String)session.getAttribute("captcha_key");
		//사용자가 입력한 캡챠 이미지 글자값
		String value = memberVO.getCaptcha_chars();
		String apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=" + code + "&key=" + key + "&value=" + value;

		Map<String, String> requestHeaders = new HashMap<String, String>();

		requestHeaders.put("X-Naver-Client-Id", X_Naver_Client_Id);
		requestHeaders.put("X-Naver-Client-Secret", X_Naver_Client_Secret);

		String responseBody = CaptchaUtil.get(apiURL, requestHeaders);

		log.debug("<<캡챠 결과>> : " + responseBody);

		//변환 작업
		JSONObject jObject = new JSONObject(responseBody);
		boolean captcha_result = jObject.getBoolean("result");
		if(!captcha_result) {
			result.rejectValue("captcha_chars", "invalidCaptcha");
			return "memberDelete";
		}
		//========캡챠 끝=============//
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		memberVO.setMem_num(user.getMem_num());
		//회원 탈퇴
		memberService.deleteMember(memberVO.getMem_num());
		memberService.deleteMember_detail(memberVO);
		
		session.invalidate();
		
		return "redirect:/main/main";
	}
	/*=============================
	 * 프로필 사진 출력
	 ============================*/
	//프로필 사진 출력(로그인 전용)
	@GetMapping("/member/memPhotoView")
	public String getProfile(HttpSession session,HttpServletRequest request,Model model) {
		MemberVO user = (MemberVO)session.getAttribute("user");
		log.debug("<<프로필 사진 출력>> : " + user);
		if(user==null) {//로그인X
			getBasicProfileImage(request, model);//기본이미지 불러오기
		}else {//로그인O
			MemberVO memberVO = memberService.selectMember(user.getMem_num());
			
			memViewProfile(memberVO, request, model);
		}
		return "imageView";
	}
	
	//프로필 사진 출력(회원번호 지정)
	@GetMapping("/member/memViewProfile")
	public String getProfileByMem_num(long mem_num,HttpServletRequest request,Model model) {
		MemberVO memberVO = memberService.selectMember(mem_num);
		
		memViewProfile(memberVO,request,model);
		
		return "imageView";
	}
	
	//프로필 사진 처리를 위한 공통 코드
	public void memViewProfile(MemberVO memberVO,HttpServletRequest request,Model model) {
		if(memberVO == null || memberVO.getMem_photoname() == null) {
			//DB에 저장된 프로필 이미지가 없기 때문에 기본 이미지 로딩
			getBasicProfileImage(request, model);
		}else {
			//업로드한 프로필 이미지 읽기
			model.addAttribute("imageFile",memberVO.getMem_photo());
			model.addAttribute("filename","face.png");
		}
		
	}
	//기본 이미지 읽기
	public void getBasicProfileImage(HttpServletRequest request,Model model) {
		
		byte[] readbyte = FileUtil.getBytes(request.getServletContext().getRealPath("/image_bundle/face.png"));
		
		model.addAttribute("imageFile",readbyte);
		model.addAttribute("filename","face.png");	
	}
	
	/*=============================
	 * 캡챠 API
	 ============================*/
	@GetMapping("/member/getCaptcha")
	public String getCaptcha(Model model,HttpSession session) {

		String code = "0";
		String key_apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=" + code;

		Map<String, String> requestHeaders = new HashMap<String, String>();


		requestHeaders.put("X-Naver-Client-Id", X_Naver_Client_Id);
		requestHeaders.put("X-Naver-Client-Secret", X_Naver_Client_Secret);

		String responseBody = CaptchaUtil.get(key_apiURL, requestHeaders);

		log.debug("<<responseBody>> : " + responseBody);

		JSONObject jObject = new JSONObject(responseBody);
		try {
			//https://openapi.naver.com/v1/captcha/nkey 호출로 받은 key값
			String key = jObject.getString("key");
			session.setAttribute("captcha_key", key);

			String apiURL = "https://openapi.naver.com/v1/captcha/ncaptcha.bin?key=" + key;

			byte[] response_byte = CaptchaUtil.getCaptchaImage(apiURL, requestHeaders);

			model.addAttribute("imageFile", response_byte);
			model.addAttribute("filename", "captcha.jpg");
		}catch(Exception e) {
			log.error(e.toString());
		}
		return "imageView";
	}
	/*=============================
	 * MyPage
    ============================*/
	@GetMapping("/member/myPage")
	public String process(HttpSession session,Model model) {
		MemberVO user = (MemberVO)session.getAttribute("user");
		//회원정보
		MemberVO member = memberService.selectMember(user.getMem_num());
		
		log.debug("<<MY페이지>> : " + member);
      
		model.addAttribute("member", member);
        
		return "myPage";
	}
	@GetMapping("/mypage/memberInfo")
   public String process1(HttpSession session,Model model) {
      MemberVO user = (MemberVO)session.getAttribute("user");
      if(user == null) {
         return "login";
      }
      //회원정보
      MemberVO member = memberService.selectMember(user.getMem_num());
      
      log.debug("<<MY페이지>> : " + member);
      
      model.addAttribute("member", member);
        
      return "memberInfo";
   }
   @GetMapping("/drug/memberDrugList")
   public String process2(HttpSession session,Model model) {
      MemberVO user = (MemberVO)session.getAttribute("user");
      if(user == null) {
         return "login";
      }
      //회원정보
      MemberVO member = memberService.selectMember(user.getMem_num());
      
      log.debug("<<MY페이지>> : " + member);
      
      model.addAttribute("member", member);
        
      return "memberDrugList";
   }
   @GetMapping("/mypage/reviewHistory")
   public String process3(HttpSession session,Model model) {
      MemberVO user = (MemberVO)session.getAttribute("user");
      if(user == null) {
         return "login";
      }
      //회원정보
      MemberVO member = memberService.selectMember(user.getMem_num());
      
      log.debug("<<MY페이지>> : " + member);
      
      model.addAttribute("member", member);
        
      return "reviewHistory";
   }
}