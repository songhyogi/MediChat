package kr.spring.health.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.health.vo.HealthyBlogVO;
import kr.spring.health.vo.HealthyFavVO;
import kr.spring.health.vo.HealthyReFavVO;
import kr.spring.health.vo.HealthyReplyVO;

public interface HealthyService {
	public void insertHeal(HealthyBlogVO vo);
	public void updateHeal(HealthyBlogVO vo);
	public void deleteHeal(Long healthy_num);
	public HealthyBlogVO getHealthy(Map<String, Object> map);
	public Integer selectHealCount(Map<String, Object> map);
	public List<HealthyBlogVO> selectHealList(Map<String, Object> map);
	public void updateHealHit(Long healthy_num);
	
	//게시글 좋아요
	public void insertHFav(HealthyFavVO vo);
	public void deleteHFav(HealthyFavVO vo);
	public Integer selectHFavCount(Long healthy_num);
	public HealthyFavVO selectHFav(HealthyFavVO vo);
	
	//댓글

	public void insertHre(HealthyReplyVO vo);
	public void updateHre(HealthyReplyVO vo);
	public void deleteHre(Long hre_num);
	public HealthyReplyVO selectHre(Long hre_num);
	public List<HealthyReplyVO> selectHreList(Map<String,Object> map);
	public Integer selectHreCount(Long healthy_num);
	
	//댓글 좋아요
	//댓글 좋아요
	public void insertHreFav(HealthyReFavVO vo);
	public void deleteHreFav(HealthyReFavVO vo);
	public Integer selectHreFavCount(Long hre_num);
	public HealthyReFavVO selectHreFav(HealthyReFavVO vo);
	
	
	
}
