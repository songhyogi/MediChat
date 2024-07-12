package kr.spring.health.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.health.vo.HealthyBlogVO;
import kr.spring.health.vo.HealthyFavVO;
import kr.spring.health.vo.HealthyReplyVO;

@Mapper
public interface HealthMapper {
	
	
	public void insertHeal(HealthyBlogVO vo);
	public void updateHeal(HealthyBlogVO vo);
	@Delete("DELETE FROM healthy_blog WHERE healthy_num=#{healthy_num}")
	public void deleteHeal(Long healthy_num);
	public HealthyBlogVO getHealthy(Map<String, Object> map );
	public Integer selectHealCount(Map<String, Object> map);
	public List<HealthyBlogVO> selectHealList(Map<String, Object> map);
	@Update("UPDATE healthy_blog SET healthy_hit= healthy_hit+1 WHERE healthy_num=#{healthy_num}")
	public void updateHealHit(Long healthy_num);
	
	
	//게시글 좋아요
	@Insert("INSERT INTO healthy_fav(healthy_num,mem_num) VALUES(#{healthy_num},#{mem_num})")
	public void insertHFav(HealthyFavVO vo);
	@Delete("DELETE FROM healthy_fav WHERE healthy_num=#{healthy_num} AND mem_num=#{mem_num}")
	public void deleteHFav(HealthyFavVO vo);
	@Select("SELECT COUNT(*) FROM healthy_fav WHERE healthy_num=#{healthy_num}")
	public Integer selectHFavCount(Long healthy_num);
	@Select("SELECT * FROM healthy_fav WHERE healthy_num=#{healthy_num} AND mem_num=#{mem_num}")
	public HealthyFavVO selectHFav(HealthyFavVO vo);
	
	@Delete("DELETE FROM healthy_fav WHERE healthy_num=#{healthy_num}")
	public void deleteHFavByHeal(Long healthy_num);
	
	//댓글 
	@Insert("INSERT INTO healthy_re(hre_num,healthy_num,mem_num,hre_renum,hre_content,hre_level) VALUES(hre_seq.nextval,#{healthy_num},#{mem_num},#{hre_renum},#{hre_content},#{hre_level})")
	public void insertHre(HealthyReplyVO vo);
	@Update("UPDATE healthy_re SET hre_content=#{hre_content}, hre_modify_num=#{hre_modify_num} WHERE hre_num=#{hre_num}")
	public void updateHre(HealthyReplyVO vo);
	@Delete("DELETE FROM healthy_re WHERE hre_num=#{hre_num}")
	public void deleteHre(Long hre_num);
	public HealthyReplyVO selectHre(Long hre_num);
	public List<HealthyReplyVO> selectHreList(Map<String,Object> map);
	@Select("SELECT COUNT(*) FROM healthy_re WHERE healthy_num =#{healthy_num}")
	public Integer selectHreCount(Long healthy_num);
	@Delete("DELETE FROM healthy_re WHERE healthy_num=#{healthy_num}")
	public void deleteHReByHeal(Long healthy_num);
	
}
