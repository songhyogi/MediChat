package kr.spring.drug.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.spring.drug.vo.MemberDrugVO;

@Mapper
public interface MemberDrugMapper {
	public List<MemberDrugVO> selectDrugList(Map<String, Object> map);
	public void insertDrug(MemberDrugVO memberDrug);
	public void updateDrug(MemberDrugVO memberDrug);
	public void deleteDrug(Long med_num);
}
