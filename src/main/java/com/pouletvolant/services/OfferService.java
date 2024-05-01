package com.pouletvolant.services;

import java.util.List;

import com.pouletvolant.http.requests.UpdateOffer;
import com.pouletvolant.models.Offer;
import com.pouletvolant.models.Student;


public interface OfferService {

	List<Offer> findAll();

	void delete(long id);

	Offer findById(long id);

	Offer update(long id, UpdateOffer request);
	
	List<Offer> findAllowedFor(Student student);
}
