package com.pouletvolant.http.payloads;

import java.time.Instant;

import com.pouletvolant.models.Application;

import lombok.Data;

@Data
public class SimplifiedApplication {

	private Application.Status status;
	
	private long id;
	private String offerTitle;
	private String studentName;
	
	private Instant createdAt;
	private Instant updatedAt;
	
	public SimplifiedApplication(Application app) {
		id = app.getId();
		status = app.getStatus();
		offerTitle = app.getOffer().getTitle();
		studentName = app.getStudent().getName();
		createdAt = app.getCreatedAt();
		updatedAt = app.getUpdatedAt();
	}
}
