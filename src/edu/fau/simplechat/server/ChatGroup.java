package edu.fau.simplechat.server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

import edu.fau.simplechat.logger.Logger;
import edu.fau.simplechat.model.GroupModel;
import edu.fau.simplechat.model.UserModel;
import edu.fau.simplechat.response.ServerResponse;

/**
 * This class handles communication with
 * the members of a group. This class has the functionality
 * to broadcast a message to all the members of a group.
 * @author kyle
 */
public class ChatGroup {

	/**
	 * The UserConnections of the group
	 */
	private final HashSet<UserConnection> users;

	/**
	 * The moderators of the group
	 */
	private final HashSet<UserModel> moderators;

	/**
	 * An instance of the chat manager
	 */
	private final ChatManager chatManager;

	/**
	 * The name of the group
	 */
	private final String groupName;

	/**
	 * The group id
	 */
	private final UUID id;


	/**
	 * Initializes the class with the given group name.
	 * @param groupName Name of the Group
	 * @param manager ChatManager instance
	 * @precondition: the group name does not exist in another group
	 * @postcondition: the group will be instantiated
	 */
	public ChatGroup(final String groupName, final ChatManager manager)
	{
		users = new HashSet<>();

		moderators = new HashSet<>();

		id = UUID.randomUUID();

		this.chatManager = manager;

		this.groupName = groupName;
	}

	/**
	 * Get the Group Model version of the group.
	 * contains the name, id, and list of moderators.
	 * @return GroupModel instance of the group
	 * @precondition: none
	 * @postconditon: a GroupModel object will be returned
	 */
	public GroupModel getGroupModel()
	{
		return new GroupModel(groupName,id,new ArrayList<UserModel>(moderators));
	}

	/**
	 * Add a moderator to this group
	 * @param user User to become moderator of this group
	 * @return true if the user is not already in the moderators list, false otherwise
	 * @precondition: User is not already moderator
	 * @postcondition: User will be moderator of this group
	 */
	public boolean addModerator(final UserModel user)
	{
		return moderators.add(user);
	}

	/**
	 * Remove a moderator from this group
	 * @param user User that is already moderator of this group
	 * @return true if the user is not already in the moderators list, false otherwise
	 * @precondition: User is already moderator
	 * @postcondition: User will no longer be moderator of this group
	 */
	public boolean removeModerator(final UserModel user)
	{
		return moderators.remove(user);
	}

	/**
	 * Add user to this group
	 * @param user UserConnection to be added to group
	 * @return true if successful, false if user already has joined.
	 * @precondition: User is not already part of this group.
	 * @postcondition: User will be a member of this group.
	 */
	public boolean addUser(final UserConnection user)
	{
		return users.add(user);
	}

	/**
	 * Remove user from this group
	 * @param user UserConnection to be removed from group
	 * @return true if successful, false if user not in group
	 * @precondition: User is already part of this group.
	 * @postcondition: User will no longer be a member of this group.
	 */
	public synchronized boolean removeUser(final UserConnection user)
	{
		Logger.getInstance().write("Attempting to Removing user : "+user.getUserModel().getUsername()+" from chat group.");

		if(users.remove(user))
		{
			Logger.getInstance().write("successfully removed user");
			return true;
		}
		else
		{
			Logger.getInstance().write("Failed to remove user");
			return false;
		}
	}

	/**
	 * Determine if a user in the group
	 * @param user User to check if is in group
	 * @return true if user is in group, false otherwise
	 * @precondition: User is not null
	 * @postcondition: none
	 */
	public boolean hasUser(final UserConnection user)
	{
		return users.contains(user);
	}

	/**
	 * Send a ServerResponse to all users in group
	 * @param response ServerResponse to send to all users
	 * @precondition: response is not null
	 * @postcondition: All users in group will receive response
	 */
	public void broadcast(final ServerResponse response)
	{
		for(UserConnection user : users)
		{
			user.sendResponse(response);
		}
	}


	/**
	 * Perform all of the functionality to clear this group.
	 * @return true if successful, false otherwise
	 * @precondition group contains all connections to users
	 * @postcondition group will have cleared all connections
	 */
	public boolean delete() {
		for(UserConnection user: users)
		{
			user.removeGroup(this);
		}

		users.clear();
		moderators.clear();
		return true;

	}

	@Override
	/**
	 * String representation of group in form of :
	 * Group: {groupname}
	 * -----{user[1]}
	 * -----{user[2]}
	 * ...
	 * -----{user[n]}
	 * 
	 */
	public String toString()
	{
		StringBuffer buffer = new StringBuffer();

		buffer.append("Group: "+groupName+"\n");

		for(UserConnection c : users)
		{
			buffer.append("-----"+c.getUserModel().getUsername()+"\n");
		}

		return buffer.toString();
	}

}
