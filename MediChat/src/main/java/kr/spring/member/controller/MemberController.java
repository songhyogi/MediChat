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
			if(member!=null) {
				//비밀번호 일치확인
				check = member.checkPasswd(memberVO.getMem_passwd());
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
		session.invalidate();
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
		
		return "redirect:/member/login";
	}
	
	/*=============================
	 * 비밀번호 변경
	 ============================*/
	
	/*=============================
	 * 회원탈퇴
	 ============================*/
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
}











