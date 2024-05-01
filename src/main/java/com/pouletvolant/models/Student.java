package com.pouletvolant.models;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue(User.Type.VALUE_STUDENT)
public class Student extends User {
	private static final long serialVersionUID = 2725872907941441354L;

	@JsonIgnore
	@OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
	private List<RestrictedOffer> restrictedOffers;
	@JsonIgnore
	@OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
	private List<Application> applications;
	
	protected Student(String firstName, String lastName, String email, String phoneNumber, String passwordHash) {
		super(User.Type.STUDENT, firstName, lastName, email, phoneNumber, passwordHash);
	}
}
