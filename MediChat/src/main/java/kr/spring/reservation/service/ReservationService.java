package kr.spring.reservation.service;

import java.util.List;
import java.util.Map;

import kr.spring.doctor.vo.DoctorVO;
import kr.spring.hospital.vo.HospitalVO;
import kr.spring.reservation.vo.ReservationVO;

public interface ReservationService {
	//병원진료시간 가져오기
	public HospitalVO getHosHours(Long hos_num);
	//특정 날짜와 시간에 근무하는 의사 가져오기
	public List<DoctorVO> getAvailableDoctors(Map<String, Object> params);
	//예약정보 저장하기
    public void insertReservation(ReservationVO reservationVO);
    //예약내역 가져오기
    public Integer selectCountByMem(Map<String,Object>map);
    public List<ReservationVO> getMyResList(Map<String,Object> map);

}
