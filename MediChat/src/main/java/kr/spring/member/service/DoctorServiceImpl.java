package kr.spring.member.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.hospital.dao.HospitalMapper;
import kr.spring.hospital.vo.HospitalVO;
import kr.spring.member.dao.DoctorMapper;
import kr.spring.member.vo.DoctorVO;

@Service
@Transactional
public class DoctorServiceImpl implements DoctorService{
	
	@Autowired
	DoctorMapper doctorMapper;
	
	@Override
	public void insertDoctor(DoctorVO doctor) {
		doctor.setMem_num(doctorMapper.selectDoc_num());
		doctorMapper.insertDoctor(doctor);
		doctorMapper.insertDoctor_detail(doctor);
	}

	@Override
	public DoctorVO selectDoctor(Long doc_num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateDoctor(DoctorVO doctor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDocPasswd(DoctorVO doctor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void uploadDocProfile(DoctorVO doctor) {
		doctorMapper.uploadDocProfile(doctor);
	}

	@Override
	public void deleteDoctor(Long doc_num) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DoctorVO checkId(String mem_id) {
		return doctorMapper.checkId(mem_id);
	}

	@Override
	public void findId(DoctorVO doctor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void findPasswd(DoctorVO doctor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<HospitalVO> getHosList(Map<String, String> map) {
		return doctorMapper.getHosList(map);
	}

}
