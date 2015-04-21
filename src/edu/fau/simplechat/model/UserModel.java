package edu.fau.simplechat.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * Immutable Model representing the information for a User.
 * @author kyle
 *
 */
public class UserModel implements Serializable {

	/**
	 * Serializable ID
	 */
	private static final long serialVersionUID = 7482345994250453402L;

	/**
	 * ID of the user
	 */
	private final UUID id;

	/**
	 * Username of the user
	 */
	private final String username;

	/**
	 * Initializes UserModel with given parameters
	 * @param id ID of the user
	 * @param username Username of the user
	 */
	public UserModel(final UUID id, final String username) {
		this.id = id;
		this.username = username;
	}

	/**
	 * Retrieve ID of the user
	 * @return ID of the User
	 * @precondition none
	 * @postcondition ID is returned
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * Retrieve username of the User
	 * @return Username of the user
	 * @precondition none
	 * @postcondition Username is returned
	 */
	public String getUsername() {
		return username;
	}

	@Override
	public boolean equals(final Object other)
	{
		if(!(other instanceof UserModel))
		{
			return false;
		}
		return ((UserModel)other).getId().equals(this.getId());
	}


}
