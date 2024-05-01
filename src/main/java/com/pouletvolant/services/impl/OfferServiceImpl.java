package com.pouletvolant.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pouletvolant.http.requests.UpdateOffer;
import com.pouletvolant.models.Offer;
import com.pouletvolant.models.Student;
import com.pouletvolant.repositories.OfferRepository;
import com.pouletvolant.services.OfferService;

@Transactional
@Service(value = "offerService")
public class OfferServiceImpl implements OfferService {

	@Autowired
	private OfferRepository offerDao;

	@Override
	public List<Offer> findAll() {
		List<Offer> list = new ArrayList<>();
		offerDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(long id) {
		offerDao.deleteById(id);
	}

	@Override
	public Offer findById(long id) {
		Optional<Offer> optionalOffer = offerDao.findById(id);
		return optionalOffer.isPresent() ? optionalOffer.get() : null;
	}

	@Override
	public Offer update(long id, UpdateOffer request) {
		Offer offer = findById(id);
		if (offer != null) {
			BeanUtils.copyProperties(request, offer);
			offer = offerDao.save(offer);
		}
		return offer;
	}

	@Override
	public List<Offer> findAllowedFor(Student student) {
		return offerDao.findAllowedOffers(student.getId());
	}

}