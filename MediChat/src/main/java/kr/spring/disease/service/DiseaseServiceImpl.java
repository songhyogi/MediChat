package kr.spring.disease.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.spring.disease.dao.DiseaseMapper;
import kr.spring.disease.vo.DiseaseVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
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
		return null;
	}

	@Override
	public Integer selectDisCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DiseaseVO getDis(Long dis_num) {
		// TODO Auto-generated method stub
		return null;
	}

}
