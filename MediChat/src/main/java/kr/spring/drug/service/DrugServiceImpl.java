package kr.spring.drug.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.drug.dao.DrugInfoMapper;
import kr.spring.drug.vo.DrugInfoVO;

@Service
@Transactional
public class DrugServiceImpl implements DrugService{

	@Autowired
	DrugInfoMapper drugInfoMapper;
	
	@Override
	public List<DrugInfoVO> selectList(Map<String, Object> map) {
		return drugInfoMapper.selectList(map);
	}

	@Override
	public Integer selectRowCount(Map<String, Object> map) {
		return drugInfoMapper.selectRowCount(map);
	}
}
