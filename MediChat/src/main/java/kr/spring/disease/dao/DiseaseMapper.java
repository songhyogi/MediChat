package kr.spring.disease.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.spring.disease.vo.DiseaseVO;

@Mapper
public interface DiseaseMapper {
	public void insertDis(DiseaseVO vo);
	//DBupdate용?
	public void updateDis();
	public List<DiseaseVO> selectDisList(Map<String,Object> map);
	public Integer selectDisCount(Map<String,Object> map);
	public DiseaseVO getDis(Long dis_num);
	
}
