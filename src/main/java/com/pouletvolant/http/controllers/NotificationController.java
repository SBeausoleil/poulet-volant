package com.pouletvolant.http.controllers;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pouletvolant.http.payloads.ApiResponse;
import com.pouletvolant.http.requests.StoreNotification;
import com.pouletvolant.models.Notification;
import com.pouletvolant.models.User;
import com.pouletvolant.repositories.NotificationRepository;
import com.pouletvolant.repositories.UserRepository;
import com.pouletvolant.security.CurrentUser;
import com.pouletvolant.services.NotificationService;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private NotificationRepository notificationRepository;
	
	@Autowired
	private UserRepository userRepository;

	private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);
	
	@PostMapping("/store")
	public ApiResponse<Notification> saveOffer(@CurrentUser User currentUser, @RequestBody StoreNotification storeNotification) {
		logger.info("En processus d'enregistrement d'une notification.....");
		Notification notification = new Notification();
		notification.setDestinataire(currentUser);
		notification.setTexte(storeNotification.getTexte());
		return new ApiResponse<>(HttpStatus.OK.value(), "Notification saved successfully.", notificationRepository.save(notification));
	}
	
	@PostMapping("/storeDestinataire")
	public ApiResponse<Notification> saveOfferDestinataire(@RequestBody StoreNotification storeNotification) {
		logger.info("En processus d'enregistrement d'une notification.....");
		Notification notification = new Notification();
		User destinataire = userRepository.findByEmail(storeNotification.getDestinataire());
		notification.setDestinataire(destinataire);
		notification.setTexte(storeNotification.getTexte());
		return new ApiResponse<>(HttpStatus.OK.value(), "Notification saved successfully.", notificationRepository.save(notification));
	}
	
	@GetMapping("/list")
	public ApiResponse<List<Notification>> listNotificationByUser(@CurrentUser User currentUser) {
		logger.info("En processus de recherche de toutes les notification....");
		return new ApiResponse<>(HttpStatus.OK.value(), "", notificationService.getNotifications(currentUser.getId()));
	}
	
	@DeleteMapping("/{id}")
	public ApiResponse<Void> delete(@PathVariable long id) {
		logger.info("En processus d'une suppression d'une notification.....");
		notificationService.delete(id);
		return new ApiResponse<>(HttpStatus.OK.value(), "", null);
	}
	
	@GetMapping("/listUsers")
	public ApiResponse<List<Notification>> listUsers() {
		logger.info("En processus de recherche de toutes les utilisateurs ...");
		return new ApiResponse<>(HttpStatus.OK.value(), "", notificationService.getAllUsers());
	}
}
