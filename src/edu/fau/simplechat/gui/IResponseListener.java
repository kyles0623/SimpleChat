package edu.fau.simplechat.gui;

import java.util.List;

import edu.fau.simplechat.model.GroupModel;
import edu.fau.simplechat.model.MessageModel;
import edu.fau.simplechat.model.UserModel;
import edu.fau.simplechat.response.FileMessageResponse;

/**
 * The IResponseListener interface defines the methods
 * called after receiving responses from the server.
 * @author kyle
 */
public interface IResponseListener {

	/**
	 * Called when the connection to the server has been established.
	 * @precondition
	 * @postcondition
	 */
	void onConnection();

	/**
	 * Called when the user has successfully logged in. If an error occurred,
	 * onError will be called.
	 * @param user The UserModel representation of the user that has successfully logged in
	 * @precondition
	 * @postcondition
	 */
	void onLogin(UserModel user);

	/**
	 * Called when the user has successfully logged out. If an error occurred,
	 * onError will be called.
	 * @param user The UserModel representation of the user that has successfully logged out
	 * @precondition
	 * @postcondition
	 */
	void onLogout(UserModel user);

	/**
	 * Called when the current user has successfully joined a group. If an error occurred,
	 * onError will be called.
	 * @param group The GroupModel representation of the group that the user has successfully joined.
	 * @precondition
	 * @postcondition
	 */
	void onGroupJoined(GroupModel group);

	/**
	 * Called when the current user has successfully left a group. If an error occurred,
	 * onError will be called.
	 * @param group The GroupModel representation of the group that the user has successfully left.
	 * @precondition
	 * @postcondition
	 */
	void onGroupLeft(GroupModel group);

	/**
	 * Called when a new group has been created.
	 * @param group The GroupModel representation of the group that has been created.
	 * @precondition
	 * @postcondition
	 */
	void onNewGroup(GroupModel group);

	/**
	 * Called when the list of active groups has been received.
	 * @param groupList List of GroupModels representing the active groups
	 * @precondition
	 * @postcondition
	 */
	void onGroupListReceived(List<GroupModel> groupList);

	/**
	 * Called when a new group has been created by the current user.
	 * @param group
	 * @precondition
	 * @postcondition
	 */
	void onGroupCreated(GroupModel group);

	/**
	 * Called when a new group has been deleted by the current user.
	 * @param group group being deleted
	 * @precondition
	 * @postcondition
	 */
	void onGroupDeleted(GroupModel group);

	/**
	 * Called when a message has been received from the server
	 * @param message Message received from server
	 * @precondition
	 * @postcondition
	 */
	void onMessageReceived(MessageModel message);

	/**
	 * Called when a new user has joined a group
	 * @param group Group user has joined
	 * @param user User who joined group
	 * @precondition
	 * @postcondition
	 */
	void onUserJoinedGroup(GroupModel group, UserModel user);

	/**
	 * Called when an error has occurred
	 * @param error String representation of error
	 * @precondition
	 * @postcondition
	 */
	void onError(String error);

	/**
	 * Called when the connection to the server has been lost.
	 * @precondition
	 * @postcondition
	 */
	void onLostConnection();

	/**
	 * Called when a specific user has left a specific group
	 * @param group Group user left
	 * @param user User who left the group
	 * @precondition
	 * @postcondition
	 */
	void onUserLeftGroup(GroupModel group, UserModel user);

	/**
	 * Called when the user has received a message
	 * containing a file.
	 * @param fileResponse Response containing file informatio
	 * @precondition
	 * @postcondition
	 */
	void onFileMessageReceived(FileMessageResponse fileResponse);
}
