package edu.fau.simplechat.request.handler;

import edu.fau.simplechat.request.ClientRequest;
import edu.fau.simplechat.server.ChatManager;
import edu.fau.simplechat.server.UserConnection;

/**
 * Abstract class for handler of requests sent by user.
 * @author kyle
 *
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
	
	public RequestHandler(ClientRequest c, UserConnection user, ChatManager cM)
	{
		this.clientRequest = c;
		this.userConnection = user;
		this.chatManager = cM;
		
	}
	
	public abstract void handle();
	
}
