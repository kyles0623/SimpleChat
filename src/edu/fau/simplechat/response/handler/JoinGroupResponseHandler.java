package edu.fau.simplechat.response.handler;

import edu.fau.simplechat.client.ServerConnection;
import edu.fau.simplechat.gui.IResponseListener;
import edu.fau.simplechat.model.GroupModel;
import edu.fau.simplechat.request.ClientRequest;
import edu.fau.simplechat.response.JoinGroupResponse;
import edu.fau.simplechat.response.ServerResponse;

public class JoinGroupResponseHandler extends ResponseHandler {

	public JoinGroupResponseHandler(ServerConnection user, ServerResponse r,
			ClientRequest c, IResponseListener listener) {
		super(user, r, c, listener);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle() {
		JoinGroupResponse joinResponse = (JoinGroupResponse)response;
		
		GroupModel group = joinResponse.getGroup();
		
		if(joinResponse.getResponse().equals(JoinGroupResponse.RESPONSE_SUCCESS))
		{
			listener.onGroupJoined(group);
		}

	}

}
