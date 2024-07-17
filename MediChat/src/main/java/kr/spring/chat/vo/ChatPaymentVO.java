package kr.spring.chat.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatPaymentVO {
	private int pay_num;
	private int chat_num;
	private int mem_num;
	private int pay_amount;
}
