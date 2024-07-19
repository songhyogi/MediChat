package kr.spring.community.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import kr.spring.community.vo.CommunityVO;

public interface CommunityMapper {
	/*-----------------------------게시판글-----------------------------*/
	//게시판
	public List<CommunityVO> selectCommunityList (Map<String, Object> map);//목록
	public Integer selectRowCount(Map<String, Object> map);//개수
	public void insertBoard(CommunityVO community);//등록
	@Select("SELECT * FROM cboard JOIN member USING(mem_num) LEFT OUTER JOIN member_detail USING(mem_num) WHERE cbo_num=#{cbo_num}")
	public void selectCommunity(Long cbo_num); //상세
	public void updateCommunity(CommunityVO community);//수정
	@Delete("DELETE * FROM cboard WHERE cbo_num=#{cbo_num}")
	public void deleteCommunity(Long cbo_num);//삭제
	
	//조회수
	public void updateHit(Long cbo_num);
	
	//게시판 좋아요
	
	
	/*-----------------------------댓글-----------------------------*/
	
	//댓글 좋아요
	
	/*-----------------------------답글/대댓글-----------------------------*/
	
}
