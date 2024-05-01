package com.pouletvolant.http.controllers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pouletvolant.http.payloads.ApiResponse;
import com.pouletvolant.http.requests.RestrictOffer;
import com.pouletvolant.http.requests.StoreOffer;
import com.pouletvolant.http.requests.StoreOfferIntern;
import com.pouletvolant.http.requests.UpdateOffer;
import com.pouletvolant.models.Address;
import com.pouletvolant.models.Employer;
import com.pouletvolant.models.Offer;
import com.pouletvolant.models.Organization;
import com.pouletvolant.models.Student;
import com.pouletvolant.models.User;
import com.pouletvolant.repositories.OfferRepository;
import com.pouletvolant.repositories.OrganizationRepository;
import com.pouletvolant.security.CurrentUser;
import com.pouletvolant.services.AddressService;
import com.pouletvolant.services.OfferService;

@RestController
@RequestMapping("/offer")
public class OfferController {

	private static final Logger logger = LoggerFactory.getLogger(OfferController.class);

	@Autowired
	private OrganizationRepository orgRepo;

	@Autowired
	private OfferService offerService;

	@Autowired
	private OfferRepository offerRepository;

	@Autowired
	private AddressService addressService;

	@PostMapping("/store")
	public ApiResponse<Offer> store(@CurrentUser User currentUser, @RequestBody StoreOffer request) {
		logger.info("store(): " + request);

		Address address = new Address(request.getStreetAddress(), request.getCity(), request.getProvince(),
				request.getCountry(), request.getPostalCode());
		address = addressService.saveIfNotExists(address);

		Offer offer = new Offer(request.getTitle(), request.getDescription(),
				((Employer) currentUser).getOrganization(), address);
		return new ApiResponse<>(HttpStatus.OK.value(), "Offer saved successfully.", offerRepository.save(offer));
	}

	@PostMapping("/store/internship-manager")
	public ResponseEntity<Void> store_internshipManager(@CurrentUser User currentUser,
			@RequestBody StoreOfferIntern request) {
		Address address = new Address(request.getStreetAddress(), request.getCity(), request.getProvince(),
				request.getCountry(), request.getPostalCode());
		address = addressService.saveIfNotExists(address);
		Organization org = orgRepo.findById(request.getIdOrganization()).get();
		Offer offer = new Offer(request.getTitle(), request.getDescription(), org, address);
		offerRepository.save(offer);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/offers")
	public List<Offer> listOffer() {
		logger.info("listOffers()");
		return offerService.findAll();
	}

	@GetMapping("/offers/mine")
	public List<Offer> employerOffers(@CurrentUser Employer employer) {
		logger.info("employerOffers()");
		return employer.getOrganization().getOffers();
	}

	@GetMapping("/offers/restricted")
	public List<Offer> restrictedListOffers(@CurrentUser Student student) {
		logger.info("restrictedListOffers()");
		return offerService.findAllowedFor(student);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Offer> get(@PathVariable long id) {
		logger.info("get(): " + id);
		Offer offer = offerService.findById(id);
		if (offer != null)
			Hibernate.initialize(offer);
		return offer != null ? ResponseEntity.ok(offer) : ResponseEntity.notFound().build();
	}

	@PutMapping("/update/{id}")
	public ApiResponse<Offer> update(@PathVariable long id, @RequestBody UpdateOffer request) {
		logger.info("update(): " + id + ", " + request);
		return new ApiResponse<>(HttpStatus.OK.value(), "", offerService.update(id, request));
	}

	@DeleteMapping("/{id}")
	public ApiResponse<Void> delete(@PathVariable long id) {
		logger.info("delete(): " + id);
		offerService.delete(id);
		return new ApiResponse<>(HttpStatus.OK.value(), "", null);
	}

	@GetMapping(path = "/offers/{studentId}/allowedOffers")
	public ResponseEntity<List<Offer>> getStudentAllowedOffers(@PathVariable long studentId) {
		return ResponseEntity.ok(offerRepository.findAllowedOffers(studentId));
	}

}
