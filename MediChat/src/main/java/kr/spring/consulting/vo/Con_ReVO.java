package kr.spring.consulting.vo;

import kr.spring.doctor.vo.DoctorVO;
import kr.spring.hospital.vo.HospitalVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter
@ToString
public class Con_ReVO {
	private Long con_re_num;
	private Long con_num;
	private Long doc_num;
	private String con_re_content;
	private String con_re_rDate;
	private String con_re_mDate;
	private int con_re_status;
	
	private DoctorVO doctor;
	private HospitalVO hospital;
}