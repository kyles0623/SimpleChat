package edu.fau.simplechat.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Immutable Model representing information for a chat group.
 * @author kyle
 *
 */
public class GroupModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2227386005151203435L;

	/**
	 * ID of the chat group
	 */
	private final UUID id;

	/**
	 * name of the chat group
	 */
	private final String groupName;

	private final List<UserModel> moderators;


	/**
	 * Creates Chat Model representing basic information of a chat room.
	 * @param groupName Name of the chat group
	 * @param id ID Of the chat room
	 */
	public GroupModel(final String groupName, final UUID id, final List<UserModel> mods)
	{
		this.groupName = groupName;
		this.id = id;
		this.moderators = new ArrayList<UserModel>(mods);
	}

	/**
	 * Return ID of chat room
	 * @return ID
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * Returns name of chat room
	 * @return name of chat room
	 */
	public String getGroupName() {
		return groupName;

	}

	/**
	 * Returns the moderators for the group.
	 * @return List of moderators
	 * @precondition none
	 * @postcondition List of users will be returned
	 */
	public List<UserModel> getModerators()
	{

		return new ArrayList<UserModel>(moderators);

	}

	/**
	 * Check if a user is a moderator of this group.
	 * @param user User to check if is moderator
	 * @return true if user is moderator, false otherwise
	 * @precondition user is not null
	 * @postcondition none
	 */
	public boolean isModerator(final UserModel user)
	{
		System.out.println(user.toString()+" , "+moderators.toString());
		return moderators.contains(user);
	}

	@Override
	public boolean equals(final Object other)
	{
		if(!(other instanceof GroupModel))
		{
			return false;
		}
		return ((GroupModel)other).getId().equals(this.getId());
	}
}
