package kr.spring.disease.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Service;

import kr.spring.disease.vo.DiseaInsertVO;
import kr.spring.disease.vo.DiseaseCodeVO;
import kr.spring.disease.vo.DiseaseVO;
import kr.spring.disease.vo.Item;
@Service
public interface DiseaseService {
	//diseaseCode   
	public void insertDisCode(Item vo);
	public void deleteDisCode();
	public List<DiseaInsertVO> selectDisCodeList();
	public void insertDis(DiseaseVO vo);
	public void updateDis();
	public List<DiseaseVO> selectDisList(Map<String,Object> map);
	public Integer selectDisCount(Map<String,Object> map);
	public DiseaseVO getDis(String sickcd);
}
