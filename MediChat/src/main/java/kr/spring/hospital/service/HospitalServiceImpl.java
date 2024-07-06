package kr.spring.hospital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.hospital.dao.HospitalMapper;
import kr.spring.hospital.vo.HospitalVO;

@Service
@Transactional
public class HospitalServiceImpl implements HospitalService{
	@Autowired
	private HospitalMapper hospitalMapper;

	@Override
	public void insertHospital(HospitalVO hospitalVO) {
		hospitalMapper.insertHospital(hospitalVO);
	}

	@Override
	public void updateHospital(HospitalVO hospitalVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HospitalVO selectHospitalByNum(Long hos_num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HospitalVO selectHospitalByName(String hos_name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HospitalVO selectHospitalByPosition(String x, String y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HospitalVO> selectListHospital() {
		// TODO Auto-generated method stub
		return null;
	}
}
