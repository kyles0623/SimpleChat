package edu.fau.simplechat.response;

import edu.fau.simplechat.model.GroupModel;
import edu.fau.simplechat.model.UserModel;

public class UserLeftGroupResponse extends ServerResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3171695668829549973L;

	private GroupModel group;
	
	private UserModel user;
	
	public UserLeftGroupResponse(final GroupModel group, final UserModel user) {
		super(null);

		this.group = group;
		this.user = user;
	}
	
	public UserModel getUser()
	{
		return user;
	}
	
	public GroupModel getGroup()
	{
		return group;
	}

}
