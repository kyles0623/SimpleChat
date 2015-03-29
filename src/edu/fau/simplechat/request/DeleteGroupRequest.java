package edu.fau.simplechat.request;

import java.util.UUID;

import edu.fau.simplechat.model.GroupModel;

public class DeleteGroupRequest extends ClientRequest{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6310493054832780270L;

	private final GroupModel group;

	public DeleteGroupRequest(final UUID userId, final GroupModel name) {
		super(userId);
		this.group = name;
		// TODO Auto-generated constructor stub
	}

	public GroupModel getGroup()
	{
		return group;
	}

}
