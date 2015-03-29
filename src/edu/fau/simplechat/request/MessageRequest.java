package edu.fau.simplechat.request;

import java.util.UUID;


public class MessageRequest extends ClientRequest {

	private final UUID groupId;	
	private final String message;
	
	public MessageRequest(UUID userId, UUID groupId, String message) {
		super(userId);
		this.groupId = groupId;
		this.message = message;
		// TODO Auto-generated constructor stub
	}
	
	public UUID getGroupId()
	{
		return groupId;
	}
	
	public String getMessage()
	{
		return message;
	}

}
