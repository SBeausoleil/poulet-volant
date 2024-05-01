package com.pouletvolant.services;

import java.util.List;

import com.pouletvolant.http.requests.StoreNotification;
import com.pouletvolant.models.Notification;
import com.pouletvolant.models.User;

public interface NotificationService {

	Notification storeNotification(StoreNotification storeNotification);
	
	List<Notification> getNotifications(long id);
	
	void delete(long id);
	
	List<User> getAllUsers();
}
