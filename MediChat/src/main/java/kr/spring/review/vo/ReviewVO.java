package kr.spring.review.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewVO {
	private long rev_num;
	private long res_num;
	private long mem_num;
	private long hos_num;
	private float rev_grade;
	private String rev_title;
	private String rev_content;
	private String rev_reg;
	private String rev_modify;
	private int rev_hit;
	private int rev_report;
	
	private String mem_id;
	
	private String hos_name;
}
