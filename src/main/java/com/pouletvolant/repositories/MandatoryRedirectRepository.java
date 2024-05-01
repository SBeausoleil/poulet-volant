package com.pouletvolant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pouletvolant.models.MandatoryRedirect;

@Repository
public interface MandatoryRedirectRepository extends JpaRepository<MandatoryRedirect, Long> {

	
}
