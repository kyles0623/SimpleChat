package edu.fau.simplechat.gui;

import java.io.File;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import edu.fau.simplechat.model.GroupModel;
import edu.fau.simplechat.model.MessageModel;

/** Chat client UI */
public class ChatTab extends Tab {

	private final TextField messageInput;

	private final TextArea messageTextArea;
	private static final double padding = 5;
	private static final double width = 900-padding;
	private static final double height = 798-padding; static final double btn_width = 100;
	private final HBox bottomBox;
	private final BorderPane panel;
	private final String title;
	private final GroupModel group;
	private final Button sendButton;
	private final Button deleteButton;

	private final VBox topBox;

	private final ArrayList<MessageModel> messages;
	public ChatTab(final GroupModel group)
	{
		messages = new ArrayList<>();
		panel = new BorderPane();
		panel.setPadding(new Insets(padding));

		topBox = new VBox();
		this.group = group;
		messageInput = new TextField();
		sendButton = new Button("Send");
		deleteButton = new Button("Delete Group");
		deleteButton.setVisible(false);

		this.setContent(panel);
		this.title = group.getGroupName();
		bottomBox = new HBox();
		topBox.getChildren().add(deleteButton);
		HBox.setHgrow(messageInput,  Priority.ALWAYS);
		messageTextArea = new TextArea();
		messageTextArea.setEditable(false);


		messageInput.setOnKeyPressed(new EventHandler<KeyEvent>()
				{
			@Override
			public void handle(final KeyEvent ke)
			{
				if (ke.getCode().equals(KeyCode.ENTER))
				{
					sendButton.fire();
				}
			}
				});

		messageInput.setMaxWidth(messageInput.getMinWidth());
		messageInput.setId("messageInput");
		bottomBox.getChildren().addAll(messageInput,sendButton);


		topBox.setPadding(new Insets(10,0,10,0));
		topBox.setAlignment(Pos.BASELINE_RIGHT);
		panel.setCenter(messageTextArea);
		panel.setBottom(bottomBox);
		panel.setTop(topBox);

		this.setText(title);
		setDragDrop();

	}

	private void setDragDrop()
	{
		panel.setOnDragOver(new EventHandler<DragEvent>(){

			@Override
			public void handle(final DragEvent event) {

				Dragboard db = event.getDragboard();
				if (db.hasFiles()) {
					event.acceptTransferModes(TransferMode.COPY);
				} else {
					event.consume();
				}
			}
		});

		panel.setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(final DragEvent event) {
				Dragboard db = event.getDragboard();
				boolean success = false;
				if (db.hasFiles()) {
					success = true;
					String filePath = "";
					for (File file:db.getFiles()) {
						if(file.exists())
						{
							filePath += file.getAbsolutePath();
						}
					}

					messageInput.setText(filePath);
				}
				event.setDropCompleted(success);
				event.consume();
			}
		});
	}

	public ChatTab(final GroupModel group, final ConfirmDialog deleteConfirmDialog)
	{
		this(group);
		deleteButton.setVisible(true);
		deleteButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(final ActionEvent arg0) {
				deleteConfirmDialog.show();

			}


		});
	}

	public void addMessage(final String line)
	{
		if(!this.isSelected())
		{
			this.setStyle("-fx-background-color: #09B6FF");
		}
		messageTextArea.appendText(line);
	}

	public String getAndClearText()
	{
		String text = messageInput.getText();
		messageInput.clear();
		return text;
	}

	public void setOnSendListener(final EventHandler<ActionEvent> event)
	{
		sendButton.setOnAction(event);
	}

	public GroupModel getGroup()
	{
		return group;
	}


}