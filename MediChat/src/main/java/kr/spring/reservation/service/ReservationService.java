package kr.spring.reservation.service;

import kr.spring.hospital.vo.HospitalVO;

public interface ReservationService {
	//병원진료시간 가져오기
	public HospitalVO getHosHours(Long hos_num);
}
