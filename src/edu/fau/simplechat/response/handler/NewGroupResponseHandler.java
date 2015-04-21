package edu.fau.simplechat.response.handler;

import edu.fau.simplechat.client.ServerConnection;
import edu.fau.simplechat.gui.IResponseListener;
import edu.fau.simplechat.model.GroupModel;
import edu.fau.simplechat.request.ClientRequest;
import edu.fau.simplechat.response.NewGroupServerResponse;
import edu.fau.simplechat.response.ServerResponse;

public class NewGroupResponseHandler extends ResponseHandler{

	public NewGroupResponseHandler(ServerConnection user, ServerResponse r,
			ClientRequest c, IResponseListener listener) {
		super(user, r, c, listener);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle() {
		NewGroupServerResponse serverResponse = (NewGroupServerResponse)response;
		GroupModel group = serverResponse.getGroupModel();
		listener.onNewGroup(group);
	}

}
