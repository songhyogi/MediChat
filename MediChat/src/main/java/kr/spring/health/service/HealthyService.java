package kr.spring.health.service;

import java.util.List;
import java.util.Map;

import kr.spring.health.vo.HealthyBlogVO;

public interface HealthyService {
	public void insertHeal(HealthyBlogVO vo);
	public void updateHeal(HealthyBlogVO vo);
	public void deleteHeal(Long healthy_num);
	public HealthyBlogVO getHealthy(Long healthy_num);
	public Integer selectHealCount(Map<String, Object> map);
	public List<HealthyBlogVO> selectHealList(Map<String, Object> map);

}
