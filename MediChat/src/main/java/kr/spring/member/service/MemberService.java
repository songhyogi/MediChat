package kr.spring.member.service;

import kr.spring.member.vo.MemberVO;
 
public interface MemberService {
	//==========일반 회원============
	//회원가입
	public void insertMember(MemberVO member);
	//회원상세정보
	public MemberVO selectMember(Long mem_num);
	//회원정보수정
	public void updateMember(MemberVO member);
	//비밀번호 수정
	public void updatePasswd(MemberVO member);
	//프로필 사진 수정
	public void updateProfile(MemberVO member);
	//회원탈퇴
	public void deleteMember(Long mem_num);
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

	//==========관리자============
	//회원등급수정
	public void updateAuth(MemberVO member);
}
