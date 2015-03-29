package edu.fau.simplechat.server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import edu.fau.simplechat.logger.Logger;
import edu.fau.simplechat.model.GroupModel;
import edu.fau.simplechat.model.MessageModel;
import edu.fau.simplechat.model.UserModel;
import edu.fau.simplechat.response.GroupDeletedResponse;
import edu.fau.simplechat.response.MessageResponse;
import edu.fau.simplechat.response.NewGroupServerResponse;
import edu.fau.simplechat.response.ServerResponse;
import edu.fau.simplechat.response.UserJoinedGroupResponse;
import edu.fau.simplechat.response.UserLeftGroupResponse;

/**
 * the ChatManager manages the creation, deletion, and aggregation
 * of all users and groups.
 * @author kyle
 *
 */
public class ChatManager {

	/**
	 * Index cache of groups by name
	 */
	private final HashMap<String,ChatGroup> groupByName;

	/**
	 * Index cache of groups by id
	 */
	private final HashMap<UUID,ChatGroup> groupById;

	/**
	 * index cache of users by username
	 */
	private final HashMap<String,UserConnection> userByUsername;

	/**
	 * index cache of users by id
	 */
	private final HashMap<UUID,UserConnection> userById;

	/**
	 * Initialize ChatManager
	 */
	public ChatManager()
	{
		groupByName = new HashMap<>();
		groupById = new HashMap<>();
		userByUsername = new HashMap<>();
		userById = new HashMap<>();

		createGroup("Default");
		createGroup("Alternative");
	}

	/**
	 * Create New Group
	 * @param groupName Name of group to create
	 * @return ChatGroup created, or null if group already exists
	 * @precondition: group name is not already taken
	 * @postcondition: new Group will be created
	 */
	public synchronized ChatGroup createGroup(final String groupName)
	{
		if(groupByName.containsKey(groupName))
		{
			return null;
		}

		ChatGroup group = new ChatGroup(groupName,this);
		groupByName.put(groupName, group);

		//Log data
		Logger.getInstance().write("Putting something with ID "+group.getGroupModel().getId());

		groupById.put(group.getGroupModel().getId(), group);

		//Let users know new group has been created
		broadcastAll(new NewGroupServerResponse(null,group.getGroupModel()));

		return group;
	}

	/**
	 * Broadcast a message to all users in chat system.
	 * @param response ServerResponse to broadcast to users
	 * @precondition response is not null
	 * @postcondition all users will receive response
	 */
	private synchronized void broadcastAll(final ServerResponse response)
	{
		for(UserConnection user : userById.values())
		{
			user.sendResponse(response);
		}
	}

	/**
	 * Create a new user connection
	 * @param socket Socket that connected to the server
	 * @return UserConnection instance
	 * @precondition socket connection exists
	 * @postcondition UserConnection is created
	 */
	public synchronized UserConnection createUserConnection(final Socket socket)
	{
		UserConnection user = new UserConnection(socket,this);
		userById.put(user.getUserId(), user);
		return user;
	}

	/**
	 * Check if a username exists in the chat manager
	 * @param username Username to check if exists
	 * @return true if username exists, false otherwise
	 * @precondition username is not null
	 * @postcondition none
	 */
	public synchronized boolean usernameExists(final String username)
	{
		return userByUsername.containsKey(username);
	}

	/**
	 * Set a UserConnection to have a username
	 * @param username username to attach
	 * @param user User to attach username to
	 * @return true is successful, false if username exists
	 * @precondition username and user are not null
	 * @postcondition username will be attached to user connection
	 */
	public synchronized boolean addUsernameToUser(final String username,final UserConnection user)
	{
		if(usernameExists(username))
		{
			return false;
		}

		user.setUsername(username);
		userByUsername.put(username, user);
		return true;
	}

	/**
	 * Retreive ChatGroup based on name
	 * @param name Group name to find
	 * @return ChatGroup with name given
	 * @precondition name exists
	 * @postcondition chat group returned
	 */
	public ChatGroup getGroupByName(final String name)
	{
		return groupByName.get(name);
	}

	/**
	 * Retrieve UserConnection based on username given
	 * @param username Username to find
	 * @return UserConnection with username given
	 * @precondition username exists
	 * @postcondition User Connection returned
	 */
	public UserConnection getUserByUsername(final String username)
	{
		return userByUsername.get(username);
	}

	/**
	 * Add a given user to a given group
	 * @param user User to add to group
	 * @param groupId ID of group to add user to
	 * @return true if successful, false otherwise
	 * @precondition user is not null, user is not already in group, and groupid exists for some group
	 * @postcondition  user will be added to group
	 */
	public synchronized boolean addUserToGroup(final UserConnection user, final UUID groupId)
	{
		ChatGroup group = groupById.get(groupId);
		if(group == null)
		{
			Logger.getInstance().write("add user to group, group is null");
			return false;
		}

		if(groupById.get(groupId).addUser(user))
		{
			group.broadcast(new UserJoinedGroupResponse(group.getGroupModel(),user.getUserModel()));
			return true;
		}

		return false;

	}

	/**
	 * Retrieve a list of groups in chat system
	 * @return List of groups in chat system
	 * @precondition none
	 * @postcondition list of groups will be returned
	 */
	public synchronized List<GroupModel> getGroups() {

		ArrayList<GroupModel> groups = new ArrayList<GroupModel>();

		for(ChatGroup group : groupById.values())
		{
			groups.add(group.getGroupModel());
		}

		return groups;
	}

	/**
	 * Retrieve group based on given id
	 * @param groupId ID of group to retrieve
	 * @return ChatGroup associated with given id
	 * @precondition a group exists for given id
	 * @postcondition a group will be returned
	 */
	public ChatGroup getGroupById(final UUID groupId) {

		return groupById.get(groupId);

	}

	/**
	 * Send a server response message to all members of a group
	 * @param user User sending the message
	 * @param groupId ID of group receiving message
	 * @param message Text of the message
	 * @precondition user is not null, group exists for given group id, and message is not null
	 * @postcondition users in group will receive the message
	 */
	public synchronized void sendMessage(final UserConnection user, final UUID groupId, final String message) {

		Logger.getInstance().write("Sending Message: "+message);

		Logger.getInstance().write("GROUP BY ID: "+groupById.toString());

		ChatGroup group = getGroupById(groupId);

		if(group == null )
		{
			Logger.getInstance().write("Failed to send message. Group is null for id: "+groupId);
			return;
		}
		else if( !group.hasUser(user))
		{
			Logger.getInstance().write("Failed to send message. User not in group.");
			return;
		}

		GroupModel groupModel = group.getGroupModel();
		UserModel userModel = user.getUserModel();

		MessageModel messageModel = new MessageModel(userModel,groupModel,message);

		group.broadcast(new MessageResponse(messageModel));

	}

	/**
	 * Remove user from the chat system.
	 * @param userConnection User to remove
	 * @precondition User exists in chat system
	 * @postcondition User will be removed from chat system
	 */
	public synchronized void removeUser(final UserConnection userConnection)
	{
		userByUsername.remove(userConnection.getUserModel().getUsername());
		userById.remove(userConnection.getUserId());

	}

	/**
	 * Remove a user from a group
	 * @param userConnection User to remove from group
	 * @param groupId ID of group to remove user from
	 * @return true if successful, false otherwise
	 * @precondition User exists, ID exists for a group, and user is in group
	 * @postcondition User will be removed from the group
	 */
	public synchronized boolean removeUserFromGroup(final UserConnection userConnection,final UUID groupId) {

		ChatGroup group = groupById.get(groupId);

		if(group == null)
		{
			Logger.getInstance().write("remove user from group, group is null");
			return false;
		}

		if(groupById.get(groupId).removeUser(userConnection))
		{
			group.broadcast(new UserLeftGroupResponse(group.getGroupModel(),userConnection.getUserModel()));
			return true;
		}

		return false;
	}

	/**
	 * Delete the given group from the system
	 * @param group Group to delete
	 * @param user User that is moderator of the group
	 * @precondition Group exists and user is moderator of it
	 * @postcondition Group will no longer exist.
	 */
	public synchronized boolean deleteGroup(final GroupModel group, final UserModel user) {
		ChatGroup chatGroup = getGroupById(group.getId());


		if(!chatGroup.getGroupModel().isModerator(user))
		{
			return false;
		}

		GroupDeletedResponse response = new GroupDeletedResponse(group);

		chatGroup.broadcast(response);

		groupById.remove(chatGroup.getGroupModel().getId());
		groupByName.remove(chatGroup.getGroupModel().getGroupName());
		chatGroup.delete();

		return true;

	}
}
