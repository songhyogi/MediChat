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
	//해당 회원의 생성된 채팅 내역 목록 불러오기 - 일반회원
	public List<ChatVO> selectChatListForMem(long mem_num);
	
	//해당 회원의 생성된 채팅 내역 목록 불러오기 - 의사회원
	public List<ChatVO> selectChatListForDoc(long doc_num);
	
	//예약 내역을 기반으로 채팅방 만들기
	public void createChat(ReservationVO reservationVO);
	
	//@Select("SELECT * FROM chat WHERE ")
	public ChatVO selectChatDetail(long res_num);
	public void insertChatFile(ChatFileVO chatFileVO);
	public void insertChatPayment(ChatPaymentVO chatPaymentVO);
	public void updateChatStatus(long chat_num);
}
