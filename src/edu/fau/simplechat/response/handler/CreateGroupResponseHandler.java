package edu.fau.simplechat.response.handler;

import edu.fau.simplechat.client.ServerConnection;
import edu.fau.simplechat.gui.IResponseListener;
import edu.fau.simplechat.request.ClientRequest;
import edu.fau.simplechat.response.CreateGroupServerResponse;
import edu.fau.simplechat.response.ServerResponse;

public class CreateGroupResponseHandler extends ResponseHandler {

	public CreateGroupResponseHandler(final ServerConnection u,final ServerResponse r, final ClientRequest c,
			final IResponseListener listener) {
		super(u,r, c, listener);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle() {
		CreateGroupServerResponse response = (CreateGroupServerResponse)this.response;

		if(response.getResponse().equals(CreateGroupServerResponse.RESPONSE_SUCCESS))
		{
			listener.onGroupCreated(response.getGroupModel());
		}
		else if(response.getResponse().equals(CreateGroupServerResponse.RESPONSE_GROUP_EXISTS))
		{
			listener.onError("That group name already exists. Please use a different group name.");
		}
		else
		{
			listener.onError("An error occured when attempting to create the group. Please try again later.");
		}

	}

}
