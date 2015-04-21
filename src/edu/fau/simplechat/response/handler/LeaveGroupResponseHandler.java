package edu.fau.simplechat.response.handler;

import edu.fau.simplechat.client.ServerConnection;
import edu.fau.simplechat.gui.IResponseListener;
import edu.fau.simplechat.request.ClientRequest;
import edu.fau.simplechat.response.LeaveGroupResponse;
import edu.fau.simplechat.response.ServerResponse;


public class LeaveGroupResponseHandler extends ResponseHandler {

	public LeaveGroupResponseHandler(ServerConnection user, ServerResponse r,
			ClientRequest c, IResponseListener listener) {
		super(user, r, c, listener);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle() {
		
		LeaveGroupResponse leaveGroupResponse = (LeaveGroupResponse)this.response;
		
		listener.onGroupLeft(leaveGroupResponse.getGroup());
		
	}
	
	

}
