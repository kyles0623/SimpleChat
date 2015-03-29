package edu.fau.simplechat.response.handler;

import edu.fau.simplechat.client.ServerConnection;
import edu.fau.simplechat.gui.IRequestListener;
import edu.fau.simplechat.model.MessageModel;
import edu.fau.simplechat.request.ClientRequest;
import edu.fau.simplechat.response.MessageResponse;
import edu.fau.simplechat.response.ServerResponse;

public class MessageResponseHandler extends ResponseHandler {

	public MessageResponseHandler(ServerConnection user, ServerResponse r,
			ClientRequest c, IRequestListener listener) {
		super(user, r, c, listener);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle() {

		MessageResponse mResponse = (MessageResponse)response;
		MessageModel message = mResponse.getMessage();
		
		if(message != null)
		{
			listener.onMessageReceived(message);
		}

	}

}
