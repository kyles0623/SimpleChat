package edu.fau.simplechat.gui;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import edu.fau.simplechat.model.GroupModel;

/**
 * GroupListView is a container used to display
 * all groups associated with chat system
 * @author kyle
 *
 */
public class GroupListView extends ListView<GroupModel>
{

	/**
	 * List of groups to display
	 */
	List<GroupModel> groups;

	/**
	 * Initialize with initial group list
	 * @param groups Initial Group List
	 */
	public GroupListView(final List<GroupModel> groups)
	{
		super();
		this.groups = groups;

		this.setItems(FXCollections.observableArrayList(groups));
		this.setCellFactory(new Callback<ListView<GroupModel>,
				ListCell<GroupModel>>() {
			@Override
			public ListCell<GroupModel> call(final ListView<GroupModel> list) {
				return new GroupChatCell( );
			}
		}
				);
	}

	/**
	 * Add a new group
	 * @param group Group to add
	 * @precondition group is not null
	 * @postcondition Group List will display new group
	 */
	public void addItem(final GroupModel group)
	{
		groups.add(group);
		this.getItems().add(group);

	}

	/**
	 * Remove a group
	 * @param group GRoup to remove
	 * @precondition group is in list
	 * @postcondition Group list will no longer display group
	 */
	public void removeItem(final GroupModel group)
	{
		groups.remove(group);
		this.getItems().remove(group);
	}

	/**
	 * ListCell used to display a group appropriated
	 * @author kyle
	 *
	 */
	private class GroupChatCell extends ListCell<GroupModel>
	{
		@Override
		public void updateItem(final GroupModel item, final boolean empty) {
			super.updateItem(item, empty);

			if(item != null) {
				setGraphic(new Label(item.getGroupName()));
			}
		}

	}

}
