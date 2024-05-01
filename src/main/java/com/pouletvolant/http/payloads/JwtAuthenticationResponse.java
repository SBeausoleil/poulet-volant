package com.pouletvolant.http.payloads;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
	public static final String BEARER_TYPE = "Bearer";

	private String accessToken;
	private String tokenType = BEARER_TYPE;

	public JwtAuthenticationResponse(String accessToken) {
		this.accessToken = accessToken;
	}

}
