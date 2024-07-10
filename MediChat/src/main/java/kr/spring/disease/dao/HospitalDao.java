package kr.spring.disease.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.spring.disease.vo.MedicalofficeVO;

@Mapper
public interface HospitalDao {
	@Insert("insert into DISEASEMEDICLIST(dsbjt_cd,main_sick) values(#{dsbjt_cd},#{main_sick})")
	public void insertH(MedicalofficeVO vo);
	@Select("SELECT * FROM DISEASEMEDICLIST WHERE  main_sick=#{main_sick} AND dsbjt_cd=#{dsbjt_cd}")
	public MedicalofficeVO selectH(Map<String,Object> map);
}
