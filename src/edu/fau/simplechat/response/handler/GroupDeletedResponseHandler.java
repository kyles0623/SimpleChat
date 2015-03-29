package edu.fau.simplechat.response.handler;

import edu.fau.simplechat.client.ServerConnection;
import edu.fau.simplechat.gui.IRequestListener;
import edu.fau.simplechat.model.GroupModel;
import edu.fau.simplechat.request.ClientRequest;
import edu.fau.simplechat.response.GroupDeletedResponse;
import edu.fau.simplechat.response.ServerResponse;

public class GroupDeletedResponseHandler extends ResponseHandler {

	public GroupDeletedResponseHandler(final ServerConnection user, final ServerResponse r,
			final ClientRequest c, final IRequestListener listener) {
		super(user, r, c, listener);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle() {
		GroupDeletedResponse groupDeletedResponse = (GroupDeletedResponse)response;

		GroupModel group = groupDeletedResponse.getGroup();

		listener.onGroupDeleted(group);


	}

}
