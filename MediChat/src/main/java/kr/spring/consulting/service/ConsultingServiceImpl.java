package kr.spring.consulting.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.consulting.dao.ConsultingMapper;
import kr.spring.consulting.vo.Con_ReVO;
import kr.spring.consulting.vo.ConsultingVO;
import kr.spring.util.DurationFromNow;

@Service
@Transactional
public class ConsultingServiceImpl implements ConsultingService{
	@Autowired
	private ConsultingMapper consultingMapper;
	
	
	@Override
	public void createConsulting(ConsultingVO consulting) {
		consultingMapper.insertConsulting(consulting);
	}


	@Override
	public List<ConsultingVO> getListConsulting(Map<String, Object> map) {
		List<ConsultingVO> cList = consultingMapper.selectListConsulting(map);
		for(ConsultingVO c:cList) {
			c.setCon_rDate(DurationFromNow.getTimeDiffLabel(c.getCon_rDate()));
			if(c.getCon_type()==0) {
				c.setCon_type_name("전체");
			} else if(c.getCon_type()==1) {
				c.setCon_type_name("만성질환");
			} else if(c.getCon_type()==2) {
				c.setCon_type_name("여성질환");
			} else if(c.getCon_type()==3) {
				c.setCon_type_name("소화기질환");
			} else if(c.getCon_type()==4) {
				c.setCon_type_name("영양제");
			} else if(c.getCon_type()==5) {
				c.setCon_type_name("정신건강");
			} else if(c.getCon_type()==6) {
				c.setCon_type_name("처방약");
			} else if(c.getCon_type()==7) {
				c.setCon_type_name("탈모");
			} else if(c.getCon_type()==8) {
				c.setCon_type_name("통증");
			} else if(c.getCon_type()==9) {
				c.setCon_type_name("여드름,피부염");
			} else if(c.getCon_type()==10) {
				c.setCon_type_name("임신,성고민");
			}
		}
		
		return cList;
	}


	@Override
	public ConsultingVO getConsulting(Long con_num) {
		ConsultingVO c = consultingMapper.selectConsulting(con_num);
		if(c.getCon_type()==0) {
			c.setCon_type_name("전체");
		} else if(c.getCon_type()==1) {
			c.setCon_type_name("만성질환");
		} else if(c.getCon_type()==2) {
			c.setCon_type_name("여성질환");
		} else if(c.getCon_type()==3) {
			c.setCon_type_name("소화기질환");
		} else if(c.getCon_type()==4) {
			c.setCon_type_name("영양제");
		} else if(c.getCon_type()==5) {
			c.setCon_type_name("정신건강");
		} else if(c.getCon_type()==6) {
			c.setCon_type_name("처방약");
		} else if(c.getCon_type()==7) {
			c.setCon_type_name("탈모");
		} else if(c.getCon_type()==8) {
			c.setCon_type_name("통증");
		} else if(c.getCon_type()==9) {
			c.setCon_type_name("여드름,피부염");
		} else if(c.getCon_type()==10) {
			c.setCon_type_name("임신,성고민");
		}
		return c;
	}


	@Override
	public void modifyConsulting(ConsultingVO consulting) {
		consultingMapper.updateConsulting(consulting);
	}


	@Override
	public void removeConsulting(Long con_num) {
		consultingMapper.deleteAllCon_Re(con_num);
		consultingMapper.deleteConsulting(con_num);
	}


	@Override
	public void createCon_Re(Con_ReVO con_Re) {
		consultingMapper.insertCon_Re(con_Re);
	}


	@Override
	public List<Con_ReVO> getListCon_Re(Map<String, Object> map) {
		return consultingMapper.selectListCon_Re(map);
	}
	
}
