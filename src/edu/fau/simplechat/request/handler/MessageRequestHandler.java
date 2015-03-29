package edu.fau.simplechat.request.handler;

import java.util.UUID;

import edu.fau.simplechat.logger.Logger;
import edu.fau.simplechat.request.ClientRequest;
import edu.fau.simplechat.request.MessageRequest;
import edu.fau.simplechat.server.ChatManager;
import edu.fau.simplechat.server.UserConnection;

public class MessageRequestHandler extends RequestHandler {

	public MessageRequestHandler(ClientRequest c, UserConnection user,
			ChatManager cM) {
		super(c, user, cM);
	}

	@Override
	public void handle() {
		Logger.getInstance().write("Attempting to handle MessageRequest");
		MessageRequest request = (MessageRequest)clientRequest;
		
		UUID groupId = request.getGroupId();
		
		String message = request.getMessage();
		
		Logger.getInstance().write("New message for group: "+groupId);
		
		chatManager.sendMessage(this.userConnection,groupId,message);

	}


}
