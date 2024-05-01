package com.pouletvolant.models;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "cvfile")
@Getter
@Setter
public class CVFile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String fileName;

	private String fileType;

	@Lob
	private byte[] data;
	
	@OneToOne(cascade= CascadeType.MERGE)
	@JoinColumn(name = "USER_ID")
	@ToString.Exclude
	private User user;

	public CVFile() {
	}

	public CVFile(String fileName, String fileType, byte[] data, User user) {
		this.fileName = fileName;
		this.fileType = fileType;
		this.data = data;
		this.user = user;
	}

}