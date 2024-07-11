package kr.spring.chat.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.spring.chat.service.ChatService;
import kr.spring.chat.vo.ChatVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.reservation.vo.ReservationVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ChatController {
	@Autowired
	ChatService chatService;
	
	@ModelAttribute
	public ChatVO initCommand() {
		return new ChatVO();
	}
	
	
	
	//비대면채팅 페이지 호출
	@GetMapping("/chat/chatView")
	public String getChat(HttpSession session, Model model) {
		MemberVO user = (MemberVO)session.getAttribute("user");
		List<ChatVO> list = new ArrayList<ChatVO>();
		
		if(user.getMem_auth()==2) {
			list = chatService.selectChatListForMem(user.getMem_num());
			log.debug("채팅 내용 : "+list);
			log.debug("user.mem_num=="+user.getMem_num());
		}else if(user.getMem_auth()==3){
			list = chatService.selectChatListForDoc(user.getMem_num());
			log.debug("mem_auth==3");
		}
		
		System.out.println(list.size());
		model.addAttribute("list",list);
		
		return "chatView";
	}
	
	/*
	//생성된 채팅방 반환
	public String setNav(HttpSession session, Model model){
		MemberVO user = (MemberVO)session.getAttribute("user");
		List<ChatVO> list = new ArrayList<ChatVO>();
		
		if(user.getMem_auth()==2) {
			list = chatService.selectChatListForMem(user.getMem_num());
		}else if(user.getMem_auth()==3){
			list = chatService.selectChatListForDoc(user.getMem_num());
		}
		
		model.addAttribute("list",list);
		
		return "chat";
	}*/
	
	//예약 확정 버튼 클릭 시 채팅방 생성
	//@PostMapping("")
	//public void submitRes(ReservationVO reservationVO) {
		
		
	//}
	
}
