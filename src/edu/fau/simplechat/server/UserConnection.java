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
 * UserConnection contains the socket connection
 * to a user over the network. All requests are
 * received through the UserConnection class
 * and responses are sent through the UserConnection class.
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

	/**
	 * InputStream to receive requests from user
	 */
	private ObjectInputStream inputStream;

	/**
	 * OutputStream to send responses to user
	 */
	private ObjectOutputStream outputStream;

	/**
	 * Boolean representing if connection is listening for requests
	 */
	private final AtomicBoolean running;

	/**
	 * Chat manager instance to handle requests
	 */
	private final ChatManager chatManager;

	/**
	 * Groups User is a member of
	 */
	private final ArrayList<ChatGroup> groups;

	/**
	 * User name of user
	 */
	private String username;

	/**
	 * Initialize the I/O connections to the user
	 * @param socket Socket user is connected to
	 * @param chatManager Instance of chat Manager
	 */
	public UserConnection(final Socket socket, final ChatManager chatManager)
	{
		this.socket = socket;
		groups = new ArrayList<>();
		id = UUID.randomUUID();
		this.chatManager = chatManager;

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

	/**
	 * Add a chat group to the user
	 * @param group Group user is a member of
	 * @precondition group is not null
	 * @postcondition User will have a connection to the group
	 */
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
				logout();
			}
		}
	}

	/**
	 * Retrieve the USer ID of the user
	 * @return User Id of the user
	 * @precondition none
	 * @postcondition none
	 */
	public UUID getUserId()
	{
		return id;
	}

	/**
	 * Set the user name of the user
	 * @param u username of the user
	 * @precondition username is not taken already
	 * @postcondition user will be associated with username
	 */
	public void setUsername(final String u)
	{
		Logger.getInstance().write("User "+u+" has Logged In.");
		username = u;
	}

	/**
	 * Retrieve the user model of the user
	 * @return User Model rerpesenting user
	 * @precondition none
	 * @postcondition none
	 */
	public UserModel getUserModel()
	{
		return new UserModel(id,username);
	}

	/**
	 * Handle a request received from the user
	 * @param request Request to be handled
	 * @precondition request is not null
	 * @postcondition Request will be handled
	 */
	private void handleRequest(final ClientRequest request)
	{
		RequestHandler handler = RequestHandlerFactory.getInstance()
				.createRequestHandler(request, this, chatManager);

		if(handler != null)
		{
			handler.handle();
		}
	}

	/**
	 * Send a response to the user
	 * @param response Response to be sent
	 * @precondition Connection is still active
	 * @postcondition User will be receive response
	 */
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

	/**
	 * Log the user out of the chat system
	 * @precondition user is logged in chat system
	 * @postcondition User will be disconnected from chat system
	 */
	public synchronized void logout()
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

		//Let all the goups know the user is gone
		for(ChatGroup group : groups)
		{

			if(!group.removeUser(this))
			{
				Logger.getInstance().write("Error removing user "+username);
			}
			group.broadcast(new UserLeftGroupResponse(group.getGroupModel(),getUserModel()));

		}

		chatManager.removeUser(this);
		running.set(false);


	}

	/**
	 * Listen for request from user
	 * @return Request sent by user
	 * @precondition connection is still active
	 * @postcondition Request will be received fom user when they send it
	 */
	private ClientRequest retrieveRequest()
	{
		ClientRequest request = null;

		try {
			//waits for connection to send object
			request = (ClientRequest)inputStream.readObject();
			Logger.getInstance().write("Request received");
		}
		catch (ClassNotFoundException e) {
			//Shouldn't be called.
		}
		catch (IOException e) {
			Logger.getInstance().write("User disconnected.");
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
