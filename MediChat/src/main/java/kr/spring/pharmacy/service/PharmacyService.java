package kr.spring.pharmacy.service;

import java.util.List;

import kr.spring.hospital.vo.HospitalVO;
import kr.spring.pharmacy.vo.PharmacyVO;

public interface PharmacyService {
	// DB 관리용
	public void insertPharmacy(PharmacyVO pharmacyVO);
	public void updatePharmacy(PharmacyVO pharmacyVO);
	
	
	// 실 사용
	public HospitalVO selectPharmacyByNum(Long pha_num);
	public HospitalVO selectPharmacyByName(String pha_name);
	public HospitalVO selectPharmacyByPosition(String x, String y);
	public List<HospitalVO> selectListPharmacy();
}
