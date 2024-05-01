package com.pouletvolant.http.requests;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StoreOrganizationHolder {
	
	
	@NotBlank
	private String emailPersonne;
	@NotBlank
	private String phoneNumberPersonne;
	@NotBlank
	private String namePersonne;
	
	@NotBlank
	private String nameOrganization;
	@NotBlank
	private String descriptionOrganization;

}
