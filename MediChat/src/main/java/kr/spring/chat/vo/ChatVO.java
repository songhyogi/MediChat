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
}
