package kr.spring.member.controller;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import kr.spring.hospital.service.HospitalService;
import kr.spring.hospital.vo.HospitalVO;
import kr.spring.member.service.DoctorService;
import kr.spring.member.vo.DoctorVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.CaptchaUtil;
import kr.spring.util.FileUtil;
import kr.spring.util.PagingUtil;
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
	private static final Logger log = LoggerFactory.getLogger(MemberController.class);
	//===========회원가입===========
	@ModelAttribute
	public DoctorVO initCommand() {
		return new DoctorVO();
	}
	//의사회원가입 폼 호출
	@GetMapping("doctor/registerDoc")
	public String form(/*
						 * int pageNum,String keyfield,String keyword,Model model, HttpSession session
						 */) {
		/*
		 * HospitalVO hos = (HospitalVO)session.getAttribute("hos"); Map<String, Object>
		 * map = new HashMap<String, Object>();
		 * 
		 * map.put("hos_num", hos.getHos_num()); map.put("keyfield", keyfield);
		 * map.put("keyword", keyword);
		 * 
		 * //총 개수 int count = doctorService.selectRowCount(map); //페이지 처리 PagingUtil
		 * page = new PagingUtil(keyfield,keyword,pageNum,count,10,10,"list");
		 * 
		 * List<HospitalVO> list = null; if(count > 0) { map.put("start",
		 * page.getStartRow()); map.put("end", page.getEndRow());
		 * 
		 * list = doctorService.getHosList(map); }
		 * 
		 * model.addAttribute("count",count); model.addAttribute("list",list);
		 * model.addAttribute("page",page.getPage());
		 */
		
		return "doctorRegister";
	}
	//의사회원가입 처리
	@PostMapping("doctor/registerDoc")
	public String submit(@Valid DoctorVO doctor,BindingResult result,
									HttpSession session,Model model) {
		if(result.hasErrors()) {
            return "doctorRegister";
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
}
