package kr.spring.notification.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import kr.spring.member.vo.MemberVO;
import kr.spring.notification.service.NotificationService;
import kr.spring.notification.vo.NotificationVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class NotificationController {
	
	@Autowired
	private NotificationService notificationService;
	
	private final Map<Long,SseEmitter> emitters = new ConcurrentHashMap<>();
	
	
	@GetMapping(value="/sse")
	public String test(HttpSession session, Model model) {
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user!=null) {
			log.debug("<<로그인 하셨네요>>");
			long user_num = user.getMem_num();
			
			List<NotificationVO> notiList = notificationService.selectListNotification(user_num);
			int notiCnt = notificationService.selectCountNotification(user_num);
			
			log.debug("<<SseEmitter의 수>> : " + emitters.size());
			
			model.addAttribute("notiList",notiList);
			model.addAttribute("notiCnt",notiCnt);
			
			return "/notification/noti";
		} else {
			log.debug("<<로그인 안하셨어요>>");
			String message = "로그인 먼저!";
			String url = "/main/main";
			
			model.addAttribute("message",message);
			model.addAttribute("url",url);
			
			return "/common/resultAlert";
		}
	}
	
//	//구독 과정 요약하자면 로그인 시 한번 emiiters라는 맵에 알림을 받기 위한 SseEmitter을 mem_num을 키값으로 넣음
//	@GetMapping(value="/sse/subscribe")
//	public String subscribe(HttpSession session, Model model) {
//		
//		MemberVO user = (MemberVO)session.getAttribute("user");
//		if(user!=null) {
//			log.debug("<<로그인 하셨네요>>");
//			long user_num = user.getMem_num();
//			
//			SseEmitter emitter = new SseEmitter(0L);// 타임아웃 설정 (0은 무제한)
//			emitters.put(user_num, emitter);
//			
//			log.debug("<<SseEmitter의 수>> : " + emitters.size());
//			
//			return "redirect:/sse";
//		} else {
//			log.debug("<<로그인 안하셨어요>>");
//			String message = "로그인 먼저!";
//			String url = "/main/main";
//			
//			model.addAttribute("message",message);
//			model.addAttribute("url",url);
//			
//			return "/common/resultAlert";
//		}
//	}
	
	@GetMapping(value = "/sse/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseBody
	public SseEmitter subscribe(HttpSession session, Model model) {
	    MemberVO user = (MemberVO) session.getAttribute("user");
	    if (user != null) {
	        long user_num = user.getMem_num();

	        // 기존 Emitter가 있다면 제거
	        SseEmitter existingEmitter = emitters.remove(user_num);
	        if (existingEmitter != null) {
	            existingEmitter.complete();
	        }

	        // 새로운 Emitter 생성
	        SseEmitter emitter = new SseEmitter(0L); // 타임아웃을 무제한으로 설정
	        emitters.put(user_num, emitter);

	        // 완료 및 타임아웃 이벤트 처리
	        emitter.onCompletion(() -> emitters.remove(user_num));
	        emitter.onTimeout(() -> emitters.remove(user_num));

	        // 이전 알림을 전송하는 로직은 필요에 따라 추가
	        List<NotificationVO> notifications = notificationService.selectListNotification(user_num);
	        notifications.forEach(notification -> {
	            try {
	                emitter.send(SseEmitter.event().data(notification));
	            } catch (IOException e) {
	                // 예외 처리
	            }
	        });

	        return emitter;
	    } else {
	        String message = "로그인 먼저!";
	        String url = "/main/main";

	        model.addAttribute("message", message);
	        model.addAttribute("url", url);

	        return null;
	    }
	}
	
	
	//구독 취소
	@GetMapping("/sse/cancle")
	public String cancle(HttpSession session, Model model) {
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user!=null) {
			log.debug("<<로그인 하셨네요>>");
			long user_num = user.getMem_num();
			
			emitters.remove(user_num);
			
			log.debug("<<SseEmitter의 수>> : " + emitters.size());
			
			return "redirect:/sse";
		} else {
			log.debug("<<로그인 안하셨어요>>");
			String message = "로그인 먼저!";
			String url = "/main/main";
			
			model.addAttribute("message",message);
			model.addAttribute("url",url);
			
			log.debug("<<SseEmitter의 수>> : " + emitters.size());
			
			return "/common/resultAlert";
		}
	}
	
	//알림 보내는 메서드
	@GetMapping(value="/sse/sendEvent", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseBody
	public SseEmitter send(HttpSession session) {
		MemberVO user = (MemberVO)session.getAttribute("user");
		long user_num = user.getMem_num();
		SseEmitter user_emitter = emitters.get(user_num);
		
		NotificationVO notification = NotificationVO.builder()
													.mem_num(user_num)
													.noti_category(1L)
													.noti_category_num(2L)
													.noti_message("안녕")
													.noti_isRead(0)
													.noti_priority(0)
													.build();
		try {
			user_emitter.send(SseEmitter.event().data(notification));
			log.debug("<<알림 보내기 성공!!!>>");
		} catch(Exception e) {
			user_emitter.completeWithError(e);
			log.debug("<<알림 보내기 실패!!!>>");
		}
		return user_emitter;
	}
}
