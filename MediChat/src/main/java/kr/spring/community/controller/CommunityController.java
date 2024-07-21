package kr.spring.community.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import kr.spring.community.service.CommunityService;
import kr.spring.community.vo.CommunityVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.PagingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@Controller
public class CommunityController {
	@Autowired
	private CommunityService communityService;
	
	@ModelAttribute
	public CommunityVO initCommand() {
		return new CommunityVO();
	}
	
	/*==============================게시판==============================*/
	//글 목록
	@GetMapping("/medichatCommunity/list")
	public String getCommunityList(@RequestParam(defaultValue="1") int pageNum,@RequestParam(defaultValue="1") int order,
								@RequestParam(defaultValue="1") String cbo_type,String keyfield,String keyword, Model model) {
		
		log.debug("<<커뮤니티 목록 - cbo_type >> : " + cbo_type);
		log.debug("<<커뮤니티 목록 - order>> : " + order);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		
		int count = communityService.selectRowCount(map);
		
		PagingUtil page = new PagingUtil(keyfield, keyword, pageNum, count, 20,10,"list","&cbo_type="+cbo_type+"&order="+order);
		
		List<CommunityVO> list = null;
		if(count > 0) {
			map.put("order", order);
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());
			
			list = communityService.selectCommunityList(map);
		}
		
		model.addAttribute("count",count);
		model.addAttribute("list",list);
		model.addAttribute("page",page.getPage());
		
		return "cboardList";
	}
	
	
	//글 등록 폼 호출
	@GetMapping("/medichatCommunity/write")
	public String insertForm() {
		
		log.debug("<<커뮤니티 등록 폼 호출>>");
		
		return "cboardWrite";
	}
	//글 등록
	@PostMapping("/medichatCommunity/write")
	public String insertSubmit(@Valid CommunityVO communityVO, HttpSession session, Model model, HttpServletRequest request, BindingResult result) {
		
		log.debug("<<커뮤니티 글 등록>> : " + communityVO);
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			model.addAttribute("message","로그인이 필요합니다.");
			model.addAttribute("url","/member/login");
			return "/common/resultAlert";
		}else if(user.getMem_auth() < 2) {
			model.addAttribute("message","권한이 없는 계정입니다. 자세한 사항은 관리자에게 문의하시기 바랍니다.");
			model.addAttribute("url","/member/login");
			return "/common/resultAlert";
		}
		
		if(result.hasErrors()) {
			return insertForm();
		}

		
		communityVO.setMem_num(user.getMem_num());
		communityService.insertCommunity(communityVO);
		
		model.addAttribute("message","글이 성공적으로 등록되었습니다");
		model.addAttribute("url",request.getContextPath()+"/medichatCommunity/list");
		
		return "/common/resultAlert";
	}
	 
	
}
