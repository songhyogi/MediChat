package kr.spring.pharmacy.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.hospital.vo.HospitalVO;
import kr.spring.pharmacy.dao.PharmacyMapper;
import kr.spring.pharmacy.vo.PharmacyVO;

@Service
@Transactional
public class PharmacyServiceImpl implements PharmacyService{
	@Autowired
	private PharmacyMapper pharmacyMapper;

	@Override
	public void insertPharmacy(PharmacyVO pharmacyVO) {
		pharmacyMapper.insertPharmacy(pharmacyVO);
	}

	@Override
	public void updatePharmacy(PharmacyVO pharmacyVO) {
		pharmacyMapper.updatePharmacy(pharmacyVO);
	}

	@Override
	public List<PharmacyVO> selectListPharmacy(Map<String, Object> map) {
		return pharmacyMapper.selectListPharmacy(map);
	}

	@Override
	public PharmacyVO selectPharmacy(Long pha_num) {
		pharmacyMapper.updateHitPharmacy(pha_num);
		
		return pharmacyMapper.selectPharmacy(pha_num);
	}

	@Override
	public void updateHitPharmacy(Long pha_num) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initHitPharmacy() {
		pharmacyMapper.initHitPharmacy();
		
	}

	
	
}
