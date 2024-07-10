package kr.spring.health.vo;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HealthyBlogVO {
	private long healthy_num;
	private long mem_num;
	private String healthy_title;
	private String healthy_content;
	private Date h_reg_date;
	private Date h_modify_date;
	private int healthy_hit;
	private String h_filename;
	private MultipartFile upload;
	private String id;
	
}
