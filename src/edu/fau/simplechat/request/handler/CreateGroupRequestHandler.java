package edu.fau.simplechat.request.handler;

import edu.fau.simplechat.request.ClientRequest;
import edu.fau.simplechat.request.CreateGroupRequest;
import edu.fau.simplechat.response.CreateGroupServerResponse;
import edu.fau.simplechat.server.ChatGroup;
import edu.fau.simplechat.server.ChatManager;
import edu.fau.simplechat.server.UserConnection;

public class CreateGroupRequestHandler extends RequestHandler{

	public CreateGroupRequestHandler(final ClientRequest c, final UserConnection user,
			final ChatManager cM) {
		super(c, user, cM);
	}

	@Override
	public void handle() {
		CreateGroupRequest request = (CreateGroupRequest)clientRequest;

		ChatGroup group = chatManager.createGroup(request.getGroupName());

		/**
		 * Group exists
		 */
		if(group == null)
		{
			CreateGroupServerResponse response = new CreateGroupServerResponse(request.getRequestId()
					,CreateGroupServerResponse.RESPONSE_GROUP_EXISTS,null);
			userConnection.sendResponse(response);
			return;
		}

		group.addUser(userConnection);
		group.addModerator(userConnection.getUserModel());

		CreateGroupServerResponse response = new CreateGroupServerResponse(request.getRequestId()
				,CreateGroupServerResponse.RESPONSE_SUCCESS,group.getGroupModel());

		userConnection.sendResponse(response);

	}

}
