package kr.spring.schedule.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.holiday.vo.HolidayVO;
import kr.spring.schedule.vo.DayoffVO;

public interface ScheduleService {
	//정기휴무요일 가져오기
	public String getRegularDayoff(Long doc_num);
	//근무시작시간, 근무종료시간 가져오기
	public Map<String,String> getWorkingHours(Long doc_num);
	//개별휴무일 가져오기
	public List<HolidayVO> getHoliday(Long doc_num);
	//개별휴무일 등록
    public void insertHoliday(HolidayVO holiday);
    //개별휴무일 업데이트
    public void updateHoliday(HolidayVO holiday);
    //개별휴무일 존재 여부 확인
    public int countHoliday(HolidayVO holiday);
}
