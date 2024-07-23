package kr.spring.community.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommunityReplyVO {
	private long cre_num;
	private long cbo_num;	//게시글 번호
	private long mem_num;	//회원번호
	private String cre_content;
	private String cre_rdate;
	private String cre_mdate;
	private long cre_level;
	private long cre_ref;
	private long cre_report;
	
	private String mem_id;
	
	private int click_num;
	private int refav_cnt;	//댓글 좋아요 개수
}
