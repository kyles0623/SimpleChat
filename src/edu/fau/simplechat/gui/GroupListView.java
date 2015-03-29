package edu.fau.simplechat.gui;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import edu.fau.simplechat.model.GroupModel;

public class GroupListView extends ListView<GroupModel>
{


	List<GroupModel> groups;

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

	public void addItem(final GroupModel group)
	{
		groups.add(group);
		this.getItems().add(group);

	}

	public void removeItem(final GroupModel group)
	{
		groups.remove(group);
		this.getItems().remove(group);
	}

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
