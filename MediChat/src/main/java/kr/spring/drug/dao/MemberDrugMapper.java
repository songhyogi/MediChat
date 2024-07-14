package kr.spring.drug.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.spring.drug.vo.DrugInfoVO;
import kr.spring.drug.vo.MemberDrugVO;

@Mapper
public interface MemberDrugMapper {
	//의약품 검색
	@Select("SELECT drg_name FROM drug WHERE drg_name LIKE '%' || #{drg_name} || '%'")
	public List<DrugInfoVO> selectDrugList (String drg_name);
	
	public List<MemberDrugVO> selectMemberDrugList(Map<String, Object> map);
	public void insertDrug(MemberDrugVO memberDrug);
	public void updateDrug(MemberDrugVO memberDrug);
	public void deleteDrug(Long med_num);
}
