package edu.fau.simplechat.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import edu.fau.simplechat.logger.Logger;
import edu.fau.simplechat.model.UserModel;
import edu.fau.simplechat.request.ClientRequest;
import edu.fau.simplechat.request.handler.RequestHandler;
import edu.fau.simplechat.request.handler.RequestHandlerFactory;
import edu.fau.simplechat.response.ServerResponse;
import edu.fau.simplechat.response.UserLeftGroupResponse;

/**
 * TODO: UserConnection Description
 * @author kyle
 *
 */
public class UserConnection implements Runnable {

	/**
	 * Main socket connected to user.
	 */
	private final Socket socket;

	/**
	 * id of current user connection
	 */
	private final UUID id;

	private ObjectInputStream inputStream;

	private ObjectOutputStream outputStream;

	private final AtomicBoolean running;

	private final ChatManager chatManager;

	private final ArrayList<ChatGroup> groups;

	private String username;

	/**
	 * TODO: Write Description
	 * @param socket
	 */
	public UserConnection(final Socket socket, final ChatManager cM)
	{
		this.socket = socket;
		groups = new ArrayList<>();
		id = UUID.randomUUID();
		this.chatManager = cM;

		Logger.getInstance().write("Creating Output and Input Objects.");

		try
		{
			inputStream = new ObjectInputStream(socket.getInputStream());
			outputStream = new ObjectOutputStream(socket.getOutputStream());
		}
		catch(IOException e)
		{
			Logger.getInstance().write(e.toString());
		}

		running = new AtomicBoolean(true);

		//TODO: Write Connected To Server Message
	}

	public void addGroup(final ChatGroup group)
	{
		groups.add(group);
	}

	@Override
	public void run()
	{
		while(running.get())
		{
			ClientRequest request = retrieveRequest();
			if(request != null)
			{
				handleRequest(request);
			}
			else
			{
				running.set(false);
			}
		}
	}

	public UUID getUserId()
	{
		return id;
	}

	public void setUsername(final String u)
	{
		Logger.getInstance().write("User "+u+" has Logged In.");
		username = u;
	}

	public UserModel getUserModel()
	{
		return new UserModel(id,username);
	}

	private void handleRequest(final ClientRequest request)
	{
		RequestHandler handler = RequestHandlerFactory.getInstance()
				.createRequestHandler(request, this, chatManager);

		if(handler != null)
		{
			handler.handle();
		}
	}

	public void sendResponse(final ServerResponse response)
	{
		Logger.getInstance().write("Sending Response for:"+response.getClass().toString());
		try
		{
			outputStream.writeObject(response);
		}
		catch (IOException e) {
			Logger.getInstance().write("Error sending response to object: "+e.getMessage());

		}
	}

	public void logout()
	{
		String message;
		if(username != null)
		{
			message = username+" is Logging out";
		}
		else
		{
			message = "User id "+getUserId().toString()+" is logging out";
		}
		Logger.getInstance().write(message);
		for(ChatGroup group : groups)
		{
			group.removeUser(this);
			group.broadcast(new UserLeftGroupResponse(group.getGroupModel(),getUserModel()));

		}

		chatManager.removeUser(this);
		running.set(false);


	}

	private ClientRequest retrieveRequest()
	{
		ClientRequest request = null;

		try {
			request = (ClientRequest)inputStream.readObject();
			Logger.getInstance().write("Request received");
		}
		catch (ClassNotFoundException e) {
		}
		catch (IOException e) {
			Logger.getInstance().write("User disconnected.");
			logout();
		}

		return request;

	}

	/**
	 * Remove the given chat group from this user.
	 * @param chatGroup
	 * @precondition User belongs to chat group
	 * @postcondition User doesnt belong to chat group
	 */
	public void removeGroup(final ChatGroup chatGroup) {
		groups.remove(chatGroup);

	}
}
