package edu.fau.simplechat.request;

import java.util.UUID;

public class CreateGroupRequest extends ClientRequest{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6310493054832780270L;

	private final String groupName;
	
	public CreateGroupRequest(UUID userId, String name) {
		super(userId);
		this.groupName = name;
		// TODO Auto-generated constructor stub
	}
	
	public String getGroupName()
	{
		return groupName;
	}

}
