package kr.spring.health.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.health.dao.HealthMapper;
import kr.spring.health.vo.HealthyBlogVO;

@Service
@Transactional
public class HealthyServiceImpl implements HealthyService{
	
	
	@Autowired
	private HealthMapper mapper;
	@Override
	public void insertHeal(HealthyBlogVO vo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateHeal(HealthyBlogVO vo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteHeal(Long healthy_num) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HealthyBlogVO getHealthy(Long healthy_num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer selectHealCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HealthyBlogVO> selectHealList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

}
