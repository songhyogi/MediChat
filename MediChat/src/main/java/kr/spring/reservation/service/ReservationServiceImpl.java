package kr.spring.reservation.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.doctor.vo.DoctorVO;
import kr.spring.hospital.vo.HospitalVO;
import kr.spring.reservation.dao.ReservationMapper;
import kr.spring.reservation.vo.ReservationVO;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService{

	@Autowired
	private ReservationMapper reservationMapper;
	
	@Override
	public HospitalVO getHosHours(Long hos_num) {
		return reservationMapper.getHosHours(hos_num);
	}

	@Override
	public List<DoctorVO> getAvailableDoctors(Map<String, Object> params) {
		return reservationMapper.getAvailableDoctors(params);
	}

	@Override
	public void insertReservation(ReservationVO reservationVO) {
		reservationMapper.insertReservation(reservationVO);
	}

}
