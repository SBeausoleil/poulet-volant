package com.pouletvolant.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "offers")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id")
    @ToString.Exclude
    private Organization organization;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    @ToString.Exclude
    private Address address;

    @OneToMany(mappedBy = "offer")
    @JsonIgnore
    private List<RestrictedOffer> restrictedStudents;
    @lombok.ToString.Exclude
    @OneToMany(mappedBy = "offer")
    @JsonIgnore
    private List<Application> applications;
    
	public Offer(String title, String description, Organization organization, Address address) {
		this.title = title;
		this.description = description;
		this.organization = organization;
		this.address = address;
	}
     
    
}
