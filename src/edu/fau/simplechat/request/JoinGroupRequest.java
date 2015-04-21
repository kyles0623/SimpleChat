package edu.fau.simplechat.request;

import java.util.UUID;

/**
 * Represents a request for a user joining a particular group.
 * @author kyle
 *
 */
public class JoinGroupRequest extends ClientRequest {

	/**
	 * Serializable ID
	 */
	private static final long serialVersionUID = -2240677943501237756L;

	/**
	 * ID of group to join
	 */
	private final UUID groupId;

	/**
	 * Create Request to Join a Group
	 * @param userId ID of user joining group
	 * @param groupId ID of group user is joining
	 */
	public JoinGroupRequest(final UUID userId, final UUID groupId) {
		super(userId);
		this.groupId = groupId;
	}

	/**
	 * Retrieve ID of group user wants to join
	 * @return ID of group user wants to join
	 * @precondition none
	 * @postcondition UUID is returned
	 */
	public UUID getGroupId()
	{
		return this.groupId;
	}

}
