package kr.spring.notification.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NotificationVO {
	private Long noti_num;
	private Long mem_num;
	private Long noti_category;
	private Long noti_category_num;
	private String noti_message;
	private int noti_isRead;
	private String noti_readDate;
	private String noti_resDate;
	private String noti_createdDate;
	private String noti_link;
	private String noti_type;
	private int noti_priority;
}