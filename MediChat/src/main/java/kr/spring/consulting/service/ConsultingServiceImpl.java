package kr.spring.consulting.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.consulting.dao.ConsultingMapper;
import kr.spring.consulting.vo.Con_ReVO;
import kr.spring.consulting.vo.ConsultingVO;

@Service
@Transactional
public class ConsultingServiceImpl implements ConsultingService{
	@Autowired
	private ConsultingMapper consultingMapper;
	
	
	@Override
	public void createConsulting(ConsultingVO consulting) {
		consultingMapper.insertConsulting(consulting);
	}


	@Override
	public List<ConsultingVO> getListConsulting(Map<String, Object> map) {
		return consultingMapper.selectListConsulting(map);
	}


	@Override
	public ConsultingVO getConsulting(Long con_num) {
		return consultingMapper.selectConsulting(con_num);
	}


	@Override
	public void modifyConsulting(ConsultingVO consulting) {
		consultingMapper.updateConsulting(consulting);
	}


	@Override
	public void removeConsulting(Long con_num) {
		consultingMapper.deleteConsulting(con_num);
	}


	@Override
	public void createCon_Re(Con_ReVO con_Re) {
		consultingMapper.insertCon_Re(con_Re);
	}


	@Override
	public List<Con_ReVO> getListCon_Re(Map<String, Object> map) {
		return consultingMapper.selectListCon_Re(map);
	}
	
}
