package kr.spring.disease.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DiseaseVO {
	private long dis_num;
	private String dis_name;
	private String symptoms;
	private String dis_departmemt;
	private int dis_hit;
	

}
