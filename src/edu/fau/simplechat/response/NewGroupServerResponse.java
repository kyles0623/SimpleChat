package edu.fau.simplechat.response;

import java.util.UUID;

import edu.fau.simplechat.model.GroupModel;

public class NewGroupServerResponse extends ServerResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5187476613063615595L;
	
	private final GroupModel newGroup;
	
	public NewGroupServerResponse(UUID reqId, GroupModel newGroup) {
		super(reqId);
		this.newGroup = newGroup; 
	}
	
	public GroupModel getGroupModel()
	{
		return newGroup;
	}

}
