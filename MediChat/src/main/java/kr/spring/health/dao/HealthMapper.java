package kr.spring.health.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import kr.spring.health.vo.HealthyBlogVO;

@Mapper
public interface HealthMapper {
	
	@Insert("INSERT INTO healthy_blog(healthy_num,mem_num,healthy_title,healthy_content,h_filename) VALUES(health_seq.nextval,#{mem_num},#{healthy_title},#{healthy_content},#{h_filename})")
	public void insertHeal(HealthyBlogVO vo);
	public void updateHeal(HealthyBlogVO vo);
	public void deleteHeal(Long healthy_num);
	public HealthyBlogVO getHealthy(Long healthy_num);
	public Integer selectHealCount(Map<String, Object> map);
	public List<HealthyBlogVO> selectHealList(Map<String, Object> map);

}
