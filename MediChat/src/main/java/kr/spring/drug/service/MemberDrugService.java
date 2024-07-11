package kr.spring.drug.service;

import java.util.List;
import java.util.Map;

import kr.spring.drug.vo.MemberDrugVO;

public interface MemberDrugService {
	public List<MemberDrugVO> selectDrugList(Map<String, Object> map);
	public void insertDrug(MemberDrugVO memberDrug);
	public void updateDrug(MemberDrugVO memberDrug);
	public void deleteDrug(Long med_num);
}
