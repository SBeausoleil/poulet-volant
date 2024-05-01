package com.pouletvolant.models;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue(User.Type.VALUE_INTERNSHIP_MANAGER)
public class InternshipManager extends User {
	private static final long serialVersionUID = 238902230780052450L;

	protected InternshipManager(String firstName, String lastName, String email, String phoneNumber,
			String passwordHash) {
		super(User.Type.INTERNSHIP_MANAGER, firstName, lastName, email, phoneNumber, passwordHash);
	}

}
