package edu.fau.simplechat.response.handler;

import edu.fau.simplechat.client.ServerConnection;
import edu.fau.simplechat.gui.IResponseListener;
import edu.fau.simplechat.request.ClientRequest;
import edu.fau.simplechat.response.ServerResponse;
import edu.fau.simplechat.response.UserLeftGroupResponse;

public class UserLeftGroupResponseHandler extends ResponseHandler {

	public UserLeftGroupResponseHandler(ServerConnection user,
			ServerResponse r, ClientRequest c, IResponseListener listener) {
		super(user, r, c, listener);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle() {
		UserLeftGroupResponse jgResponse = (UserLeftGroupResponse)response;
		listener.onUserLeftGroup(jgResponse.getGroup(),jgResponse.getUser());
		
	}

}
