package kr.spring.chat.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatVO {
	private int chat_num;
	private int mem_num;
	private int doc_num;
	private Date chat_reg_date;
	private int chat_status;
}
