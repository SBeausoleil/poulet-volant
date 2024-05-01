package com.pouletvolant.models;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "applications", indexes = @Index(columnList = "offer_id,student_id", unique = true))
public class Application extends DateAudit {
	private static final long serialVersionUID = -2721990956875546425L;
	
	public static enum Status {
		APPLIED, CONVOKED, ACCEPTED, DECLINED;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Enumerated
	private Status status;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "offer_id")
	private Offer offer;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "student_id")
	private Student student;
	
	public Application(Offer offer, Student student) {
		this.offer = offer;
		this.student = student;
		status = Status.APPLIED;
	}
}
