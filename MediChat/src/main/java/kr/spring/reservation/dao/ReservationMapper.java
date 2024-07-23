package kr.spring.reservation.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.doctor.vo.DoctorVO;
import kr.spring.hospital.vo.HospitalVO;
import kr.spring.reservation.vo.ReservationVO;

@Mapper
public interface ReservationMapper {
	//병원진료시간 가져오기
	public HospitalVO getHosHours(Long hos_num);
	//특정 날짜와 시간에 근무하는 의사 가져오기
    public List<DoctorVO> getAvailableDoctors(Map<String, Object> params);
    //예약정보 저장하기
    public void insertReservation(ReservationVO reservation);
    //예약내역 가져오기(회원)
    public Integer selectCountByMem(long mem_num);
    public List<ReservationVO> getMyResList(Map<String,Object> map);
    //예약내역 삭제하기
    @Delete("DELETE FROM reservation WHERE res_num=#{res_num}")
    public void cancelReservation(Long res_num);
    //예약 내역 가져오기(의사)
    public Integer selectCountByDoc(Map<String,Object> map);
    public List<ReservationVO> getDocResList(Map<String,Object> map);
    //예약내역 업데이트 (0:예약승인대기, 1:진료예정, 2:진료완료, 3:예약취소)
    @Update("UPDATE reservation SET res_status=#{res_status} WHERE res_num=#{res_num}")
    public void updateReservation(Long res_num,Long res_status);
    //예약내역 존재 여부
    @Select("SELECT COUNT(*) FROM reservation WHERE res_date=#{res_date} AND res_time=#{res_time} AND doc_num=#{doc_num}")
    public Integer getResExist(Map<String,Object> map);
}
