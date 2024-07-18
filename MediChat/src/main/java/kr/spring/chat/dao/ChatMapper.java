package kr.spring.chat.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
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
	//채팅방 정보 구하기
	@Select("SELECT * FROM chat WHERE chat_num=#{chat_num}")
	public ChatVO selectChat(long chat_num);
	//예약날짜 및 시간 불러오기
	@Select("SELECT res_date, res_time FROM chat JOIN reservation USING(res_num) WHERE chat_num=#{chat_num}")
	public ReservationVO selectReservationByChatNum(long chat_num);
	//채팅방 메시지 불러오기
	@Select("SELECT * FROM chat_msg	WHERE chat_num=#{chat_num}")
	public List<ChatMsgVO> selectMsg(long chat_num);
	//채팅방 메시지 입력
	@Insert("INSERT INTO chat_msg(msg_num, chat_num, msg_content, msg_sender_type) VALUES (msg_seq.nextval, #{chat_num}, #{msg_content}, #{msg_sender_type})")
	public void insertMsg(ChatMsgVO chatMsgVO);
	//채팅방 이미지 전송
	public void insertImage(ChatMsgVO chatMsgVO);
	//진료 종료 시 파일 전송
	public void insertChatFile(ChatFileVO chatFileVO);
	//파일 번호 불러오기
	@Select("SELECT file_num FROM chat_files WHERE chat_num=#{chat_num} AND file_name=#{file_name}")
	public long selectFileNum(long chat_num,String file_name);
	//불러온 파일 번호를 토대로 파일 지우기
	@Delete("DELETE FROM chat_files WHERE file_num=#{file_num}")
	public void deleteFile(long file_num);
	public void insertChatPayment(ChatPaymentVO chatPaymentVO);
	public void updateChatStatus(long chat_num);
}
