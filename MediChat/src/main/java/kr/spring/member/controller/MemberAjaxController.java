package kr.spring.member.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.member.email.Email;
import kr.spring.member.email.EmailSender;
import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberAjaxController {
	@Autowired
	private EmailSender emailSender;
	@Autowired
	private Email email;
	@Autowired
	private MemberService memberService;
	
	//아아디 중복확인
	@GetMapping("/member/confirmId") 
	@ResponseBody
	public Map<String,String> processMember(@RequestParam String mem_id){
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		MemberVO member = memberService.checkId(mem_id);
		if(member!=null) {
			//중복O
			mapAjax.put("result", "idDuplicated");
		}else {
			//중복X
			if(!Pattern.matches("^[A-Za-z0-9]{4,12}$",mem_id)) {
				//패턴 틀림
				mapAjax.put("result", "notMatchPattern");
			}else {
				//미중복
				mapAjax.put("result", "idNotFound");
			}
		}
		return mapAjax;
	}
	//프로필 사진 업로드
	@PostMapping("/member/updateMemPhoto")
	@ResponseBody
	public Map<String, String> processProfile(MemberVO memberVO,HttpSession session){
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		if(user==null) {
			mapAjax.put("result","logout");
		}else {
			memberVO.setMem_num(user.getMem_num());
			memberService.updateProfile(memberVO);

			mapAjax.put("result","success");
		}
		return mapAjax;
	}
	//비밀번호 찾기
	@PostMapping("/member/getPasswordInfo")
	@ResponseBody
	public Map<String, String> sendEmailAction(MemberVO memberVO){
		log.debug("<<비밀번호 찾기>> : " + memberVO);
		
		Map<String, String> mapJson = new HashMap<String, String>();
		
		MemberVO member = memberService.checkId(memberVO.getMem_id());

		if(member!=null && member.getMem_auth()>1 && member.getMem_email().equals(memberVO.getMem_email())) {
			//오류를 대비해서 원래 비밀번호 저장
			String origin_passwd = member.getMem_passwd();
			//기존비밀번호를 임시비밀번호로 변경
			String mem_passwd = StringUtil.randomPassword(10);
			member.setMem_passwd(mem_passwd);
			//변경된 임시 비밀번호를 DB에 저장
			memberService.findPasswd(member);
			
			email.setContent("임시 비밀번호는 "+mem_passwd+" 입니다.");
			email.setReceiver(member.getMem_email());
			email.setSubject(member.getMem_id()+" 님 비밀번호 찾기 메일입니다.");
			
			try {
				emailSender.sendEmail(email);
				mapJson.put("result", "success");
			}catch(Exception e) {
				log.error("<<비밀번호 찾기>> : " + e.toString());
				//오류 발생시 비밀번호 원상복구
				member.setMem_passwd(origin_passwd);
				memberService.findPasswd(member);
				mapJson.put("result", "failure");
			}
		}else if(member!=null && member.getMem_auth()==1) {
			//정지회원
			mapJson.put("result", "noAuthority");
		}else {
			mapJson.put("result", "invalidInfo");
		}
		return mapJson;
	}
}
