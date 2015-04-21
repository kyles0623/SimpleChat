package edu.fau.simplechat.response.handler;

import edu.fau.simplechat.client.ServerConnection;
import edu.fau.simplechat.gui.IResponseListener;
import edu.fau.simplechat.logger.Logger;
import edu.fau.simplechat.model.MessageModel;
import edu.fau.simplechat.request.ClientRequest;
import edu.fau.simplechat.response.MessageResponse;
import edu.fau.simplechat.response.ServerResponse;

public class MessageResponseHandler extends ResponseHandler {

	public MessageResponseHandler(final ServerConnection user, final ServerResponse r,
			final ClientRequest c, final IResponseListener listener) {
		super(user, r, c, listener);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle() {

		MessageResponse mResponse = (MessageResponse)response;
		MessageModel message = mResponse.getMessage();

		if(message != null)
		{
			Logger.getInstance().write("Received message: "+message.getMessage());
			listener.onMessageReceived(message);
		}
		else
		{
			Logger.getInstance().write("Message received is null");
		}

	}

}
