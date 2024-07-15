package kr.spring.notification.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class NotificationController {
	
	static SseEmitter emitter;
	
	@GetMapping("/sse")
	@ResponseBody
    public SseEmitter handleSse() {
        emitter = new SseEmitter();
        new Thread(() -> {
            try {
            	for(int i=0; i<10; i++) {
            		emitter.send("Message 안녕하세요 ");
            		Thread.sleep(1000);
            	}
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        }).start();
        return emitter;
    }
	
}
