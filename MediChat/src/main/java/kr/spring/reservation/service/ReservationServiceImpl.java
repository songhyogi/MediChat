package kr.spring.reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.hospital.vo.HospitalVO;
import kr.spring.reservation.dao.ReservationMapper;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService{

	@Autowired
	private ReservationMapper reservationMapper;
	
	@Override
	public HospitalVO getHosHours(Long hos_num) {
		return reservationMapper.getHosHours(hos_num);
	}

}
