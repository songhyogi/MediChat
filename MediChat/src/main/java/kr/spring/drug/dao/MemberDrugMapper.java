package kr.spring.drug.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.spring.drug.vo.DrugInfoVO;
import kr.spring.drug.vo.MemberDrugVO;

@Mapper
public interface MemberDrugMapper {
	//의약품 검색
	@Select("SELECT drg_name FROM drug WHERE drg_name LIKE '%' || #{drg_name} || '%'")
	public List<DrugInfoVO> selectDrugList (String drg_name);
	@Select("SELECT * FROM medicine WHERE mem_num=#{mem_num}")
	public List<MemberDrugVO> selectMemberDrugList(Long mem_num);
	public void insertDrug(MemberDrugVO memberDrug);
	@Select("SELECT * FROM medicine WHERE med_num=#{med_num}")
	public MemberDrugVO selectDrug(Long med_num);//업데이트를 위해 특정 med_num의 레코드를 구함
	public void updateDrug(MemberDrugVO memberDrug);
	@Delete("DELETE FROM medicine WHERE med_num=#{med_num}")
	public void deleteDrug(Long med_num);
}
