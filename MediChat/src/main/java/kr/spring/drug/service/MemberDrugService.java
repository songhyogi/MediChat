package kr.spring.drug.service;

import java.util.List;
import java.util.Map;

import kr.spring.drug.vo.DrugInfoVO;
import kr.spring.drug.vo.MemberDrugVO;

public interface MemberDrugService {
	public List<DrugInfoVO> selectDrugList (String drg_name);
	public List<MemberDrugVO> selectMemberDrugList(Map<String, Object> map);
	public void insertDrug(MemberDrugVO memberDrug);
	public void updateDrug(MemberDrugVO memberDrug);
	public void deleteDrug(Long med_num);
}
