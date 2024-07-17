package kr.spring.pharmacy.service;

import java.util.List;
import java.util.Map;

import kr.spring.hospital.vo.HospitalVO;
import kr.spring.pharmacy.vo.PharmacyVO;

public interface PharmacyService {
	// DB 관리용
	public void insertPharmacy(PharmacyVO pharmacyVO);
	public void updatePharmacy(PharmacyVO pharmacyVO);
	
	
	// 실 사용
	public List<PharmacyVO> selectListPharmacy(Map<String,Object> map);
	
	public PharmacyVO selectPharmacy(Long pha_num);
	public void updateHitPharmacy(Long pha_num);
	
	public void initHitPharmacy();
}
