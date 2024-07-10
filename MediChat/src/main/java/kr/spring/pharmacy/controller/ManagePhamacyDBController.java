package kr.spring.pharmacy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import kr.spring.pharmacy.crawling.PharmacyCrawling;
import kr.spring.pharmacy.service.PharmacyService;
import kr.spring.pharmacy.vo.PharmacyVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ManagePhamacyDBController {
	@Autowired
	private PharmacyCrawling pc;
	@Autowired
	private PharmacyService pharmacyService;
	
	@GetMapping("/ksy/insertPharmacyToDB/3002")
	private void initialDBSetUp(){
		pc.main();
		for(PharmacyVO pharmacyVO : pc.list) {
			pharmacyService.insertPharmacy(pharmacyVO);
		}
		System.out.println(pc.list.size());
	}
}