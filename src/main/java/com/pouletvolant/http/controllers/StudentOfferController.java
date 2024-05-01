package com.pouletvolant.http.controllers;

//import static com.pouletvolant.repositories.OfferRepository.getSqlStudentOffers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pouletvolant.facades.Auth;
import com.pouletvolant.models.Offer;
import com.pouletvolant.repositories.OfferRepository;

// TODO move the content of this controller to OfferController
@RestController
@Deprecated
public class StudentOfferController {

	@Autowired
	private OfferRepository offerRepo;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	private Auth auth;

	@Deprecated
	@PostMapping(path = "/api/studentOffers")
	public List<Offer> store(String payload) throws Exception {
		List<Offer> offers = new ArrayList<>();

		// List<Map<String, Object>> allowedOffers = getSqlStudentOffers(); // CHANGEME
		List<Map<String, Object>> allowedOffers = null; // CHANGEME

		for (Map<String, Object> map : allowedOffers) {
			offers.add(offerRepo.getOne(Long.parseLong(map.get("offer_id").toString())));
		}
		return offers;
	}
}
