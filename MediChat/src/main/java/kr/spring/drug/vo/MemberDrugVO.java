package kr.spring.drug.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDrugVO {
	private int med_num;
	private long mem_num;
	@NotBlank
	private String med_name;
	@NotBlank
	private String med_date;
	@NotEmpty
	private int med_time;
	@NotBlank
	private String med_dosage;
	private String med_note;
}
