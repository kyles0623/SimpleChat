package edu.fau.simplechat.response;

import java.util.UUID;

import edu.fau.simplechat.model.GroupModel;

public class JoinGroupResponse extends ServerResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2213891791281798290L;

	private GroupModel group;
	
	private String response;
	
	public static final String RESPONSE_SUCCESS = "RESPONSE_SUCCESS",
			RESPONSE_FAILURE = "RESPONSE_FAILURE";
	
	public JoinGroupResponse(UUID reqId,GroupModel group, String response) {
		super(reqId);
		this.group = group;
		this.response = response;
	}
	
	public GroupModel getGroup()
	{
		return group;
	}

	public String getResponse() {

		return response;
	}

}
