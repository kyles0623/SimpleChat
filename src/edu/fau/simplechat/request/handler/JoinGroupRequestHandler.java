package edu.fau.simplechat.request.handler;

import java.util.UUID;

import edu.fau.simplechat.logger.Logger;
import edu.fau.simplechat.request.ClientRequest;
import edu.fau.simplechat.request.JoinGroupRequest;
import edu.fau.simplechat.response.JoinGroupResponse;
import edu.fau.simplechat.server.ChatGroup;
import edu.fau.simplechat.server.ChatManager;
import edu.fau.simplechat.server.UserConnection;

public class JoinGroupRequestHandler extends RequestHandler {

	
	public JoinGroupRequestHandler(ClientRequest c, UserConnection user,
			ChatManager cM) {
		super(c, user, cM);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle() {
		JoinGroupRequest joinGroupRequest = (JoinGroupRequest)clientRequest;

		UUID groupId = joinGroupRequest.getGroupId();
		
		if(chatManager.addUserToGroup(userConnection, groupId))
		{
			 
			ChatGroup group = chatManager.getGroupById(groupId);
			
			/**
			 * TODO: Send message to members of group that user joined.
			 */
			JoinGroupResponse response = new JoinGroupResponse(joinGroupRequest.getRequestId(),
					group.getGroupModel(),
					JoinGroupResponse.RESPONSE_SUCCESS);
			userConnection.sendResponse(response);
		}
		else
		{
			Logger.getInstance().write("Failure to add user to group.");
		}
		
		
		
	}
	

}
