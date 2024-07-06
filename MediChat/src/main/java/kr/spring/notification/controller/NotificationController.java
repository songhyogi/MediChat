package kr.spring.notification.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class NotificationController {
	
	static List<SseEmitter> emitters = new ArrayList<>();
	
	private void setConfig(SseEmitter sseEmitter) {
		sseEmitter.onTimeout(()->{
			log.info("timeout");
			sseEmitter.complete();
		});
		sseEmitter.onCompletion(()->{
			log.info("completed");
			emitters.remove(sseEmitter);
		});
		sseEmitter.onTimeout(()->{
			log.info("error");
			sseEmitter.complete();
		});
	}
	
	@GetMapping(value = "/sse/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter sseEmitterExample() throws IOException{
		SseEmitter sseEmitter = new SseEmitter();
		setConfig(sseEmitter);
		emitters.add(sseEmitter);
		
		sseEmitter.send("data" + System.currentTimeMillis() + "\n\n");
		
		return sseEmitter;
	}
}
