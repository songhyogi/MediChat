package kr.spring.schedule.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.spring.holiday.vo.HolidayVO;

@Mapper
public interface ScheduleMapper {
	//정기휴무요일 가져오기
	@Select("SELECT doc_off FROM doctor_detail WHERE doc_num=#{doc_num}")
	public String getRegularDayoff(Long doc_num);
	
	//근무시작시간, 근무종료시간 가져오기
	@Select("SELECT doc_stime,doc_etime FROM doctor_detail WHERE doc_num=#{doc_num}")
	public Map<String,String> getWorkingHours(Long doc_num);
	
	//개별휴무일 가져오기
	@Select("SELECT * FROM holiday WHERE doc_num=#{doc_num}")
	public List<HolidayVO> getHoliday(Long doc_num);
	
	//개별휴무일 등록
	@Insert("INSERT INTO holiday(holi_num,doc_num,holi_date,holi_time,holi_status) VALUES(holi_seq.nextval,#{doc_num},#{holi_date},#{holi_time},#{holi_status})")
	public void insertHoliday(Long doc_num);
	
	//휴무시간 가져오기
	@Select("SELECT doff_time FROM dayoff WHERE doc_num=#{doc_num} AND doff_date=#{doff_date}")
	public List<String> getDayoffTimes(Long doc_num, String doff_date);
	
}
