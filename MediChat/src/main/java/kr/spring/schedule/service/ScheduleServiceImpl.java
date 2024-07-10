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
	public List<String> getDayoffTimes(Long doc_num, String doff_date) {
		return scheduleMapper.getDayoffTimes(doc_num, doff_date);
	}

	@Override
	public void updateDayoffTimes(Long doc_num, String doff_date, List<String> timesToAdd, List<String> timesToRemove) {
		for (String time : timesToAdd) {
            scheduleMapper.insertDayoff(doc_num, doff_date, time);
        }
        for (String time : timesToRemove) {
            scheduleMapper.deleteDayoff(doc_num, doff_date, time);
        }
	}
	
}
