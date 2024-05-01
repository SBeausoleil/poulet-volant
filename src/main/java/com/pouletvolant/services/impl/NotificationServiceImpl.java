package com.pouletvolant.services.impl;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pouletvolant.exceptions.FileStorageException;
import com.pouletvolant.exceptions.MyFileNotFoundException;
import com.pouletvolant.http.requests.StoreNotification;
import com.pouletvolant.models.Notification;
import com.pouletvolant.models.User;
import com.pouletvolant.repositories.NotificationRepository;
import com.pouletvolant.repositories.UserRepository;
import com.pouletvolant.services.NotificationService;

@Transactional
@Service(value = "notificationService")
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public Notification storeNotification(StoreNotification storeNotification) {
		Notification notification = new Notification();
		User user = new User();
		notification.setDestinataire(user);
		;
		notification.setTexte(storeNotification.getTexte());
		return notificationRepository.save(notification);
	}

	@Override
	public List<Notification> getNotifications(long id) {
		try {
			List<Notification> list = new ArrayList<>();
			notificationRepository.findByUserId(id).iterator().forEachRemaining(list::add);
			return list;
		} catch (Exception e) {
			throw new MyFileNotFoundException("Notification(s) not found", e);
		}
	}

	@Override
	public void delete(long id) {
		try {
			notificationRepository.deleteById(id);
		} catch (Exception e) {
			throw new FileStorageException("Could not delete notification. Please try again!", e);
		}

	}
	
	@Override
	public List<User> getAllUsers() {
		try {
			List<User> list = new ArrayList<>();
			userRepository.findAll().iterator().forEachRemaining(list::add);
			return list;
		} catch (Exception e) {
			throw new MyFileNotFoundException("User(s) not found", e);
		}
	}

}
