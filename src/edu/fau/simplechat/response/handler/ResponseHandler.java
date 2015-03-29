package edu.fau.simplechat.response.handler;

import edu.fau.simplechat.client.ServerConnection;
import edu.fau.simplechat.gui.IRequestListener;
import edu.fau.simplechat.request.ClientRequest;
import edu.fau.simplechat.response.ServerResponse;

public abstract class ResponseHandler {

	
	protected final IRequestListener listener;
	
	protected final ClientRequest request;
	
	protected final ServerResponse response;
	
	protected final ServerConnection user;
	
	public ResponseHandler(ServerConnection user,ServerResponse r, ClientRequest c, IRequestListener listener)
	{
		response = r;
		request = c;
		this.user = user;
		this.listener = listener;
	}
	
	public abstract void handle();
}
