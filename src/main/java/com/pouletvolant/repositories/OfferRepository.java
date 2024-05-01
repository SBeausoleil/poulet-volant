package com.pouletvolant.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pouletvolant.models.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

	@Query(value = "SELECT offers.* FROM offers WHERE offers.id NOT IN (SELECT offer_id FROM restricted_offers WHERE student_id = ?)", nativeQuery = true)
	List<Offer> findAllowedOffers(long studentId);
}
