package com.pouletvolant.facades;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.pouletvolant.models.User;

/**
 * Implementation of the Auth facade for the PouletVolant website.
 * 
 * @author Samuel Beausoleil
 *
 */
@Component
public class AuthImplementation implements Auth {

	@Override
	public User getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (isLoggedIn(authentication))
			return (User) authentication.getPrincipal();
		return null;
	}

	@Override
	public long getUserId() {
		User user = getUser();
		if (user != null)
			return user.getId();
		return NOT_LOGGED_IN;
	}

	@Override
	public boolean isLoggedIn() {
		return isLoggedIn(SecurityContextHolder.getContext().getAuthentication());
	}
	
	private boolean isLoggedIn(Authentication authentication) {
		return authentication != null && authentication.isAuthenticated();
	}

}
