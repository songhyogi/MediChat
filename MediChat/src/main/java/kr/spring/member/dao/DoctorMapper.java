package kr.spring.member.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.spring.hospital.vo.HospitalVO;
import kr.spring.member.vo.DoctorVO;

@Mapper
public interface DoctorMapper {
	//==========의사 회원============
	@Select("SELECT member_seq.nextval FROM dual")
	public Long selectDoc_num();
	//회원가입
	@Insert("INSERT INTO member(mem_num,mem_id,mem_name,mem_photo) VALUES(#{mem_num},#{mem_id},#{mem_name},#{mem_photo})")
	public void insertDoctor(DoctorVO doctor);
	public void insertDoctor_detail(DoctorVO doctor);
	//병원 리스트
	public List<HospitalVO> getHosList(Map<String, String> map);
	//회원상세정보
	public DoctorVO selectDoctor(Long doc_num);
	//회원정보 수정
	public void updateDoctor(DoctorVO doctor);
	public void updateDoctor_detail(DoctorVO doctor);
	//비밀번호 수정
	public void updateDocPasswd(DoctorVO doctor);
	//프로필 사진 저장
	@Insert("INSERT INTO member(mem_photo,mem_photoname) VALUES(#{mem_photo},#{mem_photoname})")
	public void uploadDocProfile(DoctorVO doctor);
	//회원탈퇴
	public void deleteDoctor(Long doc_num);
	public void deleteDoctor_detail(DoctorVO doctor);
	
	//아이디 중복확인
	public DoctorVO checkId(String mem_id);
	//아이디 찾기
	public void findId(DoctorVO doctor);
	//비밀번호 찾기
	public void findPasswd(DoctorVO doctor);
}
