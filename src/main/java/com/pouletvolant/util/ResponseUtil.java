package com.pouletvolant.util;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pouletvolant.models.User;

@Service
public class ResponseUtil {

	public static final String USER_UPDATE_HEADER = "user_update";
	
	/**
	 * Insert the instruction for the REACT App to update it's session stored user with the given values.
	 * @param user
	 * @param ?headers
	 * @return an HttpHeaders containing the update instruction and data.
	 */
	public HttpHeaders insertUserUpdateInstruction(User user, HttpHeaders headers) {
		if (headers == null)
			headers = new HttpHeaders();
		try {
			headers.add(USER_UPDATE_HEADER, new ObjectMapper().writeValueAsString(user));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		return headers;
	}
}
