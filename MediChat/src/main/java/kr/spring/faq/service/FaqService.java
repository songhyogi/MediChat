package kr.spring.faq.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

import kr.spring.faq.vo.FaqVO;

public interface FaqService {
	
	public void insertF(FaqVO vo);
	public void updateF(FaqVO vo);
	public void deleteF(Long faq_num);
	public Integer selectCountF(Map<String,Object> map);
	public  FaqVO selectF(Long faq_num);
	public List<FaqVO> selectFList(Map<String,Object> map);
	//조회수
	public void updateFhit(Long faq_num);
}
