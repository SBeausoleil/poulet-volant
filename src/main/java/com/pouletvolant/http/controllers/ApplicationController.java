package com.pouletvolant.http.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pouletvolant.http.payloads.SimplifiedApplication;
import com.pouletvolant.http.requests.UpdateApplication;
import com.pouletvolant.models.Application;
import com.pouletvolant.models.Offer;
import com.pouletvolant.models.Student;
import com.pouletvolant.repositories.ApplicationRepository;
import com.pouletvolant.repositories.OfferRepository;
import com.pouletvolant.security.CurrentUser;
import com.pouletvolant.services.OrganizationHolderService;
import com.pouletvolant.services.PersistenceComponent;

@RestController
@RequestMapping("/api/application")
public class ApplicationController {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

	@Autowired
	private OfferRepository offerRepo;

	@Autowired
	private ApplicationRepository appRepo;

	@Autowired
	private OrganizationHolderService holderService;

	@Autowired
	@Qualifier(value = "threadPoolTaskExecutor")
	private TaskExecutor taskExecutor;
	private boolean useMultiThreading = true;

	@Autowired
	private PersistenceComponent persistence;

	@PostMapping("/offer/{id}")
	public ResponseEntity<Void> store(@PathVariable long id, @CurrentUser Student user) {
		logger.info("store(): " + id);
		if (user == null)
			throw new IllegalAccessError("The current user is not a student.");
		Optional<Offer> optionnalOffer = offerRepo.findById(id);
		if (optionnalOffer.isEmpty()) {
			logger.info("Offer not found.");
			return ResponseEntity.notFound().build();
		}

		Offer offer = optionnalOffer.get();
		var application = new Application(offer, user);
		appRepo.save(application);

		if (useMultiThreading) {
			// Blocked out because starting a new thread would require a new hibernate
			// session.
			taskExecutor.execute(() -> {
				Session session = persistence.sessionFactory().openSession();
				Offer localOffer = (Offer) session.merge(offer);
				Application localApplication = (Application) session.merge(application);
				sendNotifications(localOffer, localApplication);
			});
		} else {
			sendNotifications(offer, application);
		}
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}/simplified")
	public ResponseEntity<SimplifiedApplication> get(@PathVariable long id) {
		logger.info("get(): " + id);
		Optional<Application> application = appRepo.findById(id);
		if (application.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(new SimplifiedApplication(application.get()));
	}

	@PutMapping("/{id}/update")
	public ResponseEntity<SimplifiedApplication> update(@PathVariable long id, @RequestBody UpdateApplication request) {
		logger.info("update(): " + id + ", " + request);
		Optional<Application> appFind = appRepo.findById(id);
		if (appFind.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Application app = appFind.get();
		app.setStatus(request.getStatus());
		appRepo.save(app);
		return ResponseEntity.ok().body(new SimplifiedApplication(app));
	}

	/**
	 * @param offer
	 * @param application
	 */
	private void sendNotifications(Offer offer, Application application) {
		try {
			holderService.notifyOfNewApplication(offer, application);
		} catch (IOException | MessagingException e) {
			logger.error("Error when sending notification to the holder of the offer.", e);
		}
	}

	@GetMapping("/offer/{id}/simplified/mine")
	public ResponseEntity<SimplifiedApplication> getMine(@PathVariable long id, @CurrentUser Student user) {
		logger.info("getMine(): " + id);
		Optional<Application> application = appRepo.findByStudentIdAndOfferId(user.getId(), id);
		return application.isPresent() ? ResponseEntity.ok(new SimplifiedApplication(application.get()))
				: ResponseEntity.notFound().build();
	}

	@GetMapping("/offer/{id}/simplified")
	public List<SimplifiedApplication> getForOffer(@PathVariable long id) {
		logger.info("getForOffer(): " + id);
		List<Application> applications = appRepo.findByOfferId(id);
		var simple = new ArrayList<SimplifiedApplication>(applications.size());
		for (Application application : applications) {
			simple.add(new SimplifiedApplication(application));
		}
		return simple;
	}

	@GetMapping("/all")
	public ResponseEntity<SimplifiedApplication[]> getAll() {
		logger.info("getAll()");
		List<Application> applications = appRepo.findAll(Sort.by("updatedAt").descending());
		Iterator<Application> iterator = applications.iterator();
		SimplifiedApplication[] simplified = new SimplifiedApplication[applications.size()];
		for (int i = 0; i < simplified.length; i++) {
			simplified[i] = new SimplifiedApplication(iterator.next());
		}
		return ResponseEntity.ok(simplified);
	}
}
