package kr.spring.schedule.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.schedule.dao.ScheduleMapper;
import kr.spring.schedule.vo.DayoffVO;

@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService{

	@Autowired
	private ScheduleMapper scheduleMapper;

	@Override
	public List<DayoffVO> selectList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
