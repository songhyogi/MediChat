package kr.spring.consulting.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.consulting.service.ConsultingService;
import kr.spring.consulting.vo.Con_ReVO;
import kr.spring.consulting.vo.ConsultingVO;
import kr.spring.doctor.vo.DoctorVO;
import kr.spring.member.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ConsultingController {
	
	@Autowired
	private ConsultingService consultingService;
	
	@GetMapping("/consultings")
	public String consultings(Model model, @RequestParam(defaultValue="1") int pageNum, @RequestParam(defaultValue="5") int pageItemNum, @RequestParam(defaultValue="0") int con_type) {
		Map<String,Object> map = new HashMap<>();
		map.put("pageNum", pageNum);
		map.put("pageItemNum", pageItemNum);
		map.put("con_type", con_type);		
		
		List<ConsultingVO> consultingList = consultingService.getListConsulting(map);
		model.addAttribute("consultingList", consultingList);
		
		log.debug("<<상담 리스트>> : " + consultingList);
		
		return "consultings";
	}
	
	@GetMapping("/consultings/create")
	public String createForm(HttpSession session,Model model) {
		Object user = session.getAttribute("user");
		
		if(user!=null) {
			if(user.getClass().equals(MemberVO.class)) {
				return "cWrite";
			} else if(user.getClass().equals(DoctorVO.class)) {
				model.addAttribute("url", "/main/main");
				model.addAttribute("message","의사 회원은 글을 작성하실 수 없습니다.");
				return "/common/resultAlert";
			} else {
				return "404Error";
			}
		} else {
			model.addAttribute("url", "/member/login");
			model.addAttribute("message", "회원만 이용 가능한 서비스 입니다.");
			return "/common/resultAlert";
		}
	}
	
	@PostMapping("/consultings/create")
	public String create(ConsultingVO consulting, Model model, HttpSession session) {
		MemberVO user = (MemberVO)session.getAttribute("user");
		consulting.setMem_num(user.getMem_num());
		
		log.debug("<<상담 글 등록>> : " + consulting);
		
		consultingService.createConsulting(consulting);
		
		model.addAttribute("message","글이 성공적으로 등록되었습니다");
		model.addAttribute("url","/consultings");
		
		return "/common/resultAlert";
	}
	
	@GetMapping("/consultings/detail/{con_num}")
	public String detail(@PathVariable Long con_num, Model model) {
		ConsultingVO consulting = consultingService.getConsulting(con_num);
		model.addAttribute("consulting",consulting);
		
		Map<String,Object> map = new HashMap<>();
		map.put("pageNum", 1);
		map.put("pageItemNum", 5);
		map.put("con_num", con_num);
		List<Con_ReVO> cReList = consultingService.getListCon_Re(map);
		System.out.println(cReList);
		model.addAttribute("cReList", cReList);
		return "cDetail";
	}
	
	@GetMapping("/consultings/modify/{con_num}")
	public String update (@PathVariable Long con_num, Model model,HttpSession session) {
		ConsultingVO consulting = consultingService.getConsulting(con_num);
		Object user = session.getAttribute("user");
		
		if(user!=null) {
			if(user.getClass().equals(MemberVO.class)) {
				MemberVO member = (MemberVO)user;
				if(member.getMem_num()==consulting.getMem_num()) {
					consultingService.modifyConsulting(consulting);
					model.addAttribute("consulting",consulting);
					return "cUpdate";
				} else {
					model.addAttribute("url", "/consultings");
					model.addAttribute("message","본인 글이 아니면 수정할 수 없습니다.");
					return "/common/resultAlert";
				}
			} else if(user.getClass().equals(DoctorVO.class)) {
				model.addAttribute("url", "/main/main");
				model.addAttribute("message","잘못된 접근입니다.");
				return "/common/resultAlert";
			} else {
				return "404Error";
			}
		} else {
			model.addAttribute("url", "/consultings");
			model.addAttribute("message", "본인 글이 아니면 수정할 수 없습니다.");
			return "/common/resultAlert";
		}
	}
	
	@PostMapping("/consultings/modify/{con_num}")
	public String update (@PathVariable Long con_num, ConsultingVO consulting, Model model) {
		
		ConsultingVO oldConsulting = consultingService.getConsulting(con_num);
		oldConsulting.setCon_title(consulting.getCon_title());
		oldConsulting.setCon_content(consulting.getCon_content());
		oldConsulting.setCon_type(consulting.getCon_type());
		
		consultingService.modifyConsulting(oldConsulting);

		model.addAttribute("message","글이 성공적으로 수정되었습니다");
		model.addAttribute("url","/consultings");
		
		return "/common/resultAlert";
	}
	
	@GetMapping("/consultings/remove/{con_num}")
	public String remove(HttpSession session, @PathVariable Long con_num, Model model) {
		ConsultingVO consulting = consultingService.getConsulting(con_num);
		
		Object user = session.getAttribute("user");
		
		if(user!=null) {
			if(user.getClass().equals(MemberVO.class)) {
				MemberVO member = (MemberVO)user;
				if(member.getMem_num()==consulting.getMem_num()) {
					consultingService.removeConsulting(con_num);
					model.addAttribute("url", "/consultings");
					model.addAttribute("message","글 삭제가 완료되었습니다.");
					return "/common/resultAlert";
				} else {
					model.addAttribute("url", "/consultings");
					model.addAttribute("message","본인 글이 아니면 삭제할 수 없습니다.");
					return "/common/resultAlert";
				}
			} else if(user.getClass().equals(DoctorVO.class)) {
				model.addAttribute("url", "/main/main");
				model.addAttribute("message","잘못된 접근입니다.");
				return "/common/resultAlert";
			} else {
				return "404Error";
			}
		} else {
			model.addAttribute("url", "/consultings");
			model.addAttribute("message", "본인 글이 아니면 삭제할 수 없습니다.");
			return "/common/resultAlert";
		}
	}
	
	
	@PostMapping("/consultings/createReply")
	public String insert(Con_ReVO con_re, HttpSession session, Model model) {
		Object user = session.getAttribute("user");
		
		if(user!=null) {
			if(user.getClass().equals(MemberVO.class)) {
				model.addAttribute("url", "/main/main");
				model.addAttribute("message","일반 회원은 답글을 작성하실 수 없습니다.");
				return "/common/resultAlert";
			} else if(user.getClass().equals(DoctorVO.class)) {
				DoctorVO doctor = (DoctorVO)user;
				con_re.setDoc_num(doctor.getDoc_num());
				consultingService.createCon_Re(con_re);
				
				return "redirect:/consultings/detail/"+con_re.getCon_num();
			} else {
				return "404Error";
			}
		} else {
			model.addAttribute("url", "/doctor/login");
			model.addAttribute("message", "의사 회원만 이용 가능한 서비스 입니다.");
			return "/common/resultAlert";
		}
	}
}
