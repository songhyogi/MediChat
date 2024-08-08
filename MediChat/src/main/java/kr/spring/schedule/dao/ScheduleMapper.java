package kr.spring.schedule.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.holiday.vo.HolidayVO;
import kr.spring.reservation.vo.ReservationVO;

@Mapper
public interface ScheduleMapper {
    // 정기휴무요일 가져오기
    @Select("SELECT doc_off FROM doctor_detail WHERE doc_num=#{doc_num}")
    public String getRegularDayoff(Long doc_num);
    // 근무시작시간, 근무종료시간 가져오기
    @Select("SELECT doc_stime, doc_etime FROM doctor_detail WHERE doc_num=#{doc_num}")
    public Map<String, String> getWorkingHours(Long doc_num);
    // 개별휴무일 가져오기
    @Select("SELECT * FROM holiday WHERE doc_num=#{doc_num}")
    public List<HolidayVO> getHoliday(Long doc_num);
    // 개별휴무일 등록
    @Insert("INSERT INTO holiday (holi_num, doc_num, holi_date, holi_time, holi_status) VALUES (holi_seq.nextval, #{doc_num}, #{holi_date}, #{holi_time}, #{holi_status})")
    public void insertHoliday(HolidayVO holiday);
    // 개별휴무일 업데이트
    @Update("UPDATE holiday SET holi_status = #{holi_status} WHERE doc_num = #{doc_num} AND holi_date = #{holi_date} AND holi_time = #{holi_time}")
    public void updateHoliday(HolidayVO holiday);
    // 개별휴무일 존재 여부 확인
    @Select("SELECT COUNT(*) FROM holiday WHERE doc_num = #{doc_num} AND holi_date = #{holi_date} AND holi_time = #{holi_time}")
    public int countHoliday(HolidayVO holiday);
}
