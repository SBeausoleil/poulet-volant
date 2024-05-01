package com.pouletvolant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pouletvolant.models.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

	@Query(
			value = "SELECT * FROM organizations WHERE holder_id = :holderId AND holder_type = :holderTable",
			nativeQuery = true
			)
	Organization findByHolder(long holderId, String holderTable);
	
}
