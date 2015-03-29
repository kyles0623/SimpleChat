package edu.fau.simplechat.response.handler;

import java.util.List;

import edu.fau.simplechat.client.ServerConnection;
import edu.fau.simplechat.gui.IRequestListener;
import edu.fau.simplechat.model.GroupModel;
import edu.fau.simplechat.request.ClientRequest;
import edu.fau.simplechat.response.GroupListResponse;
import edu.fau.simplechat.response.ServerResponse;


public class GroupListResponseHandler extends ResponseHandler {

	public GroupListResponseHandler(ServerConnection user, ServerResponse r,
			ClientRequest c, IRequestListener listener) {
		super(user, r, c, listener);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle() {
		
		GroupListResponse groupResponse = (GroupListResponse)response;
		
		List<GroupModel> groups = groupResponse.getGroups();
		
		if(groups == null)
		{
			listener.onError("Error retrieving Group List");
		}
		else
		{
			listener.onGroupListReceived(groups);
		}
	}

	

}
