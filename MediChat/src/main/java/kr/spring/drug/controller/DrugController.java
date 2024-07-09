package kr.spring.drug.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.spring.drug.service.DrugService;
import kr.spring.drug.vo.DrugInfoVO;
import kr.spring.util.PagingUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DrugController {

    @Autowired
    private DrugService drugService;
    
	//의약품 목록
    @GetMapping("/drug/search")
    public String getlist(@RequestParam(defaultValue="1") int pageNum, String keyfield, String keyword, Model model) {
    	
    	log.debug("<<의약품 목록>>" );
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	
    	map.put("keyfield", keyfield);
    	map.put("keyword", keyword);
    	
    	//전체 레코드 수
    	int count = drugService.selectRowCount(map);
    	
    	//페이지 처리
    	PagingUtil page = new PagingUtil(keyfield, keyword, pageNum, count, 20,10, "search");
    	
    	List<DrugInfoVO> list = null;
    	if(count > 0) {
    		map.put("start", page.getStartRow());
    		map.put("end", page.getEndRow());
    		
    		list = drugService.selectList(map);
    	}
    	
    	model.addAttribute("count",count);
    	model.addAttribute("list",list);
    	model.addAttribute("page",page.getPage());
    	
    	return "drugSearch";
    }
}
