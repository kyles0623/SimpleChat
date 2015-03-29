package edu.fau.simplechat.request.handler;

import java.util.UUID;

import edu.fau.simplechat.logger.Logger;
import edu.fau.simplechat.request.ClientRequest;
import edu.fau.simplechat.request.LeaveGroupRequest;
import edu.fau.simplechat.response.JoinGroupResponse;
import edu.fau.simplechat.response.LeaveGroupResponse;
import edu.fau.simplechat.server.ChatGroup;
import edu.fau.simplechat.server.ChatManager;
import edu.fau.simplechat.server.UserConnection;

public class LeaveGroupRequestHandler extends RequestHandler {

	
	public LeaveGroupRequestHandler(ClientRequest c, UserConnection user,
			ChatManager cM) {
		super(c, user, cM);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle() {
		LeaveGroupRequest joinGroupRequest = (LeaveGroupRequest)clientRequest;

		UUID groupId = joinGroupRequest.getGroupId();
		
		if(chatManager.removeUserFromGroup(userConnection, groupId))
		{
			 
			ChatGroup group = chatManager.getGroupById(groupId);
			
			LeaveGroupResponse response = new LeaveGroupResponse(joinGroupRequest.getRequestId(),
					group.getGroupModel(),
					JoinGroupResponse.RESPONSE_SUCCESS);
			userConnection.sendResponse(response);
		}
		else
		{
			Logger.getInstance().write("Failure to remove user to group.");
		}
		
		
		
	}
	

}
