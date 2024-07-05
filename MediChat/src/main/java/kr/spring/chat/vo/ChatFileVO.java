package kr.spring.chat.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatFileVO {
	private int file_num;
	private String file_name;
	private int mem_num;
	private int doc_num;
	private int file_type;
	private Date file_reg_date;
}
