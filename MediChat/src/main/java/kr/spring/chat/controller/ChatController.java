package kr.spring.chat.controller;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.chat.service.ChatService;
import kr.spring.chat.vo.ChatFileVO;
import kr.spring.chat.vo.ChatMsgVO;
import kr.spring.chat.vo.ChatVO;
import kr.spring.doctor.vo.DoctorVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.reservation.vo.ReservationVO;
import kr.spring.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ChatController {
	@Autowired
	ChatService chatService;
	
	/*=======================
	 * 	 비대면채팅 페이지 호출
	 ========================*/
	@GetMapping("/chat/chatView")
	public String getChat(HttpSession session, Model model) {
		Object user = session.getAttribute("user");
		
		if(user == null) {
			model.addAttribute("message","로그인이 필요합니다.");
			model.addAttribute("url","/member/login");
			return "/common/resultAlert";
		}
		
		List<ChatVO> list = new ArrayList<ChatVO>();
		
		
		if (user instanceof DoctorVO) {
            DoctorVO doctor = (DoctorVO) user;
            model.addAttribute("user", doctor);
            list = chatService.selectChatListForDoc(doctor.getMem_num());
			log.debug("<<생성된 채팅방 호출>> - 의사회원번호: " + doctor.getMem_num());
			log.debug("<<생성된 채팅방 호출>> - 일반회원번호: "+list);
        } else if (user instanceof MemberVO) {
            MemberVO member = (MemberVO) user;
            model.addAttribute("user", member);
            list = chatService.selectChatListForMem(member.getMem_num());
			log.debug("<<생성된 채팅방 호출>> - 일반회원번호: "+member.getMem_num());
			log.debug("<<생성된 채팅방 호출>> - 일반회원번호: "+list);
        }
		
		model.addAttribute("list",list);
		log.debug("생성된 채팅방의 모델: "+model);
		
		return "chatView";
	}
	
	/*=======================
	 * 	  채팅 내역 불러오기
	 ========================*/
	@GetMapping("/chat/chatDetail")
	@ResponseBody
	public Map<String, Object> getMessage(HttpSession session,
										  Model model,
										  @RequestParam("chat_num") long chat_num,
										  @RequestParam("res_date") String res_date,
										  @RequestParam("res_time") String res_time){
		log.debug("<<채팅 입장>> 채팅방 번호: "+ chat_num);
		log.debug("<<예약 날짜, 예약 시간: >>"+res_date+ res_time);
		
		Map<String,Object> map = new HashMap<String,Object>();
		Object user = session.getAttribute("user");
		
		if(user==null) {
			map.put("userCheck", "logout");
		}else {
			map.put("userCheck", "login");
		}
		
		
		if (user instanceof MemberVO) {
			MemberVO member = (MemberVO) user;
			model.addAttribute("user", member);
			int userAuth = member.getMem_auth();
			if(userAuth == 1) {
				//현재 로그인한 사용자가 정지회원인 경우
	        	map.put("type","1");
			}
		}else {
			DoctorVO doctor = (DoctorVO) user;
	        model.addAttribute("user", doctor);
	        int userAuth = doctor.getMem_auth();
	        if(userAuth == 1) {
	        	//현재 로그인한 사용자가 정지회원인 경우
	        	map.put("type","1");
	        }
		};
		
		Object userType = model.getAttribute("user");
		
		List<ChatMsgVO> msg_list = new ArrayList<ChatMsgVO>();
		ReservationVO reservation = chatService.selectReservationByChatNum(chat_num);
		
		//파라미터를 날짜 및 시각으로 파싱
		LocalDate resDate = LocalDate.parse(reservation.getRes_date(), DateTimeFormatter.ISO_DATE);
		LocalTime resTime = LocalTime.parse(reservation.getRes_time(), DateTimeFormatter.ofPattern("HH:mm"));
		
		//local 시각과 비교할 날짜와 시각으로 파싱
		LocalDateTime reservationDateTime = LocalDateTime.of(resDate, resTime);
		
		if(userType instanceof MemberVO) {
			//현재 로그인한 사용자가 일반회원인 경우
			map.put("type", "2");
		}else if(userType instanceof DoctorVO) {
			//현재 로그인한 사용자가 의사회원인 경우
			map.put("type", "3");
		}
		
		if(LocalDateTime.now().isAfter(reservationDateTime)){
			log.debug("<<채팅방 사용 가능>> - 예약 날짜/시간: "+resDate+"/"+resTime);
			msg_list = chatService.selectMsg(chat_num);
			map.put("res_num",reservation.getRes_num());
			map.put("res_date",res_date);
			map.put("res_time",res_time);
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
	@PostMapping("/chat/chatRoom")
	@ResponseBody
	public Map<String,Object> insertMsg(HttpSession session,
										ChatMsgVO chatMsgVO){
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		Object user = session.getAttribute("user");
		
		if(user==null) {
			map.put("userCheck", "logout");
		}else {
			map.put("userCheck", "login");
		}
		
		if(user instanceof MemberVO) {
			chatMsgVO.setMsg_sender_type(0); //일반회원이 0
			chatService.insertMsg(chatMsgVO);
		}else if(user instanceof DoctorVO) {
			chatMsgVO.setMsg_sender_type(1); //의사회원이 1
			chatService.insertMsg(chatMsgVO);
			}

		return map;
	}
	
	/*=======================
	 * 	   이미지 폼 전송
	 ========================*/
	//계속..수정...
	@PostMapping("image_input")
	@ResponseBody
	public Map<String,Object> insertImage(@RequestParam("select_image") MultipartFile select_image,
										  HttpSession session,
										  HttpServletRequest request,
										  ChatMsgVO chatMsgVO)
										  throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		
		Object user = session.getAttribute("user");
		
		if(user==null) {
			map.put("userCheck", "logout");
		}else {
			map.put("userCheck", "login");
		}
		
		String image_name = FileUtil.createFile(request, select_image);
		byte[] image = FileUtil.getBytes(request.getServletContext().getRealPath(image_name));
		
		chatMsgVO.setMsg_content("<img src="+image+"class='upload-image'>");
		
		if(user instanceof MemberVO) {
			chatMsgVO.setMsg_sender_type(0); //일반회원이 0
			chatService.insertImage(chatMsgVO);
		}else if(user instanceof DoctorVO) {
			chatMsgVO.setMsg_sender_type(1); //의사회원이 1
			chatService.insertImage(chatMsgVO);
			}

		return map;
	}
	
	/*=======================
	 * 	   진료 파일 전송
	 ========================*/
	@PostMapping("file_input")
	public Map<String,Object> insertChatFile(@ModelAttribute ChatFileVO chatFileVO,
							   HttpServletRequest reqeust,
							   HttpSession session
							   ){

		ChatVO chat = chatService.selectChat(chatFileVO.getChat_num());
		
		chatFileVO.setMem_num(chat.getMem_num());
		chatFileVO.setDoc_num(chat.getDoc_num());
		
		chatService.insertChatFile(chatFileVO);
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("file_name", chatFileVO.getFile_name());
		map.put("file_type", chatFileVO.getFile_type());
		map.put("valid_date", chatFileVO.getValid_date());
		
		return map;
	}
	
}
