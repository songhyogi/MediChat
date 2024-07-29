package kr.spring.community.controller;

import java.util.Collections;
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
import kr.spring.community.vo.CommunityFavVO;
import kr.spring.community.vo.CommunityReplyVO;
import kr.spring.community.vo.CommunityVO;
import kr.spring.doctor.vo.DoctorVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.PagingUtil;
import kr.spring.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beust.jcommander.internal.Console;


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
								@RequestParam(defaultValue="") String cbo_type,String keyfield,String keyword, Model model) {
		
		log.debug("<<커뮤니티 목록 - cbo_type >> : " + cbo_type);
		log.debug("<<커뮤니티 목록 - order>> : " + order);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("cbo_type",cbo_type);
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
		
		Object user = session.getAttribute("user"); 
		if(user==null) {
			model.addAttribute("message","로그인이 필요합니다.");
			model.addAttribute("url","/member/login"); return "/common/resultAlert";
		}
		
		if(user instanceof DoctorVO) {
			DoctorVO doctor = (DoctorVO) user;
			communityVO.setMem_num(doctor.getMem_num());
			communityService.insertCommunity(communityVO);
		}else if(user instanceof MemberVO) {
			MemberVO member = (MemberVO) user;
			communityVO.setMem_num(member.getMem_num());
			communityService.insertCommunity(communityVO);
		}
		
		model.addAttribute("message","글이 성공적으로 등록되었습니다");
		model.addAttribute("url",request.getContextPath()+"/medichatCommunity/list");
		
		return "/common/resultAlert";
	}
	
	//글 상세
	@GetMapping("/medichatCommunity/detail")
	public String detailCommunity(long cbo_num, Model model) {
		
		log.debug("<<커뮤니티 글 상세>> : " + cbo_num);
		
		communityService.updateHit(cbo_num); //해당 글 조회시 조회수 증가
		
		CommunityVO cboard = communityService.selectCommunity(cbo_num);
		cboard.setCbo_title(StringUtil.useNoHTML(cboard.getCbo_title()));
		
		model.addAttribute("cboard",cboard);
		
		return "cboardDetail";
	}
	
	//수정폼 호출
	@GetMapping("/medichatCommunity/update")
	public String updateForm(long cbo_num, Model model) {
		CommunityVO communityVO = communityService.selectCommunity(cbo_num);
		model.addAttribute("communityVO",communityVO);
		
		return "cboardModify";
	}
	//글 수정
	@PostMapping("/medichatCommunity/update")
	public String updateCommunity(CommunityVO communityVO, HttpSession session, Model model, BindingResult result, HttpServletRequest request) {
		
		log.debug("<<커뮤니티 글 수정>> : " + communityVO);
		
		if(result.hasErrors()) {
			CommunityVO vo = communityService.selectCommunity(communityVO.getCbo_num());
			return "cboardModify";
		}
		
		CommunityVO db_cboard = communityService.selectCommunity(communityVO.getCbo_num());
		
		communityService.updateCommunity(communityVO);
		model.addAttribute("message","글이 정상적으로 수정되었습니다.");
		model.addAttribute("url",request.getContextPath()+"/medichatCommunity/detail?cbo_num="+communityVO.getCbo_num());
		
		return "/common/resultAlert";
	}
	
	//글 삭제
	@GetMapping("/medichatCommunity/delete")
	public String deleteCommunity(long cbo_num) {
		
		log.debug("<<커뮤니티 글 삭제>> : " + cbo_num);
		
		CommunityVO db_cboard = communityService.selectCommunity(cbo_num);
		
		communityService.deleteCommunity(cbo_num);
		
		return "redirect:/medichatCommunity/list";
	}
	
	/*==============================게시판 좋아요==============================*/
	//좋아요 읽기
	@GetMapping("/medichatCommunity/getFav")
	@ResponseBody
	public Map<String, Object> getFav(CommunityFavVO fav, HttpSession session){
		
		log.debug("<<게시판 좋아요>> : " + fav);
		
		Map<String, Object> mapJson = new HashMap<String, Object>();
		Object user = session.getAttribute("user");
		
		if(user==null) {
			mapJson.put("status", "noFav");
		}else if(user!=null && user instanceof DoctorVO){
			DoctorVO doctor = (DoctorVO) user;
			fav.setMem_num(doctor.getMem_num());
			CommunityFavVO cboardFav = communityService.selectFav(fav);
			if(cboardFav != null) {
				mapJson.put("status", "yesFav");
			}else {
				mapJson.put("status", "noFav");
			}
		}else if(user!=null && user instanceof MemberVO){
			MemberVO member = (MemberVO) user;
			fav.setMem_num(member.getMem_num());
			CommunityFavVO cboardFav = communityService.selectFav(fav);
			if(cboardFav != null) {
				mapJson.put("status", "yesFav");
			}else {
				mapJson.put("status", "noFav");
			}
		}
		mapJson.put("count", communityService.selectFavCount(fav.getCbo_num()));
		
		return mapJson;
	}
	//좋아요 등록/삭제
	@PostMapping("/medichatCommunity/writeFav")
	@ResponseBody
	public Map<String, Object> wrtieFav(CommunityFavVO fav, HttpSession session){
		log.debug("<<게시판 좋아요 등록>> : " + fav);
		
		Map<String, Object> mapJson = new HashMap<String, Object>();
		Object user = session.getAttribute("user");
		
		if(user==null) {
			mapJson.put("result", "logout");
		}else if(user!=null && user instanceof DoctorVO){
			DoctorVO doctor = (DoctorVO) user;
			fav.setMem_num(doctor.getMem_num());
			
			CommunityFavVO cboardFav = communityService.selectFav(fav);
			if(cboardFav!=null) {//등록->삭제
				communityService.deleteFav(fav);
				mapJson.put("status", "noFav");
			}else {//등록
				communityService.insertFav(fav);
				mapJson.put("status", "yesFav");
			}
			
		}else if(user!= null && user instanceof MemberVO) {
			MemberVO member = (MemberVO) user;
			fav.setMem_num(member.getMem_num());
			
			CommunityFavVO cboardFav = communityService.selectFav(fav);
			if(cboardFav!=null) {//등록->삭제
				communityService.deleteFav(fav);
				mapJson.put("status", "noFav");
			}else {//등록
				communityService.insertFav(fav);
				mapJson.put("status", "yesFav");
			}
			mapJson.put("result", "success");
			mapJson.put("count", communityService.selectFavCount(fav.getCbo_num()));
		}
		
		return mapJson;
	}
	
	/*==============================댓글==============================*/
	//댓글 목록
	@GetMapping("/medichatCommunity/listComment")
	@ResponseBody
	public Map<String, Object> getLIst(long cbo_num, @RequestParam(defaultValue="1") int pageNum,int rowCount, HttpSession session){
		log.debug("<<댓글 목록 - cbo_num>> : " + cbo_num);
		log.debug("<<댓글 목록 - pageNum>> : " + pageNum);
		log.debug("<<댓글 목록 - rowCount>> : " + rowCount);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		Object user = session.getAttribute("user");
		long user_num;
		
		if(user == null) {
			user_num = 0;
		}else if(user!=null && user instanceof MemberVO){
			MemberVO member = (MemberVO)user;
			user_num = member.getMem_num();
		}else if(user!= null && user instanceof DoctorVO) {
			DoctorVO doctor = (DoctorVO)user;
			user_num = doctor.getMem_num();
		}else {
			user_num = -1; //에러발생..?(이를 명시하지 않는 경우 mapJson에 put할 때 에러 발생)
		}
		
		log.debug("<<댓글 목록 - user_num>> : " + user_num);
		
		int count = communityService.selectCountComment(cbo_num); //댓글 개수
		
		//페이지 처리
		PagingUtil page = new PagingUtil(pageNum, count, rowCount);
		map.put("cbo_num", cbo_num);
		map.put("start", page.getStartRow());
		map.put("end", page.getEndRow());
		map.put("user_num", user_num);
		
		List<CommunityReplyVO> list = null;
		if(count > 0) { //댓글이 있는 경우
			list = communityService.selectListComment(map); //댓글 목록
		}else {
			list = Collections.emptyList();//null이 아닌 비어있는 배열로 인식되게 처리
		}
		
		Map<String, Object> mapJson = new HashMap<String, Object>();
		mapJson.put("count", count);
		mapJson.put("list", list);
		if(user!=null) {
			mapJson.put("user_num", user_num);
		}
		return mapJson;
	}
	
	//댓글 등록
	@PostMapping("/medichatCommunity/writeComment")
	@ResponseBody
	public Map<String, String> writeComment(CommunityReplyVO communityReply, HttpSession session){
		
		log.debug("<<댓글 등록>> : " + communityReply);
		
		Map<String, String> mapJson = new HashMap<String, String>();
		
		Object user = session.getAttribute("user");
		
		if(user == null) {
			mapJson.put("result", "logout");
		}else if(user instanceof DoctorVO){
			DoctorVO doctor = (DoctorVO) user;
			communityReply.setMem_num(doctor.getMem_num());
			
			communityService.insertComment(communityReply);
			mapJson.put("result", "success");
		}else if(user instanceof MemberVO){
			MemberVO member = (MemberVO) user;
			communityReply.setMem_num(member.getMem_num());
			
			communityService.insertComment(communityReply);
			mapJson.put("result", "success");
		}
		
		return mapJson;
	}
	
	//댓글 수정
	@PostMapping("/medichatCommunity/updateComment")
	@ResponseBody
	public Map<String, String> updateComment(CommunityReplyVO commuityReply, HttpSession session){
		
		log.debug("<<댓글 수정>> : " + commuityReply);
		
		Map<String, String> mapJson = new HashMap<String, String>();
		
		Object user = session.getAttribute("user");
		CommunityReplyVO db_reply = communityService.selectComment(commuityReply.getCre_num());
		
		if(user == null) {
			mapJson.put("result", "logout");
		}else if(user instanceof DoctorVO){
			DoctorVO doctor = (DoctorVO) user;
			if(user != null && doctor.getMem_num()==db_reply.getMem_num()) {
				communityService.updateComment(commuityReply);
				
				mapJson.put("result", "success");
			}
		}else if(user instanceof MemberVO){
			MemberVO member = (MemberVO) user;
			if(user != null && member.getMem_num()==db_reply.getMem_num()) {
				communityService.updateComment(commuityReply);
				mapJson.put("result", "success");
			}
		}else {//로그인 회원번호와 작성자 회원번호 불일치
			mapJson.put("result", "wrongAccess");
		}
		
		return mapJson;
	}
	
	//댓글 삭제
	@PostMapping("/medichatCommunity/deleteComment")
	@ResponseBody
	public Map<String, String> deleteComment(Long cre_num, HttpSession session){
		
		log.debug("<<댓글 삭제>> : " + cre_num);
		
		Map<String, String> mapJson = new HashMap<String, String>();
		
		Object user = session.getAttribute("user");
		CommunityReplyVO db_reply = communityService.selectComment(cre_num);
		
		if(user == null) {
			mapJson.put("result", "logout");
		}else if(user instanceof DoctorVO){
			DoctorVO doctor = (DoctorVO) user;
			if(user != null && doctor.getMem_num()==db_reply.getMem_num()) {
				communityService.deleteComment(cre_num);
				mapJson.put("result", "success");
			}
		}else if(user instanceof MemberVO){
			MemberVO member = (MemberVO) user;
			if(user != null && member.getMem_num()==db_reply.getMem_num()) {
				communityService.deleteComment(cre_num);
				mapJson.put("result", "success");
			}
		}else {//로그인 회원번호와 작성자 회원번호 불일치
			mapJson.put("result", "wrongAccess");
		}
		
		return mapJson;
	}
	
	
	/*==============================답글==============================*/
	//답글 목록
	@GetMapping("/medichatCommunity/listReply")
	@ResponseBody
	public Map<String, Object> getListReply(long cre_num, HttpSession session){
		log.debug("<<답글 목록 - cre_num>> : " + cre_num);
		
		List<CommunityReplyVO> list = communityService.selectListReply(cre_num);
		log.debug("<<답글 list>>>" + list);
		Object user = session.getAttribute("user");
		
		Map<String, Object> mapJson = new HashMap<String, Object>();
		mapJson.put("list", list);
		
		if(user!=null && user instanceof DoctorVO) {
			DoctorVO doctor = (DoctorVO) user;
			mapJson.put("user_num", doctor.getMem_num());
		}else if(user!=null && user instanceof MemberVO) {
			MemberVO member = (MemberVO) user;
			mapJson.put("user_num", member.getMem_num());
		}
		
		return mapJson;
	}
	
	//답글 등록
	@PostMapping("/medichatCommunity/writeReply")
	@ResponseBody
	public Map<String, String> insertReply(CommunityReplyVO communityReplyVO, HttpSession session){
		
		log.debug("<<답글 등록>> : " + communityReplyVO);
		
		Map<String, String> mapJson = new HashMap<String, String>();
		
		Object user = session.getAttribute("user");
		
		if(user == null) {
			mapJson.put("result", "logout");
		}else if(user instanceof DoctorVO){
			DoctorVO doctor = (DoctorVO) user;
			communityReplyVO.setMem_num(doctor.getMem_num());
			
			communityService.insertReply(communityReplyVO);
			mapJson.put("result", "success");
		}else if(user instanceof MemberVO){
			MemberVO member = (MemberVO) user;
			communityReplyVO.setMem_num(member.getMem_num());
			
			communityService.insertReply(communityReplyVO);
			mapJson.put("result", "success");
		}
		
		return mapJson;
	}
}















