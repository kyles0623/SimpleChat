package edu.fau.simplechat.request;

import java.util.UUID;

/**
 * Represents a request for retrieving the list of current active groups
 * @author kyle
 *
 */
public class GroupListRequest extends ClientRequest{

	/**
	 * Serializable ID
	 */
	private static final long serialVersionUID = -2724790032064539318L;

	/**
	 * Initialize the Request
	 * @param userId
	 */
	public GroupListRequest(final UUID userId) {
		super(userId);
	}

}
