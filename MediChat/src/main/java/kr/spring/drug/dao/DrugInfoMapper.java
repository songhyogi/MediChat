package kr.spring.drug.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.spring.drug.vo.DrugInfoVO;

@Mapper
public interface DrugInfoMapper {
	public void insertDrugInfo(DrugInfoVO drugInfoVO);
	public List<DrugInfoVO> selectList(Map<String, Object> map);
	public Integer selectRowCount(Map<String, Object> map);
	@Select("SELECT * FROM drug WHERE drg_num=#{drg_num}")
	public DrugInfoVO selectDrug(Long drg_num);
}
