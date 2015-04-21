package edu.fau.simplechat.request;

import java.util.UUID;
/**
 * Represents a request for a user leaving a particular group
 * @author kyle
 *
 */
public class LeaveGroupRequest extends ClientRequest {

	/**
	 * Serializable ID
	 */
	private static final long serialVersionUID = -2240677943501237756L;

	/**
	 * ID of group user is leaving
	 */
	private final UUID groupId;

	/**
	 * Create Request to Join a Group
	 * @param userId ID of user joining group
	 * @param groupId ID of group user is joining
	 */
	public LeaveGroupRequest(final UUID userId, final UUID groupId) {
		super(userId);
		this.groupId = groupId;
	}

	/**
	 * Retrieve ID of group user wants to leave
	 * @return ID of group user wants to leave
	 * @precondition none
	 * @postcondition UUID is returned
	 */
	public UUID getGroupId()
	{
		return this.groupId;
	}

}
