package kr.spring.pharmacy.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.spring.hospital.vo.HospitalVO;
import kr.spring.pharmacy.vo.PharmacyVO;

@Mapper
public interface PharmacyMapper {
	// DB 관리용
	public void insertPharmacy(PharmacyVO pharmacyVO);
	public void updatePharmacy(PharmacyVO pharmacyVO);
	
	
	// 실 사용
	public List<PharmacyVO> selectListPharmacy(Map<String,Object> map);
	
	public PharmacyVO selectPharmacy(Long pha_num);
	public void updateHitPharmacy(Long hos_num);
	
	public void initHospital();
	
}
