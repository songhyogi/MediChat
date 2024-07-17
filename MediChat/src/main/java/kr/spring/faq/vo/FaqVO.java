package kr.spring.faq.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FaqVO {
	private long faq_num;
	private long mem_num;
	private String faq_title;
	private String faq_content;
	private String f_reg_date;
	private String f_modify_date;
	private int faq_hit;
	private String f_category;
	
	private String id;
	
}
