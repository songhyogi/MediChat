package kr.spring.member.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.member.vo.MemberVO;
 
@Mapper
public interface MemberMapper {
	//==========일반 회원============
	@Select("SELECT member_seq.nextval FROM dual")
	public Long selectMem_num();
	//회원가입
	@Insert("INSERT INTO member(mem_num,mem_id,mem_name) VALUES(#{mem_num},#{mem_id},#{mem_name})")
	public void insertMember(MemberVO member);
	public void insertMember_detail(MemberVO member);
	//회원상세정보
	@Select("SELECT * FROM member JOIN member_detail USING(mem_num) WHERE mem_num=#{mem_num}")
	public MemberVO selectMember(Long mem_num);
	//회원정보수정
	@Update("UPDATE member SET mem_name=#{mem_name} WHERE mem_num=#{mem_num}")
	public void updateMember(MemberVO member);
	public void updateMember_detail(MemberVO member);
	//비밀번호 수정
	@Update("UPDATE member_detail SET mem_passwd=#{mem_passwd} WHERE mem_num=#{mem_num}")
	public void updatePasswd(MemberVO member);
	//프로필 사진 수정
	@Update("UPDATE member SET mem_photo=#{mem_photo},mem_photoname=#{mem_photoname} WHERE mem_num=#{mem_num}")
	public void updateProfile(MemberVO member);
	//회원탈퇴
	@Update("UPDATE member SET mem_auth=0 WHERE mem_num=#{mem_num}")
	public void deleteMember(Long mem_num);
	@Delete("DELETE FROM member_detail WHERE mem_num=#{mem_num}")
	public void deleteMember_detail(MemberVO member);
	
	
	//자동 로그인
	public void updateAu_id(String au_id,Long mem_num);
	public void selectAu_id(String au_id);
	public void deleteAu_id(Long mem_num);
	
	//아이디 중복확인
	public MemberVO checkId(String mem_id);
	//아이디 찾기
	public void findId(MemberVO member);
	//비밀번호 찾기
	public void findPasswd(MemberVO member);
	
	//카카오 로그인
	@Select("SELECT * FROM member WHERE mem_id=#{mem_id}")
	public MemberVO checkUser(@Param("mem_id") String mem_id);
	//==========관리자============
	//회원등급수정
	@Update("UPDATE member SET mem_auth=#{mem_auth} WHERE mem_num=#{mem_num}")
	public void updateAuth(MemberVO member);
	
}
