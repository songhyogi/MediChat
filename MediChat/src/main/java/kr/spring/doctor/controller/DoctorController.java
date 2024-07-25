package kr.spring.doctor.controller;

import java.io.IOException;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.doctor.service.DoctorService;
import kr.spring.doctor.vo.DoctorVO;
import kr.spring.hospital.service.HospitalService;
import kr.spring.hospital.vo.HospitalVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.notification.service.NotificationService;
import kr.spring.notification.vo.NotificationVO;
import kr.spring.reservation.service.ReservationService;
import kr.spring.util.AuthCheckException;
import kr.spring.util.CaptchaUtil;
import kr.spring.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DoctorController {
	@Autowired
	DoctorService doctorService;
	@Autowired
	HospitalService hospitalService;
	@Autowired
	NotificationService notificationService;
	@Autowired
	private ReservationService reservationService;
	
	@Value("${API.KCY.X-Naver-Client-Id}")
	private String X_Naver_Client_Id;
	@Value("${API.KCY.X-Naver-Client-Secret}")
	private String X_Naver_Client_Secret;

	// 로그 처리
	private static final Logger log = LoggerFactory.getLogger(DoctorController.class);

	/*=============================
	 * 회원가입
	 ============================*/
	@ModelAttribute
	public DoctorVO initCommand() {
		return new DoctorVO();
	}

	// 의사회원가입 폼 호출
	@GetMapping("/doctor/registerDoc")
	public String form() {
		return "doctorRegister";
	}

	// 의사회원가입 처리
	@PostMapping("/doctor/registerDoc")
	public String submit(@Valid DoctorVO doctorVO, BindingResult result, HttpServletRequest request, HttpSession session,
			Model model) throws IllegalStateException, IOException {
		log.debug("<회원가입>" + doctorVO);

		if (result.hasErrors()) {
			return form();
		}
		
		// ========캡챠 시작=============//
		String code = "1";// 키 발급 0, 캡챠 이미지 비교시 1로 세팅
		// 캡챠 키 발급시 받은 키값
		String key = (String) session.getAttribute("captcha_key");
		// 사용자가 입력한 캡챠 이미지 글자값
		String value = doctorVO.getCaptcha_chars();
		String apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=" + code + "&key=" + key + "&value=" + value;

		Map<String, String> requestHeaders = new HashMap<String, String>();

		requestHeaders.put("X-Naver-Client-Id", X_Naver_Client_Id);
		requestHeaders.put("X-Naver-Client-Secret", X_Naver_Client_Secret);

		String responseBody = CaptchaUtil.get(apiURL, requestHeaders);

		log.debug("<<캡챠 결과>> : " + responseBody);

		// 변환 작업
		JSONObject jObject = new JSONObject(responseBody);
		boolean captcha_result = jObject.getBoolean("result");
		if (!captcha_result) {
			result.rejectValue("captcha_chars", "invalidCaptcha");
			return "doctorRegister";
		}
		// ========캡챠 끝=============//
		
		//의사 면허증 업로드 처리
	    doctorVO.setDoc_license(FileUtil.createFile(request, doctorVO.getDoc_upload()));
		//회원가입 처리
		doctorService.insertDoctor(doctorVO);
		
		model.addAttribute("message","성공적으로 회원가입 되었습니다.");
		model.addAttribute("url",request.getContextPath()+"/main/main");
		
		return "common/resultAlert";
	}

	/*=============================
	 * 로그인
	 ============================*/
	@GetMapping("/doctor/login")
	public String loginForm() {
		return "doctorLogin";
	}

	// 로그인 폼에서 전송된 데이터 처리
	@PostMapping("/doctor/login")
	public String loginSubmit(@Valid DoctorVO doctorVO, BindingResult result, HttpServletRequest request,
			HttpSession session) {
		log.debug("<로그인 정보> : " + doctorVO);

		// 아이디와 비밀번호만 유효성 체크
		if (result.hasFieldErrors("mem_id") || result.hasFieldErrors("doc_passwd")
												|| result.hasFieldErrors("doc_agree")) {
			return loginForm();
		}
		DoctorVO doctor = null;
		try {
			doctor = doctorService.checkId(doctorVO.getMem_id());
			boolean check = false;
			if (doctor != null && doctor.getDoc_passwd() != null) {
				// 비밀번호 확인
				check = doctor.checkPasswd(doctorVO.getDoc_passwd());
			} else {
				result.reject("notFoundUser");
				return loginForm();
			}
			if(doctor.getDoc_agree()!=1) {
				result.reject("notAgree");
				return loginForm();
			}
			if (check) {
				// =====자동 로그인 할까말까=====
				// =====자동 로그인 끝=====
				// 로그인 처리
				session.setAttribute("user", doctor);
				session.setAttribute("user_type", "doctor");
				/* ksy 알림 처리 시작 */
				int noti_cnt = notificationService.selectCountNotification(doctor.getMem_num());
				session.setAttribute("noti_cnt", noti_cnt);
				/* ksy 알림 처리 끝 */
				
				log.debug("<로그인 인증 성공> : " + doctor);

				return "redirect:/main/main";
			}
			// 인증 실패
			throw new AuthCheckException();
		} catch (AuthCheckException e) {
			// 인증 실패
			if (doctor != null && doctor.getMem_auth() == 1) {
				result.reject("noAuthority");
			} else {
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
		session.removeAttribute("user");
		// ====== 자동로그인 체크 시작 =======//
		// ====== 자동로그인 체크 끝 =======//

		return "redirect:/main/main";
	}

	/*=============================
	 * 의사회원정보 수정
	 ============================*/
	@GetMapping("/doctor/modifyDoctor")
	public String updateForm(DoctorVO doctorVO,HttpSession session,Model model) {
		
		DoctorVO user = (DoctorVO)session.getAttribute("user");
		
		log.debug("<<의사회원정보 수정 - 로그인 정보>> : " + user);
		
		if(user == null) {
			return "redirect:/main/main";
		}
		DoctorVO doctor = doctorService.selectDoctor(user.getDoc_num());
		HospitalVO hospital = hospitalService.selectHospital(user.getHos_num());
		
		model.addAttribute("hospital",hospital);
		model.addAttribute("doctorVO",doctor);
		
		return "doctorModify";
	}
	@PostMapping("/doctor/modifyDoctor")
	public String updateSubmit(@Valid DoctorVO doctorVO,BindingResult result,HttpSession session) {
		
		log.debug("<<의사회원정보 수정>> : "+doctorVO);
		
		if(result.hasFieldErrors("mem_name")||result.hasFieldErrors("doc_email")
					||result.hasFieldErrors("hos_num")) {
			return "doctorModify";
		}
		DoctorVO user = (DoctorVO)session.getAttribute("user");
		doctorVO.setMem_num(user.getMem_num());
		
		doctorService.updateDoctor(doctorVO);
		
		user.setHos_num(doctorVO.getHos_num());
		
		return "redirect:/main/main";
	}
	/*=============================
	 * 아이디 찾기
	 ============================*/
	@GetMapping("/doctor/doctorFindId")
	public String findDoctorIdForm() {
		return "doctorFindId";
	}
	@PostMapping("/doctor/doctorFindId")
	public String findDoctorIdSubmit(@Valid DoctorVO doctorVO,BindingResult result,HttpSession session,Model model) {
		//유효성 체크
		if(result.hasFieldErrors("mem_name")||result.hasFieldErrors("doc_email")) {
			return "doctorFindId";
		}
		//아이디 찾기
		DoctorVO doctor = doctorService.findId(doctorVO);
		
		log.debug("<<아이디 찾기 결과>> : " + doctor.getMem_id());
		
		model.addAttribute("mem_id",doctor.getMem_id());
		
		return "/doctor/doctorResultId";
	}
	/*============================= 
	 * 비밀번호 찾기(이메일 전송)
	 ============================*/
	@GetMapping("/doctor/sendDocPassword")
	public String sendPasswordForm() {
		return "doctorFindPassword";
	}
	
	/*============================= 
	 * 프로필 사진 출력 
	 ============================*/
	// 프로필 사진 출력(로그인 전용)
	@GetMapping("/doctor/docPhotoView")
	public String getProfile(HttpSession session, HttpServletRequest request, Model model) {
		DoctorVO user = (DoctorVO) session.getAttribute("user");
		log.debug("<<프로필 사진 출력>> : " + user);
		if (user == null) {// 로그인X
			getBasicProfileImage(request, model);// 기본이미지 불러오기
		} else {// 로그인O
			DoctorVO doctorVO = doctorService.selectDoctor(user.getMem_num());

			docViewProfile(doctorVO, request, model);
		}
		return "imageView";
	}

	// 프로필 사진 출력(회원번호 지정)
	@GetMapping("/doctor/docViewProfile")
	public String getProfileByMem_num(long mem_num, HttpServletRequest request, Model model) {
		DoctorVO doctorVO = doctorService.selectDoctor(mem_num);

		docViewProfile(doctorVO, request, model);

		return "imageView";
	}

	// 프로필 사진 처리를 위한 공통 코드
	public void docViewProfile(DoctorVO doctorVO, HttpServletRequest request, Model model) {
		if (doctorVO == null || doctorVO.getMem_photoname() == null) {
			// DB에 저장된 프로필 이미지가 없기 때문에 기본 이미지 로딩
			getBasicProfileImage(request, model);
		} else {
			// 업로드한 프로필 이미지 읽기
			model.addAttribute("imageFile", doctorVO.getMem_photo());
			model.addAttribute("filename", doctorVO.getMem_photoname());
		}

	}

	// 기본 이미지 읽기
	public void getBasicProfileImage(HttpServletRequest request, Model model) {

		byte[] readbyte = FileUtil.getBytes(request.getServletContext().getRealPath("/image_bundle/face.png"));

		model.addAttribute("imageFile", readbyte);
		model.addAttribute("filename", "face.png");
	}

	/*=============================
	 * 비밀번호 변경
	 ============================*/
	@GetMapping("/doctor/changePasswd")
	public String changePasswdForm() {
		return "doctorChangePasswd";
	}

	@PostMapping("/doctor/changePasswd")
	public String changePasswdSubmit(DoctorVO doctorVO, BindingResult result, HttpSession session, Model model,
			HttpServletRequest request) {

		if (result.hasFieldErrors("now_passwd") || result.hasFieldErrors("doc_passwd")) {
			return changePasswdForm();
		}
		// ========캡챠 시작=============//
		String code = "1";// 키 발급 0, 캡챠 이미지 비교시 1로 세팅
		// 캡챠 키 발급시 받은 키값
		String key = (String) session.getAttribute("captcha_key");
		// 사용자가 입력한 캡챠 이미지 글자값
		String value = doctorVO.getCaptcha_chars();
		String apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=" + code + "&key=" + key + "&value=" + value;

		Map<String, String> requestHeaders = new HashMap<String, String>();

		requestHeaders.put("X-Naver-Client-Id", X_Naver_Client_Id);
		requestHeaders.put("X-Naver-Client-Secret", X_Naver_Client_Secret);

		String responseBody = CaptchaUtil.get(apiURL, requestHeaders);

		log.debug("<<캡챠 결과>> : " + responseBody);

		// 변환 작업
		JSONObject jObject = new JSONObject(responseBody);
		boolean captcha_result = jObject.getBoolean("result");
		if (!captcha_result) {
			result.rejectValue("captcha_chars", "invalidCaptcha");
			return changePasswdForm();
		}
		// ========캡챠 끝=============//
		DoctorVO user = (DoctorVO) session.getAttribute("user");
		doctorVO.setMem_num(user.getMem_num());

		DoctorVO db_doctor = doctorService.selectDoctor(doctorVO.getMem_num());

		if (!db_doctor.getDoc_passwd().equals(doctorVO.getNow_passwd())) {
			result.rejectValue("now_passwd", "invalidPasswd");
			return changePasswdForm();
		}
		// 비밀번호 수정
		doctorService.updateDocPasswd(doctorVO);

		return "redirect:/main/main";
	}

	/*=============================
	 * 의사회원탈퇴
	 ============================*/
	@GetMapping("/doctor/deleteDoctor")
	public String deleteForm(HttpSession session) {
		DoctorVO user = (DoctorVO) session.getAttribute("user");
		if (user == null || user.getMem_auth() != 3) {
			return "redirect:/doctor/login";
		}
		return "doctorDelete";
	}

	// 탈퇴 폼에서 전송된 데이터 처리
	@PostMapping("/doctor/deleteDoctor")
	public String deleteSubmit(@Valid DoctorVO doctorVO, BindingResult result, HttpSession session) {

		// 아이디와 비밀번호 유효성 체크
		if (result.hasFieldErrors("mem_id") || result.hasFieldErrors("doc_passwd")
				|| result.hasFieldErrors("doc_email")) {
			return "doctorDelete";
		}
		// ========캡챠 시작=============//
		String code = "1";// 키 발급 0, 캡챠 이미지 비교시 1로 세팅
		// 캡챠 키 발급시 받은 키값
		String key = (String) session.getAttribute("captcha_key");
		// 사용자가 입력한 캡챠 이미지 글자값
		String value = doctorVO.getCaptcha_chars();
		String apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=" + code + "&key=" + key + "&value=" + value;

		Map<String, String> requestHeaders = new HashMap<String, String>();

		requestHeaders.put("X-Naver-Client-Id", X_Naver_Client_Id);
		requestHeaders.put("X-Naver-Client-Secret", X_Naver_Client_Secret);

		String responseBody = CaptchaUtil.get(apiURL, requestHeaders);

		log.debug("<<캡챠 결과>> : " + responseBody);

		// 변환 작업
		JSONObject jObject = new JSONObject(responseBody);
		boolean captcha_result = jObject.getBoolean("result");
		if (!captcha_result) {
			result.rejectValue("captcha_chars", "invalidCaptcha");
			return "doctorRegister";
		}
		// ========캡챠 끝=============//
		DoctorVO user = (DoctorVO) session.getAttribute("user");
		doctorVO.setMem_num(user.getMem_num());
		// 회원 탈퇴
		doctorService.deleteDoctor(doctorVO.getMem_num());
		doctorService.deleteDoctor_detail(doctorVO);

		session.invalidate();

		return "redirect:/main/main";
	}

	/*=============================
	 * 의사회원가입신청 관리자 승인
	 ============================*/
	@GetMapping("/doctor/agree")
	public String agreeForm(Long doc_num, Long mem_num, HttpSession session, Model model) {

		DoctorVO user = (DoctorVO) session.getAttribute("user");

		if (user == null || user.getMem_auth() != 9) {
			return "redirect:/main/main";
		} else {
			Map<String, Object> map = new HashMap<String, Object>();

			List<DoctorVO> docList = doctorService.docList(map);

			model.addAttribute("docList", docList);
		}
		return "adminAgree";
	}

	/*============================= 
	 * 의사회원상세 (관리자 승인 조회용)
	 ============================*/
	@GetMapping("/doctor/doctorDetail")
	public String detailForm(Long mem_num, HttpSession session, Model model) {

		DoctorVO user = (DoctorVO) session.getAttribute("user");
		if (user == null || user.getMem_auth() != 9) {
			return "redirect:/main/main";
		} else {
			DoctorVO doctor = doctorService.selectDoctor(mem_num);
			HospitalVO hospital = hospitalService.selectHospital(doctor.getHos_num());

			model.addAttribute("doctor", doctor);
			model.addAttribute("hospital", hospital);
		}
		return "doctorDetail";
	}

	/*=============================
	 * 캡챠 API
	 ============================*/
	@GetMapping("/doctor/getCaptcha")
	public String getCaptcha(Model model, HttpSession session) {

		String code = "0";
		String key_apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=" + code;

		Map<String, String> requestHeaders = new HashMap<String, String>();

		requestHeaders.put("X-Naver-Client-Id", X_Naver_Client_Id);
		requestHeaders.put("X-Naver-Client-Secret", X_Naver_Client_Secret);

		String responseBody = CaptchaUtil.get(key_apiURL, requestHeaders);

		log.debug("<<responseBody>> : " + responseBody);

		JSONObject jObject = new JSONObject(responseBody);
		try {
			// https://openapi.naver.com/v1/captcha/nkey 호출로 받은 key값
			String key = jObject.getString("key");
			session.setAttribute("captcha_key", key);

			String apiURL = "https://openapi.naver.com/v1/captcha/ncaptcha.bin?key=" + key;

			byte[] response_byte = CaptchaUtil.getCaptchaImage(apiURL, requestHeaders);

			model.addAttribute("imageFile", response_byte);
			model.addAttribute("filename", "captcha.jpg");
		} catch (Exception e) {
			log.error(e.toString());
		}
		return "imageView";
	}

	/*=============================
	 * MyPage
	 ============================*/
	@GetMapping("/doctor/docPage")
	public String process(HttpSession session, Model model) {
	    DoctorVO user = (DoctorVO) session.getAttribute("user");
	    if(user == null) {
	        return "redirect:/login";
	    }
	    try{
	        DoctorVO doctor = doctorService.selectDoctor(user.getDoc_num());
	        if(doctor != null) {
	            int count = reservationService.selectCountByMem(user.getDoc_num());
	            HospitalVO hospital = hospitalService.selectHospital(doctor.getHos_num());
	            doctor.setReservationCount(count);
	            log.debug("<<MY페이지>> : " + doctor);
	            model.addAttribute("doctor", doctor);
	            model.addAttribute("count", count);
	            model.addAttribute("hospital", hospital);
	            return "docPage";
	        }else{
	            return "redirect:/login";
	        }
	    }catch(Exception e) {
	        return "redirect:/login";
	    }
	}
	@GetMapping("/mypage/docInfo")
	public String process1(HttpSession session,Model model) {
		DoctorVO user = (DoctorVO)session.getAttribute("user");
		if(user == null) {
		   return "login";
		}
		//회원정보
		DoctorVO doctor = doctorService.selectDoctor(user.getMem_num());
		HospitalVO hospital = hospitalService.selectHospital(doctor.getHos_num());
		log.debug("<<MY페이지>> : " + doctor);
		  
		model.addAttribute("doctor", doctor);
		model.addAttribute("hospital", hospital);
		return "docInfo";
	}
	@GetMapping("/member/docReserve")
	public String process3(HttpSession session,Model model) {
		DoctorVO user = (DoctorVO)session.getAttribute("user");
		if(user == null) {
			return "login";
		}
		//회원정보
		DoctorVO doctor = doctorService.selectDoctor(user.getDoc_num());
  
		log.debug("<<MY페이지>> : " + doctor);
  
		model.addAttribute("doctor", doctor);
    
		return "docReserve";
	}
	/*===========================
	* 비대면 진료 신청
	============================*/
   @GetMapping("/doctor/registerTreat")
   public String registerTreatForm(Model model, HttpSession session) {
       DoctorVO user = (DoctorVO) session.getAttribute("user");
       if (user == null) {
           return "redirect:/login"; // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
       }

       DoctorVO doctor = doctorService.selectDoctor(user.getDoc_num());
       if (doctor == null) {
           // 의사 정보를 가져오지 못한 경우 적절히 처리 (예: 오류 페이지로 리다이렉트)
           return "redirect:/error"; // 예시로 오류 페이지로 리다이렉트
       }

       doctor.setMem_name(user.getMem_name());
       doctor.setDoc_email(user.getDoc_email());
       model.addAttribute("doctor", doctor);

       return "registerTreat";
   }
   @PostMapping("/doctor/registerTreat")
   public String registerTreatSubmit(@ModelAttribute("doctorVO") @Valid DoctorVO doctorVO,
                                     BindingResult result,
                                     HttpSession session,
                                     Model model) {

       log.debug("<비대면 진료 신청>" + doctorVO);

       if (result.hasFieldErrors("now_passwd") || result.hasFieldErrors("doc_passwd")) {
           return registerTreatForm(model, session); // 유효성 검사 에러가 있으면 폼으로 되돌림
       }

       DoctorVO user = (DoctorVO) session.getAttribute("user");
       if (user == null) {
           return "redirect:/login"; // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
       }

       DoctorVO loggedInDoctor = doctorService.selectDoctor(user.getDoc_num());

       if (loggedInDoctor == null || !loggedInDoctor.checkPasswd(doctorVO.getNow_passwd())) {
           result.rejectValue("now_passwd", "invalidPasswd", "현재 비밀번호가 일치하지 않습니다."); // 에러 메시지 설정
           return registerTreatForm(model, session); // 비밀번호가 일치하지 않으면 폼으로 되돌림
       }
       doctorVO.setDoc_num(user.getDoc_num());
       doctorService.updateDoctorTreat(doctorVO); // 비밀번호가 일치하면 진료 정보 업데이트
       
       /* 정보 수정 후 세션에 다시 값 설정 */
       user.setDoc_treat(1);
       session.setAttribute("user",user);
       return "redirect:/doctor/docPage"; // 성공적으로 처리된 경우 다른 페이지로 리다이렉트
   }

}
