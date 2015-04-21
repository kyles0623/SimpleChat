package edu.fau.simplechat.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import edu.fau.simplechat.controller.ChatController;
import edu.fau.simplechat.logger.Logger;
import edu.fau.simplechat.model.GroupModel;
import edu.fau.simplechat.model.MessageModel;
import edu.fau.simplechat.model.UserModel;
import edu.fau.simplechat.response.FileMessageResponse;

/**
 * This is the entry point to the application.
 * this is the main setup for the GUI and
 * @author kyle
 *
 */
public class MainApplication extends Application{

	/**
	 * Primary Stage for application
	 */
	private Stage primaryStage;

	/**
	 * Chat Controller for handling communication with server
	 */
	private ChatController controller;

	/**
	 * Set of Active groups in chat system
	 */
	private HashSet<GroupModel> groupsActive;

	/**
	 * Tab Pane holding all Chat Tabs
	 */
	private TabPane tabPane;

	/**
	 * List of groups user can enter
	 */
	private GroupListView groupListView;

	/**
	 * Button to create a new group
	 */
	private Button createButton;

	/**
	 * Source of style sheet
	 */
	String styleSheetSource;

	public static void main(final String args[])
	{
		launch(args);
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {

		this.primaryStage = primaryStage;
		primaryStage.setTitle("Simple Chat - Connect to Server");
		groupsActive = new HashSet<GroupModel>();

		controller = new ChatController(new RequestListener());
		ConnectionPane connection = new ConnectionPane(controller);

		Scene mainScene = new Scene(connection,400,200);
		styleSheetSource = MainApplication.class.getResource("/style/style.css").toExternalForm();

		mainScene.getStylesheets().add(styleSheetSource);
		primaryStage.setResizable(false);
		primaryStage.setScene(mainScene);

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){

			@Override
			public void handle(final WindowEvent event) {
				Logger.getInstance().write("Closing application.");
				controller.close();
			}
		});
		primaryStage.show();
	}

	private void goToLogin()
	{
		primaryStage.setTitle("Simple Chat Server - Login");
		LoginBox loginPane = new LoginBox(controller);
		Scene mainScene = new Scene(loginPane,400,100);
		mainScene.getStylesheets().add(styleSheetSource);
		primaryStage.setScene(mainScene);
	}

	private void displayError(final String error)
	{

		AlertDialog dialog = new AlertDialog(primaryStage,error);
		dialog.show();
	}

	private void showMainView(final List<GroupModel> list)
	{
		primaryStage.setResizable(true);
		tabPane = new TabPane();
		groupListView = new GroupListView(list);
		createButton = new Button("+");
		createButton.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(final MouseEvent arg0) {
				showCreateGroupBox();
			}
		});

		final VBox rightBox = new VBox();

		rightBox.getChildren().addAll(createButton,groupListView);
		rightBox.setPadding(new Insets(5.0));
		BorderPane borderPane = new BorderPane();

		primaryStage.setWidth(1200);
		primaryStage.setHeight(800);

		borderPane.setRight(rightBox);
		borderPane.setCenter(tabPane);

		tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>(){

			@Override
			public void changed(final ObservableValue<? extends Tab> arg0,
					final Tab oldTab, final Tab newTab) {
				if(oldTab != null)
				{
					oldTab.setStyle("-fx-background-color: #fff");
				}
				if(newTab != null)
				{

					newTab.setStyle("-fx-background-color:#AAECFF");
				}
			}
		});

		groupListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(final MouseEvent click) {

				if(click.getClickCount() == 2)
				{
					final GroupModel group = groupListView.getSelectionModel().getSelectedItem();

					for(Tab tab : tabPane.getTabs())
					{
						ChatTab chatTab = (ChatTab)tab;

						if(chatTab.getGroup().equals(group))
						{
							tabPane.getSelectionModel().select(tab);
							return;
						}
					}
					controller.joinGroup(group);
				}
			}
		});

		primaryStage.setTitle("Simple Chat Server - "+controller.getCurrentUser().getUsername());
		Scene mainScene = new Scene(borderPane);
		mainScene.getStylesheets().add(styleSheetSource);

		primaryStage.setScene(mainScene);
	}

	private void lostConnection()
	{
		final AlertDialog dialog = new AlertDialog(primaryStage,"The connection to the server has been lost.");

		dialog.setOnConfirmHandler(new EventHandler<ActionEvent>(){

			@Override
			public void handle(final ActionEvent event) {
				primaryStage.close();
				dialog.close();
				Platform.exit();
			}
		});
		dialog.show();
	}



	private void showCreateGroupBox()
	{
		final Stage dialog = new Stage();
		dialog.initStyle(StageStyle.UTILITY);
		dialog.setTitle("Create New Group");
		final Label groupNameLabel = new Label("Group Name:");
		final TextField groupName = new TextField();
		final Button createButton = new Button("Create");

		createButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(final ActionEvent arg0) {


				if(groupName.getText().isEmpty())
				{
					displayError("Group Name cannot be empty");
					return;
				}
				controller.createGroup(groupName.getText());
				dialog.close();
			}
		});

		groupName.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(final KeyEvent ke)
			{
				if (ke.getCode().equals(KeyCode.ENTER))
				{
					createButton.fire();
				}
			}
		});

		HBox box = new HBox();
		box.getChildren().addAll(groupNameLabel,groupName,createButton);
		box.setPadding(new Insets(5, 5, 5, 5));
		Scene scene = new Scene(box);
		scene.getStylesheets().add(styleSheetSource);
		dialog.setScene(scene);

		dialog.setWidth(400);
		dialog.setHeight(100);
		dialog.show();

	}

	/**
	 * Return the ChatTab associated with the given group
	 * @param group Group to search for in chat tab
	 * @return ChatTab if found, null otherwise
	 * @precondition Group has ChatTab in list
	 * @postcondition ChatTab returned
	 */
	private ChatTab findChatTabByGroup(final GroupModel group)
	{
		for(Tab tab : tabPane.getTabs()){

			if( ((ChatTab)tab).getGroup().equals(group) )
			{
				return ((ChatTab)tab);
			}
		}

		return null;
	}

	/**
	 * Add a message to a chat tab
	 * @param message Message Model containing message, and group to add to
	 * @precondition MessageModel is not null
	 * @postcondition ChatTab associated with group in MessageModel will contain new message
	 */
	private void addMessage(final MessageModel message)
	{
		ChatTab tab = findChatTabByGroup(message.getGroup());

		if(tab != null)
		{
			tab.addMessage(message);
		}
	}

	/**
	 * Add a message to a chat tab
	 * @param group Group to add message to
	 * @param message Message to add to group
	 * @precondition GroupModel is not null
	 * @postcondition ChatTab associated with group will contain new message
	 */
	private void addMessage(final GroupModel group, final String message)
	{
		ChatTab tab = findChatTabByGroup(group);

		if(tab != null)
		{
			tab.addMessage(message);
		}
	}

	/**
	 * Call to remove a group when it has been deleted
	 * @param group Group to remove from the system
	 * @precondition Group exists in the system already
	 * @postcondition Group will be deleted from the system
	 */
	private void removeGroup(final GroupModel group)
	{
		ObservableList<Tab> tabs = tabPane.getTabs();

		for(Iterator<Tab> it = tabs.iterator(); it.hasNext();)
		{
			ChatTab chatTab = (ChatTab)it.next();

			if(chatTab.getGroup().equals(group))
			{
				it.remove();
			}
		}

		groupListView.removeItem(group);

	}

	/**
	 * Call to add a recently created group
	 * @param group Group to add
	 * @precondition Group does not already exist
	 * @postcondition Group will exist in system
	 */
	private void addGroup(final GroupModel group)
	{
		final ChatTab tab;
		if(!group.isModerator(controller.getCurrentUser()))
		{
			tab = new ChatTab(group);
		}
		else
		{
			//Create new ChatTab and pass in new ConfirmDialog to delete group
			final ConfirmDialog dialog = new ConfirmDialog(primaryStage
					,"Are you sure you want to delete this group?");

			dialog.setOnConfirmHandler(new EventHandler<ActionEvent>(){

				@Override
				public void handle(final ActionEvent arg0) {
					dialog.close();
					controller.deleteGroup(group);


				}
			});
			dialog.setOnCancelHandler(new EventHandler<ActionEvent>(){
				@Override
				public void handle(final ActionEvent arg0) {
					dialog.close();
				}
			});
			tab = new ChatTab(group,dialog);
		}


		tab.setOnClosed(new EventHandler<Event>(){

			@Override
			public void handle(final Event arg0) {
				controller.leaveGroup(tab.getGroup());
				groupsActive.remove(tab.getGroup());
			}
		});

		tab.setOnSendListener(new EventHandler<ActionEvent>(){

			@Override
			public void handle(final ActionEvent event) {
				String text = tab.getAndClearText();

				if(!text.trim().isEmpty())
				{
					Logger.getInstance().write("Sending message: "+text+", for group: "+tab.getGroup().getId());
					controller.sendMessage(text,tab.getGroup());
				}
				else
				{
					displayError("Message cannot be empty.");
				}
			}
		});

		tab.setOnDragDrop(new EventHandler<DragEvent>() {
			@Override
			public void handle(final DragEvent event) {
				Dragboard db = event.getDragboard();
				boolean success = false;
				if (db.hasFiles()) {
					success = true;
					for (File file:db.getFiles()) {
						if(file.exists())
						{
							controller.sendFile(file, group);
						}
					}
				}
				event.setDropCompleted(success);
				event.consume();
			}
		});

		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				tabPane.getTabs().add(tab);
			}
		});
		groupsActive.add(group);
	}

	/**
	 * Class used to run responses from server connection.
	 * @author kyle
	 *
	 */
	private class RequestListener implements IResponseListener
	{
		@Override
		public void onConnection() {
			Logger.getInstance().write("Connection established");
			goToLogin();
		}

		@Override
		public void onLostConnection()
		{
			Platform.runLater(new Runnable(){
				@Override
				public void run() {
					lostConnection();
				}
			});
		}

		@Override
		public void onLogin(final UserModel user) {
			Logger.getInstance().write("Succesful login");
			controller.requestGroupList();
		}

		@Override
		public void onLogout(final UserModel user) {
		}

		@Override
		public void onGroupJoined(final GroupModel group) {
			Logger.getInstance().write("Joining group: "+group.getGroupName());
			Platform.runLater(new Runnable(){
				@Override
				public void run() {

					addGroup(group);

				}
			});



		}

		@Override
		public void onGroupLeft(final GroupModel group) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onNewGroup(final GroupModel group) {
			Platform.runLater(new Runnable(){
				@Override
				public void run() {
					groupListView.addItem(group);

				}
			});


		}

		@Override
		public void onGroupListReceived(final List<GroupModel> groupList) {
			Platform.runLater(new Runnable(){
				@Override
				public void run() {
					showMainView(groupList);

				}
			});


		}

		@Override
		public void onGroupCreated(final GroupModel group) {
			Platform.runLater(new Runnable(){
				@Override
				public void run() {
					addGroup(group);
				}
			});
		}

		@Override
		public void onGroupDeleted(final GroupModel group) {
			Platform.runLater(new Runnable(){
				@Override
				public void run() {
					removeGroup(group);
					displayError("The Group \""+group.getGroupName()+"\" has been deleted by the creator. Sorry for the inconvenience.");
				}
			});

		}

		@Override
		public void onMessageReceived(final MessageModel message) {

			Platform.runLater(new Runnable(){
				@Override
				public void run() {
					addMessage(message);

				}
			});
		}

		@Override
		public void onUserJoinedGroup(final GroupModel group, final UserModel user) {
			Platform.runLater(new Runnable(){
				@Override
				public void run() {
					addMessage(group, user.getUsername()+" has entered the group.\n");
				}
			});
		}

		@Override
		public void onUserLeftGroup(final GroupModel group, final UserModel user)
		{
			Platform.runLater(new Runnable(){
				@Override
				public void run() {
					addMessage(group, user.getUsername()+" has left the group.\n");
				}
			});
		}

		@Override
		public void onError(final String error) {
			Platform.runLater(new Runnable(){
				@Override
				public void run() {
					displayError(error);
				}
			});
			Logger.getInstance().write("ERROR: "+error);
		}

		@Override
		public void onFileMessageReceived(final FileMessageResponse fileResponse) {

			final String tempDir = "temp";

			String name = fileResponse.getFileMessage().getFileName();

			byte[] contents = fileResponse.getFileMessage().getContents();

			final File file = new File(name);

			try {
				file.createNewFile();
				FileOutputStream stream = new FileOutputStream(file);
				stream.write(contents);
				stream.close();

				final ChatTab tab = findChatTabByGroup(fileResponse.getFileMessage().getGroup());

				if(tab != null)
				{
					Platform.runLater(new Runnable(){
						@Override
						public void run() {
							tab.addFileMessage(file,fileResponse.getFileMessage());
						}
					});

				}

			}catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}



		}

	}

}
