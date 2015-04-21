package edu.fau.simplechat.request.handler;

import java.util.UUID;

import edu.fau.simplechat.logger.Logger;
import edu.fau.simplechat.request.ClientRequest;
import edu.fau.simplechat.request.MessageRequest;
import edu.fau.simplechat.server.ChatManager;
import edu.fau.simplechat.server.UserConnection;

/**
 * Handler to send a text message to a group.
 * @author kyle
 *
 */
public class MessageRequestHandler extends RequestHandler {

	public MessageRequestHandler(final ClientRequest c, final UserConnection user,
			final ChatManager cM) {
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
