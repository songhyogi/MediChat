package kr.spring.hospital.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import kr.spring.hospital.crawling.HospitalsCrawling;
import kr.spring.hospital.service.HospitalService;
import kr.spring.hospital.vo.HospitalVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ManageHospitalDBController {
	@Autowired
	private HospitalsCrawling hc;
	@Autowired
	private HospitalService hospitalService;
	
	@GetMapping("/ksy/insertHospitalToDB/3002")
	private void initialDBSetUp() throws Exception{
		hc.main();
		for(HospitalVO hospitalVO : hc.list) {
			hospitalService.insertHospital(hospitalVO);
		}
		System.out.println(hc.list.size());
	}
}