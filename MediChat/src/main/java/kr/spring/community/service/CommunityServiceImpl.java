package kr.spring.community.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.community.vo.CommunityVO;

@Service
@Transactional
public class CommunityServiceImpl implements CommunityService{

	@Override
	public List<CommunityVO> selectCommunityList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer selectRowCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertBoard(CommunityVO community) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectCommunity(Long cbo_num) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCommunity(CommunityVO community) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCommunity(Long cbo_num) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateHit(Long cbo_num) {
		// TODO Auto-generated method stub
		
	}

}
