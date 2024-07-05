package kr.spring.member.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.spring.member.vo.MemberVO;

@Mapper
public interface MemberMapper {
	//==========일반 회원============
	@Select("SELECT member_seq.nextval FROM dual")
	public Long selectMem_num();
	//회원가입(일반,의사 공통)
	@Insert("INSERT INTO member(mem_num,mem_id,mem_name) VALUES(#{mem_num},#{mem_id},#{mem_name})")
	public void insertMember(MemberVO member);
	//회원가입(일반회원)
	public void insertMember_detail(MemberVO member);
	//회원상세정보
	public MemberVO selectMember(Long mem_num);
	//회원정보수정(일반,의사 공통)
	public void updateMember(MemberVO member);
	//회원정보수정(일반회원)
	public void updateMember_detail(MemberVO member);
	//비밀번호 수정
	public void updatePasswd(MemberVO member);
	//프로필 사진 수정
	public void updateProfile(MemberVO member);
	//회원탈퇴(일반,의사 공통)
	public void deleteMember(Long mem_num);
	//회원탈퇴(일반회원)
	public void deleteMember_detail(MemberVO member);
	
	
	//자동 로그인
	public void updateAu_id(String au_id,Long mem_num);
	public void selectAu_id(String au_id);
	public void deleteAu_id(Long mem_num);
	
	//아이디 찾기
	public void findId(MemberVO member);
	//비밀번호 찾기
	public void findPasswd(MemberVO member);
	
	//==========관리자============
	//회원등급수정
	public void updateAuth(MemberVO member);
	
}
