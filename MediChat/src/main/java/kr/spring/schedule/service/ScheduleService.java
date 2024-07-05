package kr.spring.schedule.service;

import java.util.List;
import java.util.Map;

import kr.spring.schedule.vo.DayoffVO;

public interface ScheduleService {
	public List<DayoffVO> selectList(Map<String,Object> map);
}
