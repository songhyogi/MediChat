package kr.spring.hospital.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.spring.hospital.vo.HospitalVO;

@Mapper
public interface HospitalMapper {
	
	// DB 관리용
	public void insertHospital(HospitalVO hospitalVO);
	public void updateHospital(HospitalVO hospitalVO);
	
	
	// 실 사용
	public HospitalVO selectHospitalByNum(Long hos_num);
	public HospitalVO selectHospitalByName(String hos_name);
	public HospitalVO selectHospitalByPosition(String x, String y);
	public List<HospitalVO> selectListHospital();
}
