package kr.spring.schedule.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.spring.schedule.vo.DayoffVO;

@Mapper
public interface ScheduleMapper {
    //휴무일
	
	public List<DayoffVO> selectList(Map<String,Object> map);
}
