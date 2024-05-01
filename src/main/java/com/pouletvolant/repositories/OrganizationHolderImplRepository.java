package com.pouletvolant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pouletvolant.models.OrganizationHolderImpl;

@Repository
public interface OrganizationHolderImplRepository extends JpaRepository<OrganizationHolderImpl, Long> {

}
