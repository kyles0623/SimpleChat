package edu.fau.simplechat.response.handler;

import edu.fau.simplechat.client.ServerConnection;
import edu.fau.simplechat.gui.IResponseListener;
import edu.fau.simplechat.request.ClientRequest;
import edu.fau.simplechat.response.CreateGroupServerResponse;
import edu.fau.simplechat.response.FileMessageResponse;
import edu.fau.simplechat.response.GroupDeletedResponse;
import edu.fau.simplechat.response.GroupListResponse;
import edu.fau.simplechat.response.JoinGroupResponse;
import edu.fau.simplechat.response.LeaveGroupResponse;
import edu.fau.simplechat.response.LoginServerResponse;
import edu.fau.simplechat.response.MessageResponse;
import edu.fau.simplechat.response.NewGroupServerResponse;
import edu.fau.simplechat.response.ServerResponse;
import edu.fau.simplechat.response.UserJoinedGroupResponse;
import edu.fau.simplechat.response.UserLeftGroupResponse;

public class ResponseHandlerFactory {

	private static ResponseHandlerFactory instance;

	private ResponseHandlerFactory(){}

	public static ResponseHandlerFactory getInstance()
	{
		if(instance == null)
		{
			instance = new ResponseHandlerFactory();
		}

		return instance;
	}

	public ResponseHandler createResponseHandler(final ServerConnection u,final ServerResponse response, final ClientRequest request,
			final IResponseListener listener)
	{
		if(response instanceof LoginServerResponse)
		{
			return new LoginResponseHandler(u,response, request, listener);
		}
		else if(response instanceof CreateGroupServerResponse)
		{
			return new CreateGroupResponseHandler(u,response,request,listener);
		}
		else if(response instanceof NewGroupServerResponse)
		{
			return new NewGroupResponseHandler(u,response,request,listener);
		}
		else if(response instanceof GroupListResponse)
		{
			return new GroupListResponseHandler(u,response,request,listener);
		}
		else if(response instanceof JoinGroupResponse)
		{
			return new JoinGroupResponseHandler(u,response,request,listener);
		}
		else if(response instanceof MessageResponse)
		{
			return new MessageResponseHandler(u,response,request,listener);
		}
		else if(response instanceof UserJoinedGroupResponse)
		{
			return new UserJoinedGroupResponseHandler(u,response,request,listener);
		}
		else if(response instanceof UserLeftGroupResponse)
		{
			return new UserLeftGroupResponseHandler(u,response,request,listener);
		}
		else if(response instanceof LeaveGroupResponse)
		{
			return new LeaveGroupResponseHandler(u,response,request,listener);
		}
		else if(response instanceof GroupDeletedResponse)
		{
			return new GroupDeletedResponseHandler(u,response,request,listener);
		}
		else if(response instanceof FileMessageResponse)
		{
			return new FileMessageResponseHandler(u,response,request,listener);
		}
		else
		{
			return null;
		}

	}
}
