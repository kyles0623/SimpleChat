package edu.fau.simplechat.response.handler;

import edu.fau.simplechat.client.ServerConnection;
import edu.fau.simplechat.gui.IResponseListener;
import edu.fau.simplechat.request.ClientRequest;
import edu.fau.simplechat.response.ServerResponse;

/**
 * A Response Handler handles a specific response
 * received from server.
 * @author kyle
 *
 */
public abstract class ResponseHandler {


	protected final IResponseListener listener;

	protected final ClientRequest request;

	protected final ServerResponse response;

	protected final ServerConnection user;

	public ResponseHandler(final ServerConnection user,final ServerResponse r, final ClientRequest c, final IResponseListener listener)
	{
		response = r;
		request = c;
		this.user = user;
		this.listener = listener;
	}

	/**
	 * Handles a specific response from the server.
	 * 
	 * @precondition
	 * @postcondition
	 */
	public abstract void handle();
}
