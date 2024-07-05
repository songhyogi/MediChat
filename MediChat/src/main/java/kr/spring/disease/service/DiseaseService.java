package kr.spring.disease.service;

import java.util.List;
import java.util.Map;

import kr.spring.disease.vo.DiseaseVO;

public interface DiseaseService {
	public void insertDis(DiseaseVO vo);
	//DBupdate용?
	public void updateDis();
	public List<DiseaseVO> selectDisList(Map<String,Object> map);
	public Integer selectDisCount(Map<String,Object> map);
	public DiseaseVO getDis(Long dis_num);
}
