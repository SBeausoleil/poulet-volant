package com.pouletvolant.http.requests;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class StoreOfferIntern extends StoreOffer {
	
	private long idOrganization;

	
	
}
