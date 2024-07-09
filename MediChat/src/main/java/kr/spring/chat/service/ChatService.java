package kr.spring.chat.service;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import kr.spring.chat.vo.ChatFileVO;
import kr.spring.chat.vo.ChatPaymentVO;
import kr.spring.chat.vo.ChatVO;
import kr.spring.reservation.vo.ReservationVO;

public interface ChatService {
	@Select("SELECT mem_name FROM member m JOIN reservation r ON (m.mem_num = r.doc_num) WHERE mem_auth=3")
	public String selectDoctorName(long res_num);
	//@Select("SELECT * FROM reservation WHERE mem_num=#{mem_num} AND res_type=0")
	public List<ReservationVO> selectReservation(long mem_num);
	public void createChat(ReservationVO reservationVO);
	//@Select("SELECT * FROM chat WHERE ")
	public ChatVO selectChatDetail(long res_num);
	public void insertChatFile(ChatFileVO chatFileVO);
	public void insertChatPayment(ChatPaymentVO chatPaymentVO);
	public void updateChatStatus(long chat_num);
}
