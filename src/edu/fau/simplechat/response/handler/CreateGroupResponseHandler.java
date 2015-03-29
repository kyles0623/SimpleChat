package edu.fau.simplechat.response.handler;

import edu.fau.simplechat.client.ServerConnection;
import edu.fau.simplechat.gui.IRequestListener;
import edu.fau.simplechat.request.ClientRequest;
import edu.fau.simplechat.response.CreateGroupServerResponse;
import edu.fau.simplechat.response.LoginServerResponse;
import edu.fau.simplechat.response.ServerResponse;

public class CreateGroupResponseHandler extends ResponseHandler {

	public CreateGroupResponseHandler(ServerConnection u,ServerResponse r, ClientRequest c,
			IRequestListener listener) {
		super(u,r, c, listener);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle() {
		CreateGroupServerResponse response = (CreateGroupServerResponse)this.response;
		
		if(response.getResponse().equals(LoginServerResponse.RESPONSE_SUCCESS))
		{
			listener.onGroupCreated(response.getGroupModel());
		}
		else
		{
			listener.onError("Error creating group");
		}
		
	}

}
