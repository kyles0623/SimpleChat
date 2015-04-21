package edu.fau.simplechat.request.handler;

import edu.fau.simplechat.model.FileMessageModel;
import edu.fau.simplechat.request.ClientRequest;
import edu.fau.simplechat.request.FileMessageRequest;
import edu.fau.simplechat.response.FileMessageResponse;
import edu.fau.simplechat.response.ServerResponse;
import edu.fau.simplechat.server.ChatManager;
import edu.fau.simplechat.server.UserConnection;

/**
 * Handler to send the file message to a group.
 * @author kyle
 *
 */
public class FileMessageRequestHandler extends RequestHandler {

	public FileMessageRequestHandler(final ClientRequest clientRequest,
			final UserConnection userConnection, final ChatManager chatManager) {
		super(clientRequest, userConnection, chatManager);
	}

	@Override
	public void handle() {
		FileMessageRequest request = (FileMessageRequest)clientRequest;

		FileMessageModel fileMessage = request.getFileMessage();
		ServerResponse response = new FileMessageResponse(request.getRequestId(),fileMessage);
		chatManager.getGroupById(fileMessage.getGroup().getId()).broadcast(response);

	}

}
