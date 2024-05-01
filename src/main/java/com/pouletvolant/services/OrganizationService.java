package com.pouletvolant.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pouletvolant.models.Organization;
import com.pouletvolant.models.OrganizationHolder;
import com.pouletvolant.repositories.OrganizationRepository;

@Service
public class OrganizationService {

	@Autowired
	private OrganizationRepository repo;
	
	public Organization getOrganizationBelongingTo(OrganizationHolder holder) {
		return repo.findByHolder(holder.getId(), holder.getTable());
	}
}
