package kr.spring.community.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.community.dao.CommunityMapper;
import kr.spring.community.vo.CommunityVO;

@Service
@Transactional
public class CommunityServiceImpl implements CommunityService{
	
	@Autowired CommunityMapper communityMapper;
	
	@Override
	public List<CommunityVO> selectCommunityList(Map<String, Object> map) {
		return communityMapper.selectCommunityList(map);
	}

	@Override
	public Integer selectRowCount(Map<String, Object> map) {
		return communityMapper.selectRowCount(map);
	}

	@Override
	public void insertCommunity(CommunityVO community) {
		communityMapper.insertCommunity(community);
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
