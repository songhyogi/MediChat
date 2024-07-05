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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateMember(MemberVO member) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePasswd(MemberVO member) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateProfile(MemberVO member) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteMember(Long mem_num) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

}
