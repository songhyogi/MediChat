package kr.spring.drug.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.spring.drug.crawling.DrugCrawling;
import kr.spring.drug.dao.DrugInfoMapper;
import kr.spring.drug.vo.DrugInfoVO;

@RestController
public class DrugInfoController {

    @Autowired
    private DrugInfoMapper drugMapper;
    
    @Autowired
    private DrugCrawling drugCrawling;
    
    //데이터 DB 저장
    @GetMapping("/insert/drug/3002")
    public String insert() {
    	drugCrawling.main();
    	for (DrugInfoVO drug : drugCrawling.list) {
    		drugMapper.insertDrugInfo(drug);
		}
    	
    	return "/main";
    }
    
}
