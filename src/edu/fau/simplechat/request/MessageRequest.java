package edu.fau.simplechat.request;

import java.util.UUID;


/**
 * Represents a Request of a user sending a message to a particular group.
 * @author kyle
 *
 */
public class MessageRequest extends ClientRequest {

	/**
	 * Serializable ID
	 */
	private static final long serialVersionUID = -1231766675271450595L;

	/**
	 * ID of group user wants to send message to
	 */
	private final UUID groupId;

	/**
	 * Text of message user is sending
	 */
	private final String message;

	/**
	 * Create a MessageRequest to send a message to a group
	 * @param userId ID of user sending message
	 * @param groupId ID of group user is sending message to
	 * @param message Text of message user is sending
	 */
	public MessageRequest(final UUID userId, final UUID groupId, final String message) {
		super(userId);
		this.groupId = groupId;
		this.message = message;
	}

	/**
	 * Retrieve the ID of the group the
	 * user is sending the message to
	 * @return ID of group the user is sending the message to
	 * @precondition none
	 * @postcondition UUID is returned
	 */
	public UUID getGroupId()
	{
		return groupId;
	}

	/**
	 * Retrieve text of message user is sending
	 * @return Text of message user is sending
	 * @precondition none
	 * @postcondition String is returned
	 */
	public String getMessage()
	{
		return message;
	}

}
