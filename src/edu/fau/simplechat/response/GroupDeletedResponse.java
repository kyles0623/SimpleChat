package edu.fau.simplechat.response;

import edu.fau.simplechat.model.GroupModel;

public class GroupDeletedResponse extends ServerResponse {

	/**
	 * Serializable serial
	 */
	private static final long serialVersionUID = -4348815905277730120L;

	private final GroupModel group;

	public GroupDeletedResponse(final GroupModel group) {
		super(null);
		this.group = group;
		// TODO Auto-generated constructor stub
	}

	public GroupModel getGroup()
	{
		return group;
	}

}
