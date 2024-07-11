package kr.spring.drug.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.drug.dao.MemberDrugMapper;
import kr.spring.drug.vo.MemberDrugVO;

@Service
@Transactional
public class MemberDrugServiceImpl implements MemberDrugService{
	
	@Autowired
	MemberDrugMapper memberdrugMapper;
	
	@Override
	public List<MemberDrugVO> selectDrugList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertDrug(MemberDrugVO memberDrug) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDrug(MemberDrugVO memberDrug) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteDrug(Long med_num) {
		// TODO Auto-generated method stub
		
	}

}
