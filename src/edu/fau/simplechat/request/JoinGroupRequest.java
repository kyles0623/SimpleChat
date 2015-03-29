package edu.fau.simplechat.request;

import java.util.UUID;

public class JoinGroupRequest extends ClientRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2240677943501237756L;
	
	private final UUID groupId;
	
	/**
	 * Create Request to Join a Group
	 * @param userId ID of user joining group
	 * @param groupId ID of group user is joining
	 */
	public JoinGroupRequest(UUID userId, UUID groupId) {
		super(userId);
		this.groupId = groupId; 
		// TODO Auto-generated constructor stub
	}
	
	public UUID getGroupId()
	{
		return this.groupId;
	}

}
