package kr.spring.chat.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatVO {
	private long chat_num;
	private long mem_num;
	private long doc_num;
	private Date chat_reg_date;
	private long chat_status;
	
	//조인으로 생성
	private String doc_name;	//의사 이름
	private String mem_name;	//환자 이름
	private String res_num; 	//예약 번호
	private String res_date;	//예약 날짜
	private String res_time;	//예약 시간
}
