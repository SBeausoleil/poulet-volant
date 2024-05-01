package com.pouletvolant.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.MetaValue;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString.Exclude;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "organizations")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Organization {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String description;

	@Any(metaColumn = @Column(name = "holder_type"))
	@AnyMetaDef(idType = "long", metaType = "string", metaValues = {
			@MetaValue(targetEntity = Employer.class, value = User.TABLE),
			@MetaValue(targetEntity = OrganizationHolderImpl.class, value = OrganizationHolderImpl.TABLE) })
	@Cascade({ CascadeType.DELETE })
	@JoinColumn(name = "holder_id")
	private OrganizationHolder holder;

	@Exclude
	@lombok.EqualsAndHashCode.Exclude
	@OneToMany(mappedBy = "organization")
	@JsonIgnore
	private List<Offer> offers;

	public Organization(String name, String description, OrganizationHolder holder) {
		this.name = name;
		this.description = description;
		this.holder = holder;
	}

	@Override
	public String toString() {
		return "Organization [id=" + id + ", name=" + name + ", description=" + description + ", holder=" + holder
				+ ", offers=" + offers + "]";
	}
	
	

}
