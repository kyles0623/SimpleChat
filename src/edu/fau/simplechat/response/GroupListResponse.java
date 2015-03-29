package edu.fau.simplechat.response;

import java.util.List;
import java.util.UUID;

import edu.fau.simplechat.model.GroupModel;

public class GroupListResponse extends ServerResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4906349440127141991L;

	
	private final List<GroupModel> groups;
	public GroupListResponse(UUID reqId,List<GroupModel> groups) {
		super(reqId);
		
		this.groups = groups;
		// TODO Auto-generated constructor stub
	}
	
	public List<GroupModel> getGroups()
	{
		return this.groups;
	}

}
