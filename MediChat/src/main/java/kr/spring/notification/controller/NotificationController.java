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
                for (int i = 0; i < 10; i++) {
                    emitter.send("Message " + i);
                    Thread.sleep(1000); // 1초 간격으로 메시지 전송
                }
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        }).start();
        return emitter;
    }
	
	@GetMapping("/notification")
	public String notification(Model model) {
		model.addAttribute("emitter",emitter);
		return "notification";
	}
}
