package kr.spring.member.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.consulting.vo.ConsultingVO;
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
	//회원목록
	public List<MemberVO> getMemList(Map<String,Object> map);
	public Integer selectRowCount(Map<String, Object> map);
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
	
	//나의 의료상담 목록
	public List<ConsultingVO> consultList(Map<String,Object> map);
	
	//자동 로그인
	@Update("UPDATE member_detail SET mem_au_id=#{mem_au_id} WHERE mem_num=#{mem_num}")
	public void updateMem_au_id(String au_id,Long mem_num);
	@Select("SELECT m.mem_num,m.mem_id,m.mem_auth,d.mem_au_id,d.passwd,m.mem_name,d.mem_email FROM member m JOIN member_detail d ON m.mem_num=d.mem_num WHERE mem_num=#{mem_num}")
	public MemberVO selectMem_au_id(String au_id);
	@Update("UPDATE member_detail SET mem_au_id='' WHERE mem_num=#{mem_num}")
	public void deleteMem_au_id(Long mem_num);
	
	//아이디 중복확인
	public MemberVO checkId(String mem_id);
	//아이디 찾기
	@Select("SELECT m.mem_id FROM member m JOIN member_detail d ON m.mem_num=d.mem_num WHERE mem_name=#{mem_name} AND mem_email=#{mem_email}")
	public MemberVO findId(MemberVO member);
	//비밀번호 찾기
	@Update("UPDATE member_detail SET mem_passwd=#{mem_passwd} WHERE mem_num=#{mem_num}")
	public void findPasswd(MemberVO member);
	
	//카카오 로그인
	@Select("SELECT * FROM member WHERE mem_id=#{mem_id}")
	public MemberVO checkUser(@Param("mem_id") String mem_id);
	//==========관리자============
	//회원등급수정
	@Update("UPDATE member SET mem_auth=1 WHERE mem_num=#{mem_num}")
	public void updateAuth(MemberVO member);
	@Update("UPDATE member SET mem_auth=2 WHERE mem_num=#{mem_num}")
	public void cancelAuth(MemberVO member);
	
}
