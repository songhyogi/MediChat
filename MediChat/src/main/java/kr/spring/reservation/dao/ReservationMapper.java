package kr.spring.reservation.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.spring.hospital.vo.HospitalVO;

@Mapper
public interface ReservationMapper {
	//병원진료시간 가져오기
	public HospitalVO getHosHours(Long hos_num);
}
