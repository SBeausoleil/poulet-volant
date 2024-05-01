package com.pouletvolant.models;

public interface OrganizationHolder {

	String getEmail();
	String getPhoneNumber();
	String getName();
	Organization getOrganization();
	
	long getId();
	String getTable();
}
