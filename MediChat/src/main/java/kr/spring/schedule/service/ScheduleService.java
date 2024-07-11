package kr.spring.schedule.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;

import kr.spring.schedule.vo.DayoffVO;

public interface ScheduleService {
	public String getRegularDayoff(Long doc_num);
	public Map<String,String> getWorkingHours(Long doc_num);
	
	public List<String> getDayoffTimes(Long doc_num, String doff_date);
	public void updateDayoffTimes(Long doc_num, String doff_date, List<String> timesToAdd, List<String> timesToRemove);
}
