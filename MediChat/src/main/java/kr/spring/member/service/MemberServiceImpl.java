package kr.spring.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public void updateAu_id(String au_id, Long mem_num) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectAu_id(String au_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAu_id(Long mem_num) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void findId(MemberVO member) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void findPasswd(MemberVO member) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAuth(MemberVO member) {
		memberMapper.updateAuth(member);
	}

	@Override
	public MemberVO checkId(String mem_id) {
		return memberMapper.checkId(mem_id);
	}


}
