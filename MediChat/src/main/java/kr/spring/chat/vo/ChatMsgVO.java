package kr.spring.chat.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatMsgVO {
	private int msg_num;
	private int chat_num;
	private String msg_content;
	private int msg_sender_type;
}
