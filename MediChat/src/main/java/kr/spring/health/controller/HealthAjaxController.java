package kr.spring.health.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.health.service.HealthyService;
import kr.spring.health.vo.HealthyFavVO;
import kr.spring.health.vo.HealthyReplyVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.PagingUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HealthAjaxController {
	@Autowired 
	private HealthyService service ;
	
	@PostMapping("/health/selectHReply")
	@ResponseBody
	public Map<String,Object> selectHreList(long healthy_num,@RequestParam(defaultValue="1") int pageNum,HttpSession session){
		
		Map<String,Object> map = new HashMap<String,Object>();
		MemberVO user = (MemberVO) session.getAttribute("user");
		long user_num;
		if(user == null) {
			user_num=0;
		}else{
			user_num = user.getMem_num();
		}
	
		int count = service.selectHreCount(healthy_num);
		PagingUtil page = new PagingUtil(pageNum,count,10,10,"");
		map.put("healthy_num", healthy_num);
		map.put("start", page.getStartRow());
		map.put("end", page.getEndRow());
		List<HealthyReplyVO> list = service.selectHreList(map);
		
		Map<String,Object> jmap = new HashMap<String,Object>();
		jmap.put("list", list);
		jmap.put("count", count);
		jmap.put("user_num", user_num);
		log.debug("로그 댓글불러오기"+jmap);
		return jmap;
	}
	
	
	@PostMapping("/health/clickHFav")
	@ResponseBody
	public Map<String,Object> clickFav(long healthy_num,HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		MemberVO user =(MemberVO) session.getAttribute("user");
		if(user == null) {
			map.put("result", "logout");
		}else {
			HealthyFavVO userfav= new HealthyFavVO();
			userfav.setHealthy_num(healthy_num);
			userfav.setMem_num(user.getMem_num());
			HealthyFavVO fav = service.selectHFav(userfav);
			if(fav == null) {
				service.insertHFav(userfav);
				map.put("status", "yesFav");
			}else {
				service.deleteHFav(userfav);
				map.put("status", "noFav");
			}
			map.put("result", "success");
			map.put("count",service.selectHFavCount(healthy_num));
		}
		
		return map;
	}
	@PostMapping("/health/insertHreply")
	@ResponseBody
	public Map<String,Object> insertHre(HealthyReplyVO vo, HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		MemberVO user =(MemberVO) session.getAttribute("user");
		if(user == null) {
			map.put("result", "logout");
		}else {
			vo.setMem_num(user.getMem_num());
			service.insertHre(vo);
			map.put("result", "success");
		}
		
		return map;
	}

}
