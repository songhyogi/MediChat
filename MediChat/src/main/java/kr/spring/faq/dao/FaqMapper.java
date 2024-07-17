package kr.spring.faq.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.faq.vo.FaqVO;
import kr.spring.video.vo.VideoVO;

@Mapper
public interface FaqMapper {
	
	@Insert("INSERT INTO faq(faq_num,mem_num,faq_title,faq_content,f_category) VALUES(faq_seq.nextval,#{mem_num},#{faq_title},#{faq_content},#{f_category})")
	public void insertF(FaqVO vo);
	@Update("UPDATE faq SET faq_title=#{faq_title}, faq_content=#{faq_content},f_category=#{f_category},f_modify_date=sysdate WHERE faq_num=#{faq_num}")
	public void updateF(FaqVO vo);
	@Delete("DELETE FROM faq WHERE faq_num=#{faq_num}")
	public void deleteF(Long faq_num);
	public Integer selectCountF(Map<String,Object> map);
	public  FaqVO selectF(Long faq_num);
	public List<FaqVO> selectFList(Map<String,Object> map);
	//조회수
	@Update("UPDATE faq SET faq_hit=faq_hit+1 WHERE faq_num=#{faq_num}")
	public void updateFhit(Long faq_num);
}
