package kr.spring.drug.service;

import java.util.List;
import java.util.Map;

import kr.spring.drug.vo.DrugInfoVO;

public interface DrugService {
	public List<DrugInfoVO> selectList(Map<String, Object> map);
	public Integer selectRowCount(Map<String, Object> map);
}
