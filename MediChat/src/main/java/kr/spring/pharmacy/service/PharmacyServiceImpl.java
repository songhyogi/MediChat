package kr.spring.pharmacy.service;

import java.util.List;

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public HospitalVO selectPharmacyByNum(Long pha_num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HospitalVO selectPharmacyByName(String pha_name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HospitalVO selectPharmacyByPosition(String x, String y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HospitalVO> selectListPharmacy() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
