package kr.spring.hospital.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		
		// 병원 (진료 과목) 맵 생성 후 값 넣기
		Map<String,String> subMap = new HashMap<>();
		subMap.put("가정의학과", "건강증진, 예방, 만성질환 등");
		subMap.put("내과","감기, 소화기, 호흡기 등");
		subMap.put("마취통증학과","급성통증, 만성통증 등");
		subMap.put("비뇨기과","소변 시 통증, 남성 질환 등");
		subMap.put("산부인과","피임상담, 여성질환 등");
		subMap.put("성형외과","피부질환, 화상, 상처 등");
		subMap.put("소아과","소아소화기, 소아호흡기, 알레르기 등");
		subMap.put("신경과","두통, 어지럼증, 뇌졸중 등");
		subMap.put("신경외과","요통, 디스크, 신경계 질환 등");
		subMap.put("안과","눈 피로, 결막염, 다래끼 등");
		subMap.put("영상의학과","방사선 촬영, MRI, CT");
		subMap.put("외과","갑상선, 유방, 하지정맥 등");
		subMap.put("응급의학과","심한 탈수, 급성처치 등");
		subMap.put("이비인후과","비염, 이명, 편도염 등");
		subMap.put("재활의학과","신체회복, 물리치료, 만성통증 등");
		subMap.put("정신건강의학과","수면장애, 스트레스, 중독 등");
		subMap.put("정형외과","관절염, 골절, 척추 측만증 등");
		subMap.put("치과","치아질환, 잇몸질환, 턱 관절 등");
		subMap.put("피부과","두드러기, 가려움증, 탈모 등");
		subMap.put("한의원","한방 진료, 다이어트, 경옥고 등");

		model.addAttribute("subMap",subMap);
		
		// 병원 (어떻게 아프신가요) 리스트 생성 후 값 넣기
		List<String> howSick = new ArrayList<>(Arrays.asList("독감","탈모","비염","대상포진","다이어트","아토피"));
		model.addAttribute("howSick", howSick);
		
		// 병원 (인기 검색어) 리스트 생성 후 값 넣기
		List<String> hotKeyWord = new ArrayList<>(Arrays.asList("여드름","지루성 피부염","감기","두드러기","역류성 식도염","보톡스","발열","백옥주사","당뇨"));
		model.addAttribute("hotKeyWord", hotKeyWord);
		
		return model;
	}
	
	
	// 병원 > 검색 결과
	@GetMapping("/hospitals/search")
	public String search(Model model) {
		
		// 접속할 때 내 위도 경도 값 담아서 받아오기 (진행중)
		
		
		
		
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
