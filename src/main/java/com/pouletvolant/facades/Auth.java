package com.pouletvolant.facades;

import com.pouletvolant.models.User;

/**
 * Facade to access the authentification service of the PouletVolant website.
 * 
 * @author Samuel Beausoleil
 *
 */
public interface Auth {
	/**
	 * The return value of getUserId() if the client is not logged in.
	 * @see #getUserId()
	 */
	public static final int NOT_LOGGED_IN = -1;
	
	/**
	 * Returns the connected user.
	 * @return an instance of the currently logged user, null if the client is not logged in.
	 */
	public User getUser();
	
	/**
	 * Returns the id of the connected user.
	 * @return the id of the currently logged user, -1 if the client is not logged in.
	 */
	public long getUserId();
	
	/**
	 * Checks if the client is logged in.
	 * @return true if the client is logged in, false if not.
	 */
	public boolean isLoggedIn();
}
