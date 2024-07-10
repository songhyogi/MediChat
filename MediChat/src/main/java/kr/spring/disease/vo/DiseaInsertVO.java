package kr.spring.disease.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DiseaInsertVO {
	private String sickcd;
	private String sicknm;
	private String  main_sick;
	private int dsbjt_cd;
	private String dis_department;
}
