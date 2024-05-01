package com.pouletvolant.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pouletvolant.services.OrganizationService;
import com.pouletvolant.util.BeanUtil;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = OrganizationHolderImpl.TABLE)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrganizationHolderImpl implements OrganizationHolder {
	public static final String TABLE = "organization_holders";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String email;
	private String phoneNumber;
	private String name;

	@lombok.ToString.Exclude
	@Transient
	@JsonIgnore
	private Organization organization;

	public OrganizationHolderImpl(String email, String phoneNumber, String name) {
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.name = name;
	}

	@Override
	public String getTable() {
		return TABLE;
	}

	@JsonIgnore
	@Override
	public Organization getOrganization() {
		if (organization == null) {
			organization = BeanUtil.getBean(OrganizationService.class).getOrganizationBelongingTo(this);
		}
		return organization;
	}

	@Override
	public String toString() {
		return "OrganizationHolderImpl [id=" + id + ", email=" + email + ", phoneNumber=" + phoneNumber + ", name="
				+ name + "]";
	}
	
	
}
