package kr.spring.video.service;

import java.util.List;
import java.util.Map;


import kr.spring.video.vo.VideoVO;


public interface VideoService {
	public void insertV(VideoVO vo);
	public void updateV(VideoVO vo);
	public void deleteV(Long video_num);
	public Integer selectCountV(Map<String,Object> map);
	public  VideoVO selectV(Long video_num);
	public List<VideoVO> selectVList(Map<String,Object> map);
	public void updateVhit(Long video_num);
}
