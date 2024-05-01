package com.pouletvolant.http.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginRequest {

	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	private String password;
}
