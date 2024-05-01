package com.pouletvolant.http.requests;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RestrictOffer {
	@NotBlank
	private Long offerId;
	@NotBlank
	private Long studentId;	
}
