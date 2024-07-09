package kr.spring.chat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import kr.spring.chat.service.ChatService;
import kr.spring.chat.vo.ChatVO;
import kr.spring.member.vo.MemberVO;
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
	
	//채팅방 목록 불러오기
	@GetMapping("/chat/chatView")
	public String setNav(HttpSession session, Model model){
		MemberVO user = (MemberVO)session.getAttribute("user");
		
		if(user.getMem_auth()==2) {
			//List<ChatVO> chat = chatService.selectChatListForMem(user.getMem_num());
		}else if(user.getMem_auth()==3) {
			
		}
		//model.addAttribute("chat",chat);
		
		return "chatView";
	}
	
	//채팅방 생성
	//@PostMapping("")
	//public void submitRes(ReservationVO reservationVO) {
		
		
	//}
	
}
