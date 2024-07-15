package kr.spring.hospital.service;

import java.util.List;
import java.util.Map;

import kr.spring.hospital.vo.HospitalVO;

public interface HospitalService {
	// DB 관리용
	public void insertHospital(HospitalVO hospitalVO);
	public void updateHospital(HospitalVO hospitalVO);
	
	
	// 실 사용
	public List<HospitalVO> selectListHospital(Map<String,Object> map);
	
	public HospitalVO selectHospital(Long hos_num);
	
	public void initHitHospital();
}
