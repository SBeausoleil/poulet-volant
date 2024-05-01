package com.pouletvolant.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.EqualsAndHashCode.Exclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String streetAddress;
	private String city;
	private String province;
	private String country;
	private String postalCode;
	@Exclude
	@lombok.ToString.Exclude
	@OneToMany(mappedBy = "address")
	@JsonIgnore
	private List<Offer> offers;

	public Address(String streetAddress, String city, String province, String country, String postalCode) {
		this.streetAddress = streetAddress;
		this.city = city;
		this.province = province;
		this.country = country;
		this.postalCode = postalCode;
	}
	
	
}
