package com.pouletvolant.http.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pouletvolant.http.requests.StoreOrganization;
import com.pouletvolant.http.requests.StoreOrganizationHolder;
import com.pouletvolant.models.Employer;
import com.pouletvolant.models.Organization;
import com.pouletvolant.models.OrganizationHolderImpl;
import com.pouletvolant.models.User;
import com.pouletvolant.repositories.MandatoryRedirectRepository;
import com.pouletvolant.repositories.OrganizationHolderImplRepository;
import com.pouletvolant.repositories.OrganizationRepository;
import com.pouletvolant.security.CurrentUser;
import com.pouletvolant.util.ResponseUtil;

@RestController
@RequestMapping("/api/organization")
public class OrganizationController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private OrganizationRepository orgRepo;
	
	@Autowired
	private OrganizationHolderImplRepository orgHolderRepo;
	
	@Autowired
	private MandatoryRedirectRepository redirectRepo;
	
	@Autowired
	private ResponseUtil responseUtil;

	@GetMapping("/has-one")
	public ResponseEntity<Boolean> hasOne(@CurrentUser User user) {
		logger.info("hasOne(): " + user);
		Employer employer = (Employer) user;
		return new ResponseEntity<>(employer.getOrganization() != null, HttpStatus.OK);
	}
	
	@GetMapping("/getAll")
	public List<Organization> getAll() {
		return orgRepo.findAll();
	}
	
	@Transactional
	@PostMapping("/storeImpl")
	public ResponseEntity<Void> storeImpl(@CurrentUser User user, @RequestBody StoreOrganizationHolder request) {
		OrganizationHolderImpl organizationHolderImpl = new OrganizationHolderImpl(request.getEmailPersonne(), request.getPhoneNumberPersonne(), request.getNamePersonne());
		Organization org = new Organization(request.getNameOrganization(), request.getDescriptionOrganization(), organizationHolderImpl);
		orgHolderRepo.saveAndFlush(organizationHolderImpl);
		orgRepo.saveAndFlush(org);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@Transactional
	@PostMapping("/store")
	public ResponseEntity<Void> store(@CurrentUser User user, @Valid @RequestBody StoreOrganization request) {
		logger.info("store(): " + user + " : " + request);
		Organization org = new Organization(request.getName(), request.getDescription(), (Employer) user);
		try {
			orgRepo.save(org);
			user.setMandatoryRedirects(user.getMandatoryRedirects().stream().filter((redirect) -> {
				if (redirect.getUrl().equals("/organization/create")) {
					redirectRepo.delete(redirect);
					return false;
				}
				return true;
			}).collect(Collectors.toList()));
			return new ResponseEntity<Void>(responseUtil.insertUserUpdateInstruction(user, null), HttpStatus.CREATED);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
