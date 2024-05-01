package com.pouletvolant.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.pouletvolant.models.Address;
import com.pouletvolant.repositories.AddressRepository;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepo;

	public Example<Address> example(Address address) {
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withIgnorePaths("id");
		return Example.of(address, matcher);

	}

	public boolean exists(Address address) {
		return addressRepo.exists(example(address));
	}

	public Address saveIfNotExists(Address address) {
		if (!exists(address))
			return addressRepo.save(address);
		return addressRepo.findOne(example(address)).get();
	}
}
