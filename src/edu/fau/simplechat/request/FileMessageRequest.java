package edu.fau.simplechat.request;

import java.util.UUID;

import edu.fau.simplechat.model.FileMessageModel;


/**
 * Represents a Request of a user sending a file message to a particular group.
 * @author kyle
 *
 */
public class FileMessageRequest extends ClientRequest {

	/**
	 *  Serializable ID
	 */
	private static final long serialVersionUID = 2365180437838644236L;

	/**
	 * File message model containing information about the message
	 */
	private final FileMessageModel fileMessage;

	/**
	 * Initialize the File Message Request
	 * @param userId ID of user sending request
	 * @param message File Message being sent
	 */
	public FileMessageRequest(final UUID userId,  final FileMessageModel message) {
		super(userId);
		fileMessage = message;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Retrieve the file message attached to request
	 * @return File Message being sent
	 * @precondition none
	 * @postcondition none
	 */
	public FileMessageModel getFileMessage()
	{
		return fileMessage;
	}
}
