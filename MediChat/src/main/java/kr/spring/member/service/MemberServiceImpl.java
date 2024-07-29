package kr.spring.member.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.consulting.vo.ConsultingVO;
import kr.spring.member.dao.MemberMapper;
import kr.spring.member.vo.MemberVO;
 
@Service
@Transactional
public class MemberServiceImpl implements MemberService{
	@Autowired
	MemberMapper memberMapper;

	@Override
	public void insertMember(MemberVO member) {
		member.setMem_num(memberMapper.selectMem_num());
		memberMapper.insertMember(member);
		memberMapper.insertMember_detail(member);
	}

	@Override
	public MemberVO selectMember(Long mem_num) {
		return memberMapper.selectMember(mem_num);
	}

	@Override
	public void updateMember(MemberVO member) {
		memberMapper.updateMember(member);
		memberMapper.updateMember_detail(member);
	}

	@Override
	public void updatePasswd(MemberVO member) {
		memberMapper.updatePasswd(member);
	}

	@Override
	public void updateProfile(MemberVO member) {
		memberMapper.updateProfile(member);
	}
	
	@Override
	public void deleteMember(Long mem_num) {
		memberMapper.deleteMember(mem_num);
	}

	@Override
	public void deleteMember_detail(MemberVO member) {
		memberMapper.deleteMember_detail(member);
	}
	
	@Override
	public void updateMem_au_id(String mem_au_id, Long mem_num) {
		memberMapper.updateMem_au_id(mem_au_id, mem_num);
	}

	@Override
	public MemberVO selectMem_au_id(String mem_au_id) {
		return memberMapper.selectMem_au_id(mem_au_id);
	}

	@Override
	public void deleteMem_au_id(Long mem_num) {
		memberMapper.deleteMem_au_id(mem_num);
	}

	@Override
	public MemberVO findId(MemberVO member) {
		return memberMapper.findId(member);
	}

	@Override
	public void findPasswd(MemberVO member) {
		memberMapper.findPasswd(member);
	}

	@Override
	public void updateAuth(MemberVO member) {
		memberMapper.updateAuth(member);
	}
	@Override
	public void cancelAuth(MemberVO member) {
		memberMapper.cancelAuth(member);
	}
	@Override
	public MemberVO checkId(String mem_id) {
		return memberMapper.checkId(mem_id);
	}

	@Override
	public MemberVO checkUser(String mem_id) {
		return memberMapper.checkUser(mem_id);
	}

	@Override
	public List<MemberVO> getMemList(Map<String, Object> map) {
		return memberMapper.getMemList(map);
	}

	@Override
	public Integer selectRowCount(Map<String, Object> map) {
		return memberMapper.selectRowCount(map);
	}

	@Override
	public List<ConsultingVO> consultList(Map<String, Object> map) {
		return memberMapper.consultList(map);
	}

}
