package edu.fau.simplechat.request.handler;


import edu.fau.simplechat.logger.Logger;
import edu.fau.simplechat.request.ClientRequest;
import edu.fau.simplechat.request.CreateGroupRequest;
import edu.fau.simplechat.request.DeleteGroupRequest;
import edu.fau.simplechat.request.FileMessageRequest;
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

	private static RequestHandlerFactory instance = new RequestHandlerFactory();;

	/**
	 * Private Constructor
	 */
	private RequestHandlerFactory(){}

	/**
	 * Retrieve single instance of the factory
	 * @return Instance of the Request Handler Factory
	 * @precondition none
	 * @postcondition none
	 */
	public static RequestHandlerFactory getInstance()
	{
		return instance;
	}

	/**
	 * Retrieve a RequestHandler based on the given ClientRequest
	 * @param request Request being received
	 * @param user User associated with request
	 * @param manager Chat Manager to be used by the handler
	 * @return Request Handler associated with the given request,
	 * or null if no handler is associated with request.
	 * @precondition Request has associated Handler, user connection is not null, and chat manager is not null.
	 * @postcondition Request Handler will be returned
	 */
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
		else if (request instanceof FileMessageRequest)
		{
			return new FileMessageRequestHandler(request,user,manager);
		}
		else
		{
			return null;
		}

	}
}
