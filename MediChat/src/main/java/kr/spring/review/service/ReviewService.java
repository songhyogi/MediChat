package kr.spring.review.service;

import java.util.List;
import java.util.Map;

import kr.spring.review.vo.ReviewVO;

public interface ReviewService {
	public void insertReview(ReviewVO vo);
	public void updateReview(ReviewVO vo);
	public void deleteReview(Long rev_num);
	public Integer selectCountByHos(Map<String,Object> map);
	public List<ReviewVO> selectReviewListByHos(Map<String,Object> map);
	public Integer selectCountByMem(Map<String,Object> map);
	public List<ReviewVO> selectReviewListByMem(Map<String,Object> map);
	public Integer selectCountByDoc(Map<String,Object> map);
	public List<ReviewVO> selectReviewListByDoc(Map<String,Object> map);
	public ReviewVO selectReviewDetail(Long rev_num);
	public void clickHitReview(Long rev_num);
}
