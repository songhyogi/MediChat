package kr.spring.disease.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.disease.dao.DiseaseMapper;
import kr.spring.disease.vo.DiseaInsertVO;
import kr.spring.disease.vo.DiseaseVO;
import kr.spring.disease.vo.Item;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
public class DiseaseServiceImpl implements DiseaseService{
	
	@Autowired
	private DiseaseMapper mapper;
	@Override
	public void insertDis(DiseaseVO vo) {
		// TODO Auto-generated method stub
		mapper.insertDis(vo);
	}

	@Override
	public void updateDis() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<DiseaseVO> selectDisList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectDisList(map);
	}

	@Override
	public Integer selectDisCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectDisRowCount(map);
	}

	@Override
	public DiseaseVO getDis(String sickcd) {
		// TODO Auto-generated method stub
		return mapper.getDis(sickcd);
	}
	//diseaseCode Insert
	@Override
	public void insertDisCode(Item vo) {
		// TODO Auto-generated method stub
		mapper.insertDisCode(vo);
	}

	@Override
	public void deleteDisCode() {
		// TODO Auto-generated method stub
		mapper.deleteDisCode();
	}

	@Override
	public List<DiseaInsertVO> selectDisCodeList() {
		// TODO Auto-generated method stub
		return mapper.selectDisCodeList();
	}



}
