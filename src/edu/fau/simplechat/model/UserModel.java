package edu.fau.simplechat.model;

import java.io.Serializable;
import java.util.UUID;

public class UserModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7482345994250453402L;

	private final UUID id;

	private final String username;

	public UUID getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public UserModel(final UUID id, final String username) {
		this.id = id;
		this.username = username;
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
