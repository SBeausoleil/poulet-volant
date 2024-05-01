package com.pouletvolant.http.requests;


import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StoreOffer {

	@NotBlank
	private String title;
	@NotBlank
	private String description;
	
	@NotBlank
	private String country;
	
	@NotBlank
	private String postalCode;
	
	@NotBlank
	private String province;
	
	@NotBlank
	private String streetAddress;
	@NotBlank
	private String city;
	
}
