package com.pouletvolant.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pouletvolant.models.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
	
	Optional<Application> findByStudentIdAndOfferId(long studentId, long offerId);
	
	List<Application> findByOfferId(long offerId);
}
