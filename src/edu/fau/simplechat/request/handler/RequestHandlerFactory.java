package edu.fau.simplechat.request.handler;


import edu.fau.simplechat.logger.Logger;
import edu.fau.simplechat.request.ClientRequest;
import edu.fau.simplechat.request.CreateGroupRequest;
import edu.fau.simplechat.request.DeleteGroupRequest;
import edu.fau.simplechat.request.GroupListRequest;
import edu.fau.simplechat.request.JoinGroupRequest;
import edu.fau.simplechat.request.LeaveGroupRequest;
import edu.fau.simplechat.request.LoginClientRequest;
import edu.fau.simplechat.request.MessageRequest;
import edu.fau.simplechat.server.ChatManager;
import edu.fau.simplechat.server.UserConnection;

/**
 * This factory figures out which request handler will best complete the request of the user.
 * @author kyle
 *
 */
public class RequestHandlerFactory {

	private static RequestHandlerFactory instance;

	private RequestHandlerFactory(){}

	public static RequestHandlerFactory getInstance()
	{
		if(instance == null)
		{
			instance = new RequestHandlerFactory();
		}

		return instance;
	}

	public RequestHandler createRequestHandler(final ClientRequest request, final UserConnection user, final ChatManager manager)
	{
		Logger.getInstance().write("Creating Request Handler for: "+request.getClass().getName());

		if(request instanceof LoginClientRequest)
		{
			return new LoginClientRequestHandler(request, user, manager);
		}
		else if(request instanceof CreateGroupRequest)
		{
			return new CreateGroupRequestHandler(request,user,manager);
		}
		else if(request instanceof GroupListRequest)
		{
			return new GroupListRequestHandler(request,user,manager);
		}
		else if(request instanceof JoinGroupRequest)
		{
			return new JoinGroupRequestHandler(request,user,manager);
		}
		else if(request instanceof LeaveGroupRequest)
		{
			return new LeaveGroupRequestHandler(request,user,manager);
		}
		else if(request instanceof MessageRequest)
		{
			return new MessageRequestHandler(request,user,manager);
		}
		else if(request instanceof DeleteGroupRequest)
		{
			return new DeleteGroupRequestHandler(request,user,manager);
		}
		else
		{
			return null;
		}

	}
}
