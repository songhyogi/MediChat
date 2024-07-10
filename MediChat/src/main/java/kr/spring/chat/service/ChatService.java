package kr.spring.chat.service;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import kr.spring.chat.vo.ChatFileVO;
import kr.spring.chat.vo.ChatPaymentVO;
import kr.spring.chat.vo.ChatVO;
import kr.spring.reservation.vo.ReservationVO;

public interface ChatService {
		public List<ChatVO> selectChatListForMem(long mem_num);
		public List<ChatVO> selectChatListForDoc(long mem_num);
		public void createChat(ReservationVO reservationVO);
		public ChatVO selectChatDetail(long res_num);
		public void insertChatFile(ChatFileVO chatFileVO);
		public void insertChatPayment(ChatPaymentVO chatPaymentVO);
		public void updateChatStatus(long chat_num);
}
