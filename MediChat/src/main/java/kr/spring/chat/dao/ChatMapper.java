package kr.spring.chat.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.spring.chat.vo.ChatFileVO;
import kr.spring.chat.vo.ChatPaymentVO;
import kr.spring.chat.vo.ChatVO;
import kr.spring.reservation.vo.ReservationVO;

@Mapper
public interface ChatMapper {
	//예약번호를 통해 의사 이름 구하기
	@Select("SELECT mem_name FROM member m JOIN reservation r ON (m.mem_num = r.doc_num) WHERE mem_auth=3")
	public String selectDoctorName(long res_num);
	
	//해당 회원 예약 내역 목록 불러오기
	@Select("SELECT * FROM reservation WHERE mem_num=#{mem_num} AND res_type=0")
	public List<ReservationVO> selectReservation(long mem_num);
	
	//예약 내역을 기반으로 채팅방 만들기
	public void createChat(ReservationVO reservationVO);
	
	//@Select("SELECT * FROM chat WHERE ")
	public ChatVO selectChatDetail(long res_num);
	public void insertChatFile(ChatFileVO chatFileVO);
	public void insertChatPayment(ChatPaymentVO chatPaymentVO);
	public void updateChatStatus(long chat_num);
}
