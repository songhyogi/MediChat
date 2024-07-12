package kr.spring.chat.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.chat.service.ChatService;
import kr.spring.chat.vo.ChatMsgVO;
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
	
	/*=======================
	 * 	 비대면채팅 페이지 호출
	 ========================*/
	@GetMapping("/chat/chatView")
	public String getChat(HttpSession session, Model model) {
		MemberVO user = (MemberVO)session.getAttribute("user");
		
		List<ChatVO> list = new ArrayList<ChatVO>();
		
		if(user.getMem_auth()==2) {
			list = chatService.selectChatListForMem(user.getMem_num());
			log.debug("<<생성된 채팅방 호출>> - 일반회원번호: "+user.getMem_num());
		}else if(user.getMem_auth()==3){
			list = chatService.selectChatListForDoc(user.getMem_num());
			log.debug("<<생성된 채팅방 호출>> - 의사회원번호: " + user.getMem_num());
		}
		
		model.addAttribute("list",list);
		
		return "chatView";
	}
	
	/*=======================
	 * 	  채팅 내역 불러오기
	 ========================*/
	@GetMapping("/chat/chatRoom")
	public Map<String, Object> getMessage(HttpSession session,
										  @RequestParam("chat_num") long chat_num,
										  @RequestParam("res_date") String res_date,
										  @RequestParam("res_time") String res_time){
		log.debug("<<채팅 입장>> 채팅방 번호: "+ chat_num);
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		
		List<ChatMsgVO> msg_list = new ArrayList<ChatMsgVO>();
		ReservationVO reservation = chatService.selectReservationByChatNum(chat_num);
		
		//파라미터를 날짜 및 시각으로 파싱
		LocalDate resDate = LocalDate.parse(reservation.getRes_date(), DateTimeFormatter.ISO_DATE);
		LocalTime resTime = LocalTime.parse(reservation.getRes_time(), DateTimeFormatter.ofPattern("HH:mm"));
		
		//local 시각과 비교할 날짜와 시각으로 파싱
		LocalDateTime reservationDateTime = LocalDateTime.of(resDate, resTime);
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(user==null) {
			map.put("user", "logout");
		}else {
			map.put("user", "login");
			if(user.getMem_auth()==1) {
				//현재 로그인한 사용자가 정지회원인 경우
				map.put("type", "1");
			}else if(user.getMem_auth()==2) {
				//현재 로그인한 사용자가 일반회원인 경우
				map.put("type", "2");
			}else if(user.getMem_auth()==3) {
				//현재 로그인한 사용자가 의사회원인 경우
				map.put("type", "3");
			}
		}
		
		if(LocalDateTime.now().isAfter(reservationDateTime)){
			log.debug("<<채팅방 사용 가능>> - 예약 날짜/시간: "+resDate+"/"+resTime);
			msg_list = chatService.selectMsg(chat_num);
			map.put("list",msg_list);
			map.put("chat","open");
		}else{
			log.debug("<<채팅방 사용 불가>> - 예약 날짜/시간: "+resDate+"/"+resTime);
			map.put("chat","close");
		}
		
	 return map;
		
	}
	
	//예약 확정 버튼 클릭 시 채팅방 생성
	//@PostMapping("")
	//public void submitRes(ReservationVO reservationVO) {
		
		
	//}
	
	/*=======================
	 * 	 	 메시지 입력
	 ========================*/
	@PostMapping("/chat/ChatRoom")
	public Map<String,Object> insertMsg(HttpSession session,
										ChatMsgVO chatMsgVO,
										HttpServletRequest request){
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(user==null) {
			//로그아웃 상태
			map.put("user","logout");
		}else {
			//로그인 상태
			if(user.getMem_auth()==2) {
				chatMsgVO.setMsg_sender_type(0); //일반회원이 0
			}else if(user.getMem_auth()==3) {
				chatMsgVO.setMsg_sender_type(1); //의사회원이 1
			}
			//chatMsgVO.set
			
		}
		
		
		
		return map;
	}
	
}
