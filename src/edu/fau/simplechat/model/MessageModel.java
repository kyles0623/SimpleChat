package edu.fau.simplechat.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Represents an Immutable Message Sent by a user to a group
 * @author kyle
 *
 */
public class MessageModel implements Serializable {

	/**
	 * Serializable ID for class
	 */
	private static final long serialVersionUID = -4476525459273033230L;

	/**
	 * User sending the message
	 */
	private final UserModel sendingUser;

	/**
	 * Group receiving the message
	 */
	private final GroupModel group;

	/**
	 * Time the message was sent
	 */
	private final Date timeSent;

	/**
	 * ID of the message
	 */
	private final UUID messageId;

	/**
	 * Text of the message
	 */
	private final String message;

	/**
	 * Initialize the MessageModel
	 * @param sendingUser User sending the message
	 * @param group Group receiving the message
	 * @param message Message being sent
	 */
	public MessageModel(final UserModel sendingUser, final GroupModel group, final String message) {

		this.sendingUser = sendingUser;
		this.group = group;
		this.message = message;
		timeSent = new Date();

		messageId = UUID.randomUUID();
	}

	/**
	 * Retrieve the User sending the message
	 * @return User sending the message
	 * @precondition none
	 * @postcondition User is returned
	 */
	public UserModel getSendingUser() {
		return sendingUser;
	}

	/**
	 * Retrieve the Group receiving the message
	 * @return Group receiving message
	 * @precondition none
	 * @postcondition Group is returned
	 */
	public GroupModel getGroup() {
		return group;
	}

	/**
	 * Retrieve the time the user sent the message
	 * @return Date object representing sent time
	 * @precondition none
	 * @postcondition Time of sending is returned
	 */
	public Date getTimeSent() {
		return new Date(timeSent.getTime());
	}

	/**
	 * Retrieve the ID of the message
	 * @return ID of the message
	 * @precondition none
	 * @postcondition ID of message is returned
	 */
	public UUID getMessageId() {
		return messageId;
	}

	/**
	 * Retrieve the message text for the message
	 * @return text message of the message
	 * @precondition none
	 * @postcondition Message is returned
	 */
	public String getMessage() {
		return message;
	}

	@Override
	public boolean equals(final Object other)
	{
		if(!(other instanceof MessageModel))
		{
			return false;
		}
		return ((MessageModel)other).getMessageId().equals(this.getMessageId());
	}
}
