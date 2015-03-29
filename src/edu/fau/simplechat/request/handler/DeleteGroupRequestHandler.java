package edu.fau.simplechat.request.handler;

import edu.fau.simplechat.model.GroupModel;
import edu.fau.simplechat.request.ClientRequest;
import edu.fau.simplechat.request.DeleteGroupRequest;
import edu.fau.simplechat.server.ChatManager;
import edu.fau.simplechat.server.UserConnection;

public class DeleteGroupRequestHandler extends RequestHandler {

	public DeleteGroupRequestHandler(final ClientRequest c, final UserConnection user,
			final ChatManager cM) {
		super(c, user, cM);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle()
	{
		DeleteGroupRequest request = (DeleteGroupRequest)clientRequest;
		GroupModel group = request.getGroup();
		chatManager.deleteGroup(group,userConnection.getUserModel());

	}

}
