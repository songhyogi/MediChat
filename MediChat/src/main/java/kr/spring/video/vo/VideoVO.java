package kr.spring.video.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VideoVO {
	 private long video_num;
	 private long mem_num;
	 private String video_title;
	 private String video_content;
	 private String v_reg_date;
	 private String v_modify_date;
	 private int video_hit;
	 private String v_category;
	 
	 private String id;
}
