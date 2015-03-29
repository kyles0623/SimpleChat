package edu.fau.simplechat.controller;


import edu.fau.simplechat.client.ServerConnection;
import edu.fau.simplechat.gui.IRequestListener;
import edu.fau.simplechat.logger.Logger;
import edu.fau.simplechat.model.GroupModel;
import edu.fau.simplechat.model.UserModel;
import edu.fau.simplechat.request.CreateGroupRequest;
import edu.fau.simplechat.request.DeleteGroupRequest;
import edu.fau.simplechat.request.GroupListRequest;
import edu.fau.simplechat.request.JoinGroupRequest;
import edu.fau.simplechat.request.LeaveGroupRequest;
import edu.fau.simplechat.request.LoginClientRequest;
import edu.fau.simplechat.request.MessageRequest;

/**
 * This class handles the interaction between the GUI and the ServerConnection.
 * @author kyle
 */
public class ChatController {

	/**
	 * Connection to Server. Requests and sent through this.
	 */
	private ServerConnection connection;

	/**
	 * Listener defining what to do when specific request are received.
	 */
	private final IRequestListener requestListener;

	/**
	 * Initialize Chat Controller with Request Listener
	 * @param requestListener Request Listener defining what happens when requests are received
	 * @precondition RequestListener is not null
	 * @postcondition chat controller will be initialized
	 */
	public ChatController(final IRequestListener requestListener)
	{
		this.requestListener = requestListener;
	}

	/**
	 * Closes connection to server.
	 * @precondition connect() has been called
	 * @postcondition User will be disconnected from server
	 */
	public void close()
	{
		Logger.getInstance().write("closing system");
		if(connection != null)
		{
			connection.stopRunning();
		}
	}

	/**
	 * Create connection to server
	 * @param host Host name of server
	 * @param port Port to connect to server through
	 * @precondition host and port are available for connection
	 * @postcondition User will be connected to system
	 */
	public void connect(final String host, final int port)
	{
		connection = new ServerConnection(host,port);
		connection.setRequestListener(requestListener);
		connection.connect();
		connection.start();
	}

	/**
	 * Login user to chat system
	 * @param username Username to use
	 * @precondition User has not already logged in and username is not taken
	 * @postcondition User will be logged into system
	 */
	public void login(final String username) {
		LoginClientRequest request = new LoginClientRequest(username);
		connection.sendRequest(request);

	}

	/**
	 * Send Request for Group List
	 * @precondition User has logged in
	 * @postcondition User will receive group list
	 */
	public void requestGroupList() {

		GroupListRequest request = new GroupListRequest(connection.getUser().getId());
		connection.sendRequest(request);
	}

	/**
	 * Send Message to people in Group
	 * @param message Message to send them
	 * @param group Group to send to
	 * @precondition User is connected to group
	 * @postcondition Users will receive message
	 */
	public void sendMessage(final String message, final GroupModel group) {

		MessageRequest request = new MessageRequest(connection.getUser().getId(),group.getId(),message);
		connection.sendRequest(request);
	}

	/**
	 * Join a group to communicate with
	 * @param group Group to join
	 * @precondition Group has not been joined already
	 * @postcondition User will be added to group
	 */
	public void joinGroup(final GroupModel group) {
		JoinGroupRequest request = new JoinGroupRequest(connection.getUser().getId(),group.getId());
		connection.sendRequest(request);
	}

	/**
	 * Leave group current being communicated with
	 * @param group
	 * @precondition joinGroup has been called for same group
	 * @postcondition User will be disconnected from group.
	 */
	public void leaveGroup(final GroupModel group)
	{
		LeaveGroupRequest request = new LeaveGroupRequest(connection.getUser().getId(),group.getId());
		connection.sendRequest(request);
	}

	/**
	 * Retrieve Users information
	 * @precondition TODO
	 * @postcondition TODO
	 * @return
	 */
	public UserModel getCurrentUser() {
		return connection.getUser();
	}

	/**
	 * Forward  call to the RequestListener.
	 * @param error Error to be displayed to user.
	 */
	public void onError(final String error)
	{
		requestListener.onError(error);
	}

	/**
	 * Send Request to create group
	 * @param groupName Name of Group to create
	 * @precondition group name is not already created.
	 * @postcondition Group will be created
	 */
	public void createGroup(final String groupName) {
		CreateGroupRequest request = new CreateGroupRequest(connection.getUser().getId(),groupName);
		connection.sendRequest(request);
	}

	/**
	 * Send request to delete group
	 * @param group Group to be deleted
	 * @precondition group is not null and exists
	 * @postcondition group will be deleted
	 */
	public void deleteGroup(final GroupModel group) {
		DeleteGroupRequest request = new DeleteGroupRequest(connection.getUser().getId(),group);
		connection.sendRequest(request);

	}

}
