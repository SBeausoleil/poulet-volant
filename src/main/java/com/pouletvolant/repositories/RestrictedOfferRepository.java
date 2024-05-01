package com.pouletvolant.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.pouletvolant.models.RestrictedOffer;

public interface RestrictedOfferRepository extends JpaRepository<RestrictedOffer, Long> {
  @Query(value="select * from offers o where o.id not in (select offer_id from restricted_offers where student_id = ? )",nativeQuery = true)
  public List<Object[]> getAllowedOffers(Long id);
}
