package com.pouletvolant.http.requests;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreNotification {
	
	@NotBlank
	private String destinataire;
	
	@NotBlank
	private String texte;
}
