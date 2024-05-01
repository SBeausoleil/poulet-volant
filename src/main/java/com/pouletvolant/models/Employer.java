package com.pouletvolant.models;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
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
@DiscriminatorValue(User.Type.VALUE_EMPLOYER)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Employer extends User implements OrganizationHolder {
	private static final long serialVersionUID = -7104893326848041907L;

	@Transient
	@JsonIgnore
	private Organization organization;
	
	@JsonIgnore
	@Transient
	@OneToMany(mappedBy = "employeur", fetch = FetchType.EAGER)
	private List<Formulaire> formulaires;
	
	protected Employer(String firstName, String lastName, String email, String phoneNumber, String passwordHash) {
		super(User.Type.EMPLOYER, firstName, lastName, email, phoneNumber, passwordHash);
	}

	@Override
	public Organization getOrganization() {
		if (organization == null) {
			organization = BeanUtil.getBean(OrganizationService.class).getOrganizationBelongingTo(this);
		}
		return organization;
	}

	@Override
	public String getTable() {
		return User.TABLE;
	}
}
