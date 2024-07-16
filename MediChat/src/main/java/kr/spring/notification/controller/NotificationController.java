package kr.spring.notification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

import kr.spring.notification.service.NotificationService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class NotificationController {
	
	static SseEmitter emitter;
	
	@Autowired
	private NotificationService notificationService;
	
	@GetMapping("/sse")
	@ResponseBody
    public SseEmitter handleSse() {
        emitter = new SseEmitter(60L * 1000 * 60);
        try {
        	
        	emitter.send("Message 안녕하세요");
        	
        	emitter.complete();
        } catch(Exception e) {
        	
        }
        
        return emitter;
    }
}
