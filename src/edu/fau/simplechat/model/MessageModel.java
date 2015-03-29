package edu.fau.simplechat.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class MessageModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4476525459273033230L;

	private final UserModel sendingUser;
	
	private final GroupModel group;
	
	private final Date timeSent;
	
	private final UUID messageId;
	
	private final String message;

	public MessageModel(UserModel sendingUser, GroupModel group, String message) {
		
		this.sendingUser = sendingUser;
		this.group = group;
		this.message = message;
		
		timeSent = new Date();
		
		messageId = UUID.randomUUID();
	}

	public UserModel getSendingUser() {
		return sendingUser;
	}

	public GroupModel getGroup() {
		return group;
	}

	public Date getTimeSent() {
		return timeSent;
	}

	public UUID getMessageId() {
		return messageId;
	}

	public String getMessage() {
		return message;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if(!(other instanceof MessageModel))
		{
			return false;
		}
		return ((MessageModel)other).getMessageId().equals(this.getMessageId());
	}
}
