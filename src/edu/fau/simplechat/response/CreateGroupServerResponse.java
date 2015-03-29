package edu.fau.simplechat.response;

import java.util.UUID;

import edu.fau.simplechat.model.GroupModel;
import edu.fau.simplechat.model.UserModel;

public class CreateGroupServerResponse extends ServerResponse{

/**
	 * 
	 */
	private static final long serialVersionUID = 5105768078071283677L;

	public static final String RESPONSE_SUCCESS = "RESPONSE_SUCCESS";
	
	public static final String RESPONSE_FAILED = "RESPONSE_FAILED";
	
	public static final String RESPONSE_GROUP_EXISTS = "RESPONSE_GROUP_EXISTS";
	
	private final String response;
	
	private final GroupModel group;
	
	public CreateGroupServerResponse(UUID reqId,String response,GroupModel group) {
		super(reqId);
		this.response = response;
		this.group = group;
	}
	
	public String getResponse()
	{
		return this.response;
	}
	
	public GroupModel getGroupModel()
	{
		return group;
	}
}
