package com.pouletvolant.models;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class UserDateAudit extends DateAudit {
	private static final long serialVersionUID = -7669155772671112938L;

	@CreatedBy
	@Column(updatable = false)
	private long creadedBy;
	
	@LastModifiedBy
	private long updatedBy;
}
