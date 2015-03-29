package edu.fau.simplechat.request;

import java.util.UUID;

public class GroupListRequest extends ClientRequest{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2724790032064539318L;

	public GroupListRequest(UUID userId) {
		super(userId);
	}

}
