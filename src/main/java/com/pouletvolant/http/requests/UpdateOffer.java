package com.pouletvolant.http.requests;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class UpdateOffer {

	@NotEmpty
	private String title;
	@NotEmpty
	private String description;
}
