package kr.spring.video.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.spring.health.vo.HealthyBlogVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.PagingUtil;
import kr.spring.video.service.VideoService;
import kr.spring.video.vo.VideoVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class VideoController {
	
	@Autowired
	private VideoService service;
	
	@ModelAttribute
	public VideoVO initCommand() {
		return new VideoVO();
	}
	
	@GetMapping("/video/videoList")
	public String getVideoMain(@RequestParam(defaultValue="1") int pageNum,String keyfield,String keyword,Model model) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("keyword", keyword);
		map.put("keyfield", keyfield);
		int count = service.selectCountV(map);
		PagingUtil page = new PagingUtil(keyfield,keyword,pageNum,count,4,10,"videoList");
		map.put("start", page.getStartRow());
		map.put("end", page.getEndRow());
		List<VideoVO> list = service.selectVList(map);
		model.addAttribute("list",list);
		model.addAttribute("count",count);
		model.addAttribute("page",page.getPage());
		return "video_List";
	}
	
	@GetMapping("/video/videoWrite")
	public String getVideoWriteForm(HttpSession session,Model model,HttpServletRequest request) {
		MemberVO user = (MemberVO) session.getAttribute("user");
		
		if(user == null) {
			model.addAttribute("message","로그인 후 사용가능합니다.");
			model.addAttribute("url",request.getContextPath()+"/member/login");
			
		}else if(user.getMem_auth() >2 && user!= null) {
			return "video_Write";
		}else {
			model.addAttribute("message","쓰기 권한이 없습니다.");
			model.addAttribute("url",request.getContextPath()+"/video/videoList");
		}
		
		return "common/resultAlert";
		
	}
	
	@PostMapping("/video/videoWrite")
	public String getVidoWrite(VideoVO vo,HttpSession session,HttpServletRequest request,Model model) {
		MemberVO user = (MemberVO) session.getAttribute("user");
		
		if(user == null) {
			model.addAttribute("message","로그인 후 사용가능합니다.");
			model.addAttribute("url",request.getContextPath()+"/member/login");
			
		}else if(user.getMem_auth() >2 && user!= null) {
			vo.setMem_num(user.getMem_num());
			service.insertV(vo);
			return "redirect:/video/videoList";
		}else {
			model.addAttribute("message","쓰기 권한이 없습니다.");
			model.addAttribute("url",request.getContextPath()+"/video/videoList");
		}
		return "common/resultAlert";
	}
	@GetMapping("/video/videoDetail")
	public String getVdetail(Long video_num,Model model) {
		
		VideoVO vo = service.selectV(video_num);
		
		model.addAttribute("video", vo);
		
		
		return "video_Detail";
	}
}
