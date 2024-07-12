package kr.spring.chat.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.spring.chat.vo.ChatFileVO;
import kr.spring.chat.vo.ChatMsgVO;
import kr.spring.chat.vo.ChatPaymentVO;
import kr.spring.chat.vo.ChatVO;
import kr.spring.reservation.vo.ReservationVO;

@Mapper
public interface ChatMapper {
	//해당 회원 채팅방 목록 불러오기 - 일반회원
	public List<ChatVO> selectChatListForMem(long mem_num);
	
	//해당 회원 채팅방 목록 불러오기 - 의사회원
	public List<ChatVO> selectChatListForDoc(long mem_num);
	
	//예약 내역을 기반으로 채팅방 만들기
	public void createChat(ReservationVO reservationVO);
	
	@Select("SELECT res_date, res_time FROM chat JOIN reservation USING(res_num) WHERE chat_num=#{chat_num}")
	public ReservationVO selectReservationByChatNum(long chat_num);
	public List<ChatMsgVO> selectMsg(long chat_num);
	
	public void insertChatFile(ChatFileVO chatFileVO);
	public void insertChatPayment(ChatPaymentVO chatPaymentVO);
	public void updateChatStatus(long chat_num);
}
