package edu.fau.simplechat.request.handler;

import edu.fau.simplechat.logger.Logger;
import edu.fau.simplechat.request.ClientRequest;
import edu.fau.simplechat.request.LoginClientRequest;
import edu.fau.simplechat.response.LoginServerResponse;
import edu.fau.simplechat.server.ChatManager;
import edu.fau.simplechat.server.UserConnection;

/**
 * Handler to log in a client with a given user name.
 * @author kyle
 *
 */
public class LoginClientRequestHandler extends RequestHandler {

	public LoginClientRequestHandler(final ClientRequest c, final UserConnection user,
			final ChatManager cM) {
		super(c, user, cM);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle() {
		Logger.getInstance().write("Handling LoginClientRequest");

		LoginClientRequest request = (LoginClientRequest)clientRequest;
		String username = request.getUsername();

		String response = LoginServerResponse.RESPONSE_SUCCESS;

		if(!chatManager.addUsernameToUser(username, userConnection))
		{
			response = LoginServerResponse.RESPONSE_USERNAME_EXISTS;
		}

		userConnection.sendResponse(new LoginServerResponse(request.getRequestId(),
				response,
				userConnection.getUserModel()));
	}

}
