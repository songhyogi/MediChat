package kr.spring.community.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommunityVO {
	private long cbo_num;
	private long mem_num;
	private long cbo_type;
	@NotBlank
	private String cbo_title;
	@NotEmpty
	private String cbo_content;
	private int cbo_hit;
	private String cbo_rdate;
	private String cbo_mdate;
	private int cbo_report;		//누적 신고수
	
	//JOIN을 통해 사용
	private String mem_id;
	private String mem_photo;
	
	private int re_cnt;			//댓글 개수
	private int fav_cnt;			//좋아요 개수
}
