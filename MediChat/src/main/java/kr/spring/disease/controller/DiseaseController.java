package kr.spring.disease.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import kr.spring.disease.service.DiseaseService;
import kr.spring.disease.service.HospitalService;
import kr.spring.disease.vo.DiseaInsertVO;
import kr.spring.disease.vo.DiseaseCodeVO;
import kr.spring.disease.vo.DiseaseVO;
import kr.spring.disease.vo.Item;
import kr.spring.disease.vo.Response;
import kr.spring.util.CaptchaUtil;
import kr.spring.util.PagingUtil;
import kr.spring.util.XUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DiseaseController {
	
	@Autowired
	private DiseaseService service;
	@Autowired
	HospitalService hservice;
	
	@GetMapping("/disease/diseaseDictionary")
	public String getList(String keyword,String keyfield, @RequestParam(defaultValue="1") int pageNum, Model model,HttpServletRequest request) throws JAXBException, IOException {
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("keyword", keyword);
		map.put("keyfield", keyfield);
		int count = service.selectDisCount(map);
		PagingUtil page = new PagingUtil(keyfield,keyword,pageNum,count,10,10,request.getContextPath()+"/disease/diseaseDictionary");
		map.put("start", page.getStartRow());
		map.put("end", page.getEndRow());
		List<DiseaseVO> list = service.selectDisList(map);
		model.addAttribute("keyfield",keyfield);
		model.addAttribute("keyword",keyword);
		model.addAttribute("count",count);
		model.addAttribute("list",list);
		model.addAttribute("page",page.getPage());
		return "disease_Dictionary";
	}
	//질병사전 상세 페이지
	@GetMapping("/disease/diseaseDictDetail")
	public String getDictDetail(String sickcd,Model model) {
		DiseaseVO disease = service.getDis(sickcd);
		model.addAttribute("disease",disease);
		return "disease_Detail";
	}
	
	//csv파일 > diseaseMedic DB
	@GetMapping("/disease/diseaseCsvInsert")
	public String insertCsvList(Model model,HttpServletRequest request) throws JAXBException, IOException {
		  BufferedReader reader = new BufferedReader(
	                new FileReader(request.getSession().getServletContext().getRealPath("/csv/DISEASEMEDICLIST.CSV"))
	        );
		
		int count =hservice.insertLargeVolumeHospitalData(request.getSession().getServletContext().getRealPath("/csv/DISEASEMEDICLIST.CSV"));
	
	
		model.addAttribute("count",count);
		
		return "diseaseMain";
	}
	//질병코드 api > disease_code  DB
	@GetMapping("/disease/diseaseCodeInsert")
	public String insertCodeList(Model model,HttpServletRequest request) throws JAXBException, IOException {
		Response diseasecode = XUtil.getDiseaseDepart(request);
		List<Item> list = diseasecode.getBody().getItemList().getIList();

		for(int i=0; i<list.size(); i++) {
			service.insertDisCode(list.get(i));
		}

	
		return "diseaseMain";
	}
	// diseaseVO >> disease DB
	@GetMapping("/disease/diseaseInsert")
	public String insertDiseaseList(Model model,HttpServletRequest request) throws JAXBException, IOException {
		List<DiseaInsertVO> list = service.selectDisCodeList();
		
		for(int i=0; i<list.size(); i++) {
			DiseaInsertVO code= list.get(i);
			DiseaseVO vo = new DiseaseVO();
			vo.setSickcd(code.getSickcd());
			vo.setDis_name(code.getSicknm());
			if(code.getDis_department()!= null) {
				vo.setDis_department(code.getDis_department());
			}else {
				vo.setDis_department("병의원과 상담");
			}
			
		
			String q= null;
			try {
	            q = URLEncoder.encode( code.getSicknm()+" 증상", "UTF-8");
	        } catch (UnsupportedEncodingException e) {
	            throw new RuntimeException("검색어 인코딩 실패",e);
	        }
			String key_apiURL = "https://openapi.naver.com/v1/search/encyc.json?query="+q+"&display=5";
			
			Map<String,String> requestHeaders = new HashMap<String,String>();
			
			requestHeaders.put("X-Naver-Client-Id","eOOwyOu2JylwwiqE4HAO");
			requestHeaders.put("X-Naver-Client-Secret","RZcZOCuwtq");
			
			String responseBody = CaptchaUtil.get(key_apiURL, requestHeaders);
			

			
			JSONObject jObject = new JSONObject(responseBody);
			String l = null;
			try {
				//https://openapi.naver.com/v1/captcha/nkey 호출해서 받은 키값
				
			JSONArray key = jObject.getJSONArray("items");
				String k =key.get(0).toString();
				String m =k.substring(k.indexOf("\"description\":\""),k.indexOf("..")).replace("\\", "").replace("\"", "").replace(":", "").replace("</b>", "").replace("<b>", "").replace("description", "");
				if(m.substring(m.indexOf(":")+1) != null) {
					l = m.substring(m.indexOf(":")+1);
				}else if(k.substring(k.indexOf("\"description\":\"")) != null){
					l=k.substring(k.indexOf("\"description\":\"")).replace("\\", "").replace("\"", "").replace(":", "").replace("</b>", "").replace("<b>", "").replace("...", "").replace("description", "");
				}else {
					l="원인미상";
				}
			
		
			}catch(Exception e) {
				e.printStackTrace();
			}
			if(l !=  null)
			vo.setDis_symptoms(l);
			else
			vo.setDis_symptoms("미상");
			service.insertDis(vo);
		}
		
		

	
		return "diseaseMain";
	}
	@GetMapping("/di")
	public String getC(Model model,HttpSession session) {
		
		
		return "diseaseMain";
	}
	
	
}
