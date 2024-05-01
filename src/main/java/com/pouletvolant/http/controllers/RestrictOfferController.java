package com.pouletvolant.http.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pouletvolant.http.requests.RestrictOffer;
import com.pouletvolant.models.Offer;
import com.pouletvolant.models.RestrictedOffer;
import com.pouletvolant.models.Student;
import com.pouletvolant.models.User;
import com.pouletvolant.repositories.OfferRepository;
import com.pouletvolant.repositories.RestrictedOfferRepository;
import com.pouletvolant.repositories.UserRepository;

@RestController
public class RestrictOfferController {

	@Autowired
	private OfferRepository repoOffer;

	@Autowired
	private UserRepository repoUser;

	@Autowired
	private RestrictedOfferRepository repoRestrict;

	@PostMapping(path = "/api/restrictOffer")
	public ResponseEntity<?> store(@RequestBody RestrictOffer request) {
		ResponseEntity<?> responseEntity = new ResponseEntity<>(HttpStatus.OK);
		Optional<Offer> offer = repoOffer.findById(request.getOfferId());
		Optional<User> user = repoUser.findById(request.getStudentId());
		if (!offer.isPresent())
			responseEntity = new ResponseEntity<>(HttpStatus.FORBIDDEN);
		else if (!user.isPresent())
			responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);

		else {
			Student actualUser = (Student) user.get();
			Offer actualOffer = offer.get();
			RestrictedOffer restrictedOffer = new RestrictedOffer();
			restrictedOffer.setOffer(actualOffer);
			restrictedOffer.setStudent(actualUser);
			repoRestrict.save(restrictedOffer);
		}
		return responseEntity;

	}
}
