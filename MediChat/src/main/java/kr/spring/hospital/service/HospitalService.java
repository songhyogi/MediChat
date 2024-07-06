package kr.spring.hospital.service;

import java.util.List;

import kr.spring.hospital.vo.HospitalVO;

public interface HospitalService {
	// DB 관리용
	public void insertHospital(HospitalVO hospitalVO);
	public void updateHospital(HospitalVO hospitalVO);
	
	
	// 실 사용
	public HospitalVO selectHospitalByNum(Long hos_num);
	public HospitalVO selectHospitalByName(String hos_name);
	public HospitalVO selectHospitalByPosition(String x, String y);
	public List<HospitalVO> selectListHospital();
}
