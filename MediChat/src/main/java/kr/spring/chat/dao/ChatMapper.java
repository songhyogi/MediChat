package kr.spring.chat.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.spring.chat.vo.ChatFileVO;
import kr.spring.chat.vo.ChatPaymentVO;

@Mapper
public interface ChatMapper {
	@Select("SELECT mem_name FROM member m JOIN reservation r ON (m.mem_num = r.doc_num) WHERE mem_auth=3")
	public String selectDoctorName(int res_num);
	/*@Select("SELECT * FROM reservation WHERE mem_num=#{mem_num} AND res_type=0")
	public List<ReservateionVO> selectReservation(int mem_num);
	@Insert("INSERT INTO chat(chat_num, mem_num, doc_num, chat_reg_date, chat_status) VALUES ")
	public void createChat(ReservationVO);*/
	public void insertChatFile(ChatFileVO chatFileVO);
	public void insertChatPayment(ChatPaymentVO chatPaymentVO);
	public void updateChatStatus(int chat_num);
}
