package kr.spring.hospital.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.spring.hospital.service.HospitalService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HospitalController {
	
	@Value("${API.KSY.KAKAO-API-KEY}")
	private String apiKey;
	
	@Autowired
	private HospitalService hospitalService;
	
	
	// 병원
	@GetMapping("/hospitals")
	public Model hospital(Model model) {
		//카카오맵 api 키
		model.addAttribute("apiKey", apiKey);
		
		return model;
	}
	
	
	
	// 병원 > 검색 결과
	@GetMapping("/hospitals/search")
	public String search(Model model) {
		
		// 접속할 때 내 위도 경도 값 담아서 받아오기		
		
		// 병원 (진료 과목) 리스트 생성 후 값 넣기
		
		// 병원 (어떻게 아프신가요) 리스트 생성 후 값 넣기
		
		// 병원 (인기 검색어) 리스트 생성 후 값 넣기
		
		// 현재 시간 변수 생성 후 값 넣기
		LocalDateTime now = LocalDateTime.now();
		
		String time = now.format(DateTimeFormatter.ofPattern("HH:mm")); //hh:mm
		int day = now.getDayOfWeek().getValue(); //1:월 2:화 3:수 4:목 5:금 6:토 7:일
		model.addAttribute("time", time);
		model.addAttribute("day", day);
		
		return "search";
	}
	
	
	
	
	
	// 병원 > 검색 결과 > 상세 페이지
	@GetMapping("/hospitals/search/detail")
	public String detail() {
		
		
		return "";
	}
}
