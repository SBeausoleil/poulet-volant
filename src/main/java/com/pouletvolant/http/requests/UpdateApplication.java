package com.pouletvolant.http.requests;

import javax.validation.constraints.NotBlank;

import com.pouletvolant.models.Application;

import lombok.Data;

@Data
public class UpdateApplication {

	@NotBlank
	private Application.Status status;

}
