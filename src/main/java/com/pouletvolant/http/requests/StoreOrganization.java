package com.pouletvolant.http.requests;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class StoreOrganization {

	@NotBlank
	private String name;
	@NotBlank
	private String description;
}
