package kr.spring.community.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.community.dao.CommunityMapper;
import kr.spring.community.vo.CommunityFavVO;
import kr.spring.community.vo.CommunityVO;

@Service
@Transactional
public class CommunityServiceImpl implements CommunityService{
	
	@Autowired CommunityMapper communityMapper;
	
	/*-----------------------------게시판글-----------------------------*/
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
	public CommunityVO selectCommunity(Long cbo_num) {
		return communityMapper.selectCommunity(cbo_num);
	}

	@Override
	public void updateCommunity(CommunityVO community) {
		communityMapper.updateCommunity(community);
	}

	@Override
	public void deleteCommunity(Long cbo_num) {//게시판글 삭제
		//댓글(답글) 좋아요 삭제
		//댓글(답글) 삭제
		//게시판 좋아요 삭제
		communityMapper.deleteFavByCommunityNum(cbo_num);
		//글 삭제
		communityMapper.deleteCommunity(cbo_num);
	}

	@Override
	public void updateHit(Long cbo_num) {
		communityMapper.updateHit(cbo_num);
	}
	
	/*-----------------------------게시판글 좋아요-----------------------------*/
	@Override
	public CommunityFavVO selectFav(CommunityFavVO fav) {
		return communityMapper.selectFav(fav);
	}

	@Override
	public Integer selectFavCount(Long cbo_num) {
		return communityMapper.selectFavCount(cbo_num);
	}

	@Override
	public void insertFav(CommunityFavVO fav) {
		communityMapper.insertFav(fav);
	}

	@Override
	public void deleteFav(CommunityFavVO fav) {
		communityMapper.deleteFav(fav);
	}

}
