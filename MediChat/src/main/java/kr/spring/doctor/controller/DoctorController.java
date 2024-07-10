package kr.spring.doctor.controller;

import java.util.HashMap;
import java.util.List;
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

import kr.spring.doctor.service.DoctorService;
import kr.spring.doctor.vo.DoctorVO;
import kr.spring.hospital.service.HospitalService;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.AuthCheckException;
import kr.spring.util.CaptchaUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DoctorController {
	@Autowired
	DoctorService doctorService;
	@Autowired
	HospitalService hospitalService;
	
	@Value("${API.KCY.X-Naver-Client-Id}")
	private String X_Naver_Client_Id;
	@Value("${API.KCY.X-Naver-Client-Secret}")
	private String X_Naver_Client_Secret;
	
	//로그 처리
	private static final Logger log = LoggerFactory.getLogger(DoctorController.class);
	//===========회원가입===========
	@ModelAttribute
	public DoctorVO initCommand() {
		return new DoctorVO();
	}
	//의사회원가입 폼 호출
	@GetMapping("/doctor/registerDoc")
	public String form() {
		return "doctorRegister";
	}
	//의사회원가입 처리
	@PostMapping("/doctor/registerDoc")
	public String submit(@Valid DoctorVO doctor,BindingResult result,HttpServletRequest request,
									HttpSession session,Model model) {
		log.debug("<회원가입>" + doctor);
		
		if(result.hasErrors()) {
            return form();
		}
		//========캡챠 시작=============//
		String code = "1";//키 발급 0, 캡챠 이미지 비교시 1로 세팅
		//캡챠 키 발급시 받은 키값
		String key = (String)session.getAttribute("captcha_key");
		//사용자가 입력한 캡챠 이미지 글자값
		String value = doctor.getCaptcha_chars();
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
			return "doctorRegister";
		}
		//========캡챠 끝=============//
		//회원가입 처리
		doctorService.insertDoctor(doctor);
		
		return "redirect:/main/main";
	}
	/*=============================
	 * 로그인
	 ============================*/
	@GetMapping("/doctor/login")
	public String loginForm() {
		return "doctorLogin";
	}
	//로그인 폼에서 전송된 데이터 처리
	@PostMapping("/doctor/login")
	public String loginSubmit(@Valid DoctorVO doctorVO,BindingResult result,HttpServletRequest request,
									HttpSession session) {
		log.debug("<로그인 정보> : " + doctorVO);
		
		//아이디와 비밀번호만 유효성 체크
		if(result.hasFieldErrors("mem_id")||result.hasFieldErrors("doc_passwd")) {
			return loginForm();
		}
		DoctorVO doctor = null;
		try {
			doctor = doctorService.checkId(doctorVO.getMem_id());
			boolean check = false;
			if(doctor!=null) {
				//비밀번호 확인
				check = doctor.checkPasswd(doctorVO.getDoc_passwd());
			}
			if(check) {
				//=====자동 로그인 할까말까=====
				//=====자동 로그인 끝=====
				//로그인 처리
				session.setAttribute("user", doctor);
				
				log.debug("<로그인 인증 성공> : "+doctor);
				
				return "redirect:/main/main";
			}
			//인증 실패
			throw new AuthCheckException();
		}catch(AuthCheckException e) {
			//인증 실패
			if(doctor!=null && doctor.getMem_auth() == 1) {
				result.reject("noAuthority");
			}else {
				result.reject("invalidIdOrPassword");
			}
		}
		log.debug("<인증 실패>");
		
		return loginForm();
	}
	/*=============================
	 * 로그아웃
	 ============================*/
	@GetMapping("/doctor/logout")
	public String processLogout(HttpSession session) {
		session.removeAttribute("doctor");
		//====== 자동로그인 체크 시작 =======//
		//====== 자동로그인 체크 끝 =======//
		
		return "redirect:/main/main";
	}
	/*=============================
	 * 의사회원정보 수정
	 ============================*/
	
	
	/*=============================
	 * 비밀번호 변경
	 ============================*/
	
	/*=============================
	 * 의사회원 탈퇴
	 ============================*/
	
	/*=============================
	 * 의사회원가입 관리자 승인
	 ============================*/
	@GetMapping("/doctor/agree")
	public String agreeForm(Long doc_num,Long mem_num,HttpSession session,Model model) {
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		DoctorVO doctor = (DoctorVO)session.getAttribute("doctor");
		if(user==null || user.getMem_auth()!=9) {
			return "redirect:/main/main";
		}else {
			Map<String, Object> map = new HashMap<String, Object>();
			List<DoctorVO> docList = null;

			docList = doctorService.docList(map);
			

			model.addAttribute("docList",docList);
		}
		return "adminAgree";
	}
	
	/*=============================
	 * 캡챠 API
	 ============================*/
	@GetMapping("/doctor/getCaptcha")
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
	@GetMapping("/doctor/myPage")
	public String process(HttpSession session,Model model) {
		DoctorVO user = (DoctorVO)session.getAttribute("user");
		//회원정보
		DoctorVO doctor = doctorService.selectDoctor(user.getDoc_num());
		
		log.debug("<<MY페이지>> : " + doctor);
      
		model.addAttribute("doctor", doctor);
        
		return "docPage";
	}
}
