package edu.fau.simplechat.request;

import java.util.UUID;

/**
 * Represents a request to Create a New Group
 * @author kyle
 *
 */
public class CreateGroupRequest extends ClientRequest{

	/**
	 * Serializable ID
	 */
	private static final long serialVersionUID = 6310493054832780270L;


	/**
	 * Name of the group being created
	 */
	private final String groupName;

	/**
	 * Initialize Create Group Request
	 * @param userId ID of the user sending the request
	 * @param name Name of the new group
	 */
	public CreateGroupRequest(final UUID userId, final String name) {
		super(userId);
		this.groupName = name;
	}

	/**
	 * Retrieve name of group being created
	 * @return Name of group being created
	 * @precondition none
	 * @postcondition Group Name returned
	 */
	public String getGroupName()
	{
		return groupName;
	}

}
