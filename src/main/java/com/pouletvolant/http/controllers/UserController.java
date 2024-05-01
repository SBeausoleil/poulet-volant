package com.pouletvolant.http.controllers;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pouletvolant.http.requests.StoreUser;
import com.pouletvolant.models.MandatoryRedirect;
import com.pouletvolant.models.User;
import com.pouletvolant.models.UserFactory;
import com.pouletvolant.repositories.MandatoryRedirectRepository;
import com.pouletvolant.repositories.UserRepository;
import com.pouletvolant.security.CurrentUser;
import com.pouletvolant.services.HashingService;
import com.pouletvolant.util.ResponseUtil;

@RestController
@RequestMapping("/api/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private UserFactory userFactory;
	@Autowired
	private MandatoryRedirectRepository redirectRepo;
	@Autowired
	private ResponseUtil responseUtil;

	@Transactional
	@GetMapping("/me")
	public User me(@CurrentUser User currentUser) {
		return currentUser;
	}

	@PostMapping("/store")
	public ResponseEntity<Void> store(@Valid @RequestBody StoreUser request) {
		logger.info("Registering a new user. : " + request);
		System.out.println(request.getPhoneNumber());
		User user = userFactory.newUser(User.Type.valueOf(request.getUserType()), request.getFirstName(),
				request.getLastName(), request.getEmail(), request.getPhoneNumber(), request.getPassword());
		try {
			user = userRepo.save(user);
		} catch (Exception e) {
			// e is actually an SQLIntegrityConstraintViolationException, except since that exception is hidden, we can't catch it directly.
			return ResponseEntity.badRequest().build();
		}
		if (user.getType() == User.Type.EMPLOYER) {
			redirectRepo.save(new MandatoryRedirect("/organization/create", user));
		} else if (user.getType() == User.Type.STUDENT) {
			redirectRepo.save(new MandatoryRedirect("/cv", user));
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PostMapping("/changeMail")
	public ResponseEntity<String> updateMail(@RequestBody User change, @CurrentUser User current) {
		if (change.getEmail().equals(""))
			return ResponseEntity.ok("Vide");

		if (change.getEmail() != current.getEmail()) {
			Boolean alreadyExist = userRepo.findByEmail(change.getEmail()) == null ? true : false;
			if (!alreadyExist)
				return ResponseEntity.ok("Exist");
			current.setEmail(change.getEmail());
			userRepo.saveAndFlush(current);
		}
		return new ResponseEntity<>("Modified", responseUtil.insertUserUpdateInstruction(current, null), HttpStatus.OK);
	}

	@PostMapping("/changeMDP")
	public String updateMDP(@RequestBody String change, @CurrentUser User current) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			@SuppressWarnings("unchecked")
			Map<String, String> map = mapper.readValue(change, Map.class);
			if (map.get("password").equals(""))
				return "Vide";
			current.setPasswordHash(new HashingService().encode(map.get("password")));
			userRepo.saveAndFlush(current);
			return "Modified";
		} catch (IOException e) {
			return "Erreur";
		}
	}

	@PostMapping("/changePersonnalData")
	public String updatePersonnalData(@RequestBody User change, @CurrentUser User current) {
		if (change.getFirstName().equals("") || change.getLastName().equals("") || change.getPhoneNumber().equals(""))
			return "Vide";

		current.setFirstName(change.getFirstName());
		current.setLastName(change.getLastName());
		current.setPhoneNumber(change.getPhoneNumber());
		userRepo.saveAndFlush(current);
		return "Modified";
	}

	@GetMapping("/informations")
	public String getInfo(@CurrentUser User current) {
		return current.getFirstName() + "," + current.getLastName() + "," + current.getEmail() + ","
				+ current.getPhoneNumber() + "," + current.getType();
	}

	@GetMapping("/findAllStudents")
	public List<User> getAllStudents() {
		List<User> users = userRepo.findAllStudents();

		return users;
	}
}