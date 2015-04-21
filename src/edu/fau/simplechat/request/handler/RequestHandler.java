package edu.fau.simplechat.request.handler;

import edu.fau.simplechat.request.ClientRequest;
import edu.fau.simplechat.server.ChatManager;
import edu.fau.simplechat.server.UserConnection;

/**
 * Abstract class for handler of requests
 * sent by user. Each type of request
 * should have a request handler class
 * associated with it.
 * @author kyle
 */
public abstract class RequestHandler {

	/**
	 * Request to be handled
	 */
	protected ClientRequest clientRequest;

	/**
	 * ChatManager instance
	 */
	protected ChatManager chatManager;

	/**
	 * User Connection to user who received request
	 */
	protected UserConnection userConnection;

	/**
	 * Initialize the RequestHandler with the required
	 * objects to handle the request.
	 * @param clientRequest Request initially sent by user
	 * @param userConnection UserConnection connected to client side
	 * @param chatManager Manager managing the user connections and chat groups
	 */
	public RequestHandler(final ClientRequest clientRequest, final UserConnection userConnection, final ChatManager chatManager)
	{
		this.clientRequest = clientRequest;
		this.userConnection = userConnection;
		this.chatManager = chatManager;

	}

	/**
	 * Call to handle the request.
	 * @precondition none
	 * @postcondition The request will be handled
	 */
	public abstract void handle();

}
