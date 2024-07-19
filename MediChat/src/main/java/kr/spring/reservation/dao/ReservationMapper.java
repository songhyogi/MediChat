package kr.spring.reservation.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

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
    public void insertReservation(ReservationVO reservationVO);
}
