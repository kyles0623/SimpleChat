package edu.fau.simplechat.request.handler;

import java.util.List;

import edu.fau.simplechat.model.GroupModel;
import edu.fau.simplechat.request.ClientRequest;
import edu.fau.simplechat.response.GroupListResponse;
import edu.fau.simplechat.server.ChatManager;
import edu.fau.simplechat.server.UserConnection;

public class GroupListRequestHandler extends RequestHandler {

	public GroupListRequestHandler(ClientRequest c, UserConnection user,
			ChatManager cM) {
		super(c, user, cM);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle() {
		
		List<GroupModel> groups = chatManager.getGroups();
		
		GroupListResponse response = new GroupListResponse(clientRequest.getRequestId(),groups);
		
		userConnection.sendResponse(response);
		
	}

}
