package edu.fau.simplechat.response.handler;

import edu.fau.simplechat.client.ServerConnection;
import edu.fau.simplechat.gui.IResponseListener;
import edu.fau.simplechat.request.ClientRequest;
import edu.fau.simplechat.response.FileMessageResponse;
import edu.fau.simplechat.response.ServerResponse;

public class FileMessageResponseHandler extends ResponseHandler {

	public FileMessageResponseHandler(final ServerConnection user, final ServerResponse r,
			final ClientRequest c, final IResponseListener listener) {
		super(user, r, c, listener);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle() {

		FileMessageResponse fileResponse = (FileMessageResponse)response;
		listener.onFileMessageReceived(fileResponse);

	}

}
