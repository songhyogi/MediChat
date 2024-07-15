package kr.spring.notification.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.notification.dao.NotificationMapper;
import kr.spring.notification.vo.NotificationVO;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService{
	
	@Autowired
	private NotificationMapper notificationMapper;
	
	@Override
	public void insertNotification(NotificationVO notificationVO) {
		notificationMapper.insertNotification(notificationVO);
	}

	@Override
	public void readNotification(Long noti_num) {
		notificationMapper.readNotification(noti_num);
	}

	@Override
	public NotificationVO selectNotification(Long noti_num) {
		return notificationMapper.selectNotification(noti_num);
	}

	@Override
	public int selectCountNotification(Long mem_num) {
		return notificationMapper.selectCountNotification(mem_num);
	}

	@Override
	public List<NotificationVO> selectListNotification(Long mem_num) {
		return notificationMapper.selectListNotification(mem_num);
	}

	@Override
	public void deleteNotification(Long noti_num) {
		notificationMapper.deleteNotification(noti_num);
	}
}
