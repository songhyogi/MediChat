package kr.spring.chat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.chat.dao.ChatMapper;
import kr.spring.chat.vo.ChatFileVO;
import kr.spring.chat.vo.ChatPaymentVO;
import kr.spring.chat.vo.ChatVO;
import kr.spring.reservation.vo.ReservationVO;

@Service
@Transactional
public class ChatServiceImpl implements ChatService{

	@Autowired
	ChatMapper chatMapper;
	
	@Override
	public String selectDoctorName(long res_num) {
		return chatMapper.selectDoctorName(res_num);
	}

	@Override
	public ChatVO selectChatDetail(long res_num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertChatFile(ChatFileVO chatFileVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertChatPayment(ChatPaymentVO chatPaymentVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateChatStatus(long chat_num) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ReservationVO> selectReservation(long mem_num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createChat(ReservationVO reservationVO) {
		// TODO Auto-generated method stub
		
	}

}
