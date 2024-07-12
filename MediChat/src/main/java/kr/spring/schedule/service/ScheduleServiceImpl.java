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
	public String getRegularDayoff(Long doc_num) {
		return scheduleMapper.getRegularDayoff(doc_num);
	}

	@Override
	public Map<String,String> getWorkingHours(Long doc_num) {
		return scheduleMapper.getWorkingHours(doc_num);
	}

	@Override
	public List<Map<String, Object>> getHoliday(Long doc_num) {
		return scheduleMapper.getHoliday(doc_num);
	}

	@Override
	public void insertHoliday(Long doc_num) {
		scheduleMapper.insertHoliday(doc_num);
	}

}
