package com.pouletvolant.models;

import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pouletvolant.models.User.Type;
import com.pouletvolant.services.HashingService;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class UserFactory {

	@Autowired
	private HashingService hashingService;

	@SuppressWarnings("unchecked")
	public <T extends User> T newUser(Type type, String firstName, String lastName, @Email String email,
			String phoneNumber, String password) {
		String passwordHash = hashingService.encode(password);
		switch (type) {
		case STUDENT:
			return (T) new Student(firstName, lastName, email, phoneNumber, passwordHash);
		case EMPLOYER:
			return (T) new Employer(firstName, lastName, email, phoneNumber, passwordHash);
		case INTERNSHIP_MANAGER:
			return (T) new InternshipManager(firstName, lastName, email, phoneNumber, passwordHash);
		default:
			throw new IllegalArgumentException("The User.Type type " + type + " is not recognized.");
		}
	}
}
