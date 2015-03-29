package edu.fau.simplechat.gui;

import java.util.List;

import edu.fau.simplechat.model.GroupModel;
import edu.fau.simplechat.model.MessageModel;
import edu.fau.simplechat.model.UserModel;

public interface IRequestListener {

	void onConnection();
	
	void onLogin(UserModel user);
	
	void onLogout(UserModel user);
	
	void onGroupJoined(GroupModel group);
	
	void onGroupLeft(GroupModel group);
	
	void onNewGroup(GroupModel group);
	
	void onGroupListReceived(List<GroupModel> group);
	
	void onGroupCreated(GroupModel group);
	
	void onGroupDeleted(GroupModel group);
	
	void onMessageReceived(MessageModel message);
	
	void onUserJoinedGroup(GroupModel group, UserModel user);
	
	void onError(String error);

	void onLostConnection();

	void onUserLeftGroup(GroupModel group, UserModel user);
}
