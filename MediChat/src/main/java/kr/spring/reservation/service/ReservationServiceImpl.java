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
    public void insertReservation(ReservationVO reservation) {
        reservationMapper.insertReservation(reservation);
    }

	@Override
	public Integer selectCountByMem(long mem_num) {
		return reservationMapper.selectCountByMem(mem_num);
	}

	@Override
	public List<ReservationVO> getMyResList(Map<String, Object> map) {
		return reservationMapper.getMyResList(map);
	}

	@Override
	public void cancelReservation(Long res_num) {
		reservationMapper.cancelReservation(res_num);
	}

	@Override
	public Integer selectCountByDoc(Map<String, Object> map) {
		return reservationMapper.selectCountByDoc(map);
	}

	@Override
	public List<ReservationVO> getDocResList(Map<String, Object> map) {
		return reservationMapper.getDocResList(map);
	}

	@Override
	public void updateReservation(Long res_num,Long res_status) {
		reservationMapper.updateReservation(res_num,res_status);
	}

	@Override
	public List<String> getResExist(Map<String, Object> map) {
		return reservationMapper.getResExist(map);
	}

	@Override
	public ReservationVO getReservationById(long res_num) {
		return reservationMapper.getReservationById(res_num);
	}

	
}
