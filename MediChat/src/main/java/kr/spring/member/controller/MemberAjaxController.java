package kr.spring.member.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberAjaxController {
	@Autowired
	private MemberService memberService;
	
	//아아디 중복확인
	@GetMapping("/member/confirmId")
	@ResponseBody
	public Map<String,String> process(@RequestParam String mem_id){
		
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
}
