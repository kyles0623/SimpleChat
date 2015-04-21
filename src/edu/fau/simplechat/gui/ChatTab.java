package edu.fau.simplechat.gui;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
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
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import edu.fau.simplechat.logger.Logger;
import edu.fau.simplechat.model.FileMessageModel;
import edu.fau.simplechat.model.GroupModel;
import edu.fau.simplechat.model.MessageModel;

/**
 * ChatTabs are Tabs that contain all of the GUI elements
 * necessary to communicate with users in  a group.
 * Elements include a textfield to send messages, a
 * webview to view all messages, and a send button to send messages.
 * @author kyle
 *
 */
public class ChatTab extends Tab {

	/**
	 * TextField used to retrieve message user wants to send
	 */
	private final TextField messageInput;

	/**
	 * Displays all current messages in group
	 */
	private final WebView webView;

	/**
	 * Engine to add and remove content from webView
	 */
	private final WebEngine messageAreaHTML;

	/**
	 * Constant padding value
	 */
	private static final double padding = 5;

	/**
	 * Bottom Box in border pane containing
	 * messageInput and send button
	 */
	private final HBox bottomBox;

	/**
	 * Border Pane to organize elements in tab
	 */
	private final BorderPane panel;

	/**
	 * title of tab
	 */
	private final String title;

	/**
	 *Group associated with tab
	 */
	private final GroupModel group;

	/**
	 * Button to send messages
	 */
	private final Button sendButton;

	/**
	 * Button to delete group, if user owns it
	 */
	private final Button deleteButton;

	/**
	 * Constant format for all dates
	 */
	private static final DateFormat format = DateFormat.getTimeInstance(DateFormat.MEDIUM);

	/**
	 * List containing content
	 */
	private final ArrayList<String> content;

	/**
	 * Number of unread messages to be displayed in tab area
	 */
	private int numNewMessages = 0;

	/**
	 * Top Box containing delete button, if necessary
	 */
	private final VBox topBox;

	/**
	 * Initialize Chat Tab with group model
	 * @param group Group associated with ChatTab
	 */
	public ChatTab(final GroupModel group)
	{
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
		webView = new WebView();
		webView.setContextMenuEnabled(false);
		webView.setOnDragDropped(null);
		messageAreaHTML = webView.getEngine();
		content = new ArrayList<>();

		loadWebViewContent();

		webView.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
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
		panel.setCenter(webView);
		panel.setBottom(bottomBox);
		panel.setTop(topBox);

		this.setText(title);

	}

	/**
	 * Initialize Chat Tab with GroupModel and a confirm dialog
	 * to show when user clicks delete. This constructor is to be
	 * used if the delete button is supposed to be shown
	 * @param group Group associated with Chat Tab
	 * @param deleteConfirmDialog ConfirmDialog showing what happens when user clicks delete
	 */
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

	/**
	 * Load Content into WebView
	 * @precondition none
	 * @postcondition WebView will contain content
	 */
	private void loadWebViewContent()
	{

		//This is used so when a new message is added
		//The page scrolls to the bottom of the message area
		//
		//
		webView.setStyle("-fx-background-color: #cccccc; -fx-border-color: #00a7c8");
		StringBuilder topHTML= new StringBuilder().append("<html>");
		topHTML.append("<head>");
		topHTML.append("   <script language=\"javascript\" type=\"text/javascript\">");
		topHTML.append("       function toBottom(){");
		topHTML.append("           window.scrollTo(0, document.body.scrollHeight);");
		topHTML.append("       }");
		topHTML.append("   </script>");
		topHTML.append("</head>");
		topHTML.append("<body onload='toBottom()'>");

		content.add(topHTML.toString());
		StringBuilder bottomHTML= new StringBuilder().append("</body></html>");
		content.add(bottomHTML.toString());

	}

	/**
	 * Set what happens when a user drags a file onto the tab area
	 * @param event Event to occur when the user drags onto the area
	 * @precondition Panel has been initialized
	 * @postcondition When a user drags a file onto the tab area, this event will occur
	 */
	public void setOnDragDrop(final EventHandler<DragEvent> event)
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

		panel.setOnDragDropped(event);
	}

	/**
	 * Retrieve the content as a single string
	 * @return String representation of all messages
	 * @precondition None
	 * @postcondition none
	 */
	private String getMessageContent()
	{
		StringBuffer buffer = new StringBuffer();

		for(String n : content)
		{
			buffer.append(n);
		}

		return buffer.toString();

	}

	/**
	 * Add a new message to the webview
	 * @param message  Message to add
	 * @precondition message is not null
	 * @postcondition WebView will be updated with new content
	 */
	private void updateMessages(final String message)
	{
		//Add to size-2 to insert between Header HTML info,
		//and footer HTML info
		content.add(content.size()-2, message);

		//Reload Message Area with updated content
		messageAreaHTML.loadContent(getMessageContent());


		if(!isSelected())
		{
			numNewMessages++;
			setText(title+"("+numNewMessages+")");
			setStyle("-fx-background-color: #09B6FF");
		}
	}

	/**
	 * Add a message to the messsage area
	 * @param line Message to add
	 * @precondition line is not null
	 * @postcondition Messages will be updated with new message
	 */
	public void addMessage(final String line)
	{
		Logger.getInstance().write("appending message :"+line);
		String newLine = "<span style='color:green'>"+line+"</span></br>";
		updateMessages(newLine);


	}

	/**
	 * Add a message to the message Area
	 * @param message MessageModel containing message
	 * @precondition message is not null
	 * @postcondition Messages will be updated with new message
	 */
	public void addMessage(final MessageModel message)
	{

		String date = format.format(message.getTimeSent());
		String user = message.getSendingUser().getUsername();
		String text = stringToHTMLString(message.getMessage());

		String html = "<span style='font-family:Calibri;font-size:18px'><span>"+date+"</span>"
				+"<span style='color:blue'> "+user+": </span>"
				+"<span>"+text+"</span></span></br>";

		updateMessages(html);
	}

	/**
	 * Add a file to the message area
	 * @param file File to be added to message area
	 * @param fileMessage Original File Message
	 * @precondition file is an image type (.jpeg, .gif, .png, etc.)
	 * @postcondition file will be displayed in message aera
	 */
	public void addFileMessage(final File file, final FileMessageModel fileMessage) {
		if(file.exists())
		{
			String date = format.format(fileMessage.getTimeSent());
			String user = fileMessage.getSendingUser().getUsername();
			String imgHtml = "<img src=\""+file.toURI()+"\" style=\"max-width:350px;max-height=350px\" /><br />";

			String html = "<span style='font-family:Calibri;font-size:18px'><span>"+date+"</span>"
					+"<span style='color:blue'> "+user+": </span>"
					+"<span><br/>"+imgHtml+"</span></span></br>";

			this.updateMessages(html);
		}

	}

	/**
	 * Set the current tab as the selected tab
	 * 
	 * @precondition none
	 * @postcondition Tab color will change to indicated its selected
	 */
	public void setAsSelectedTab()
	{
		numNewMessages = 0;
		setStyle("-fx-background-color:#AAECFF");
		this.setText(title);
	}

	/**
	 * Set the current tab as an unselected tab
	 * 
	 * @precondition none
	 * @postcondition Tab color will change to indicate unselected tab
	 */
	public void resetSelection()
	{
		setStyle("-fx-background-color: #fff");
	}

	/**
	 * Retrive the text typed by the user and then clear it fmo the GUI
	 * @return message typed by user
	 * @precondition none
	 * @postcondition textfield will be cleared
	 */
	public String getAndClearText()
	{
		String text = messageInput.getText();
		messageInput.clear();
		return text;
	}

	/**
	 * Set what happens when the user clicks the send button
	 * @param event Event to happen when the user clicks the send button
	 * @precondition event is not null
	 * @postcondition The event will be triggered when the user clicks send
	 */
	public void setOnSendListener(final EventHandler<ActionEvent> event)
	{
		sendButton.setOnAction(event);
	}

	/**
	 * Retrieve the group associated with the chat tab
	 * @return Group associated with chat tab
	 * @precondition none
	 * @postcondition none
	 */
	public GroupModel getGroup()
	{
		return group;
	}

	/**
	 * Helper function to convert string to escaped string
	 * @param string Message to convert to escaped string
	 * @return Escaped version of original string
	 * @precondition none
	 * @postcondition none
	 */
	public static String stringToHTMLString(final String string) {
		StringBuffer sb = new StringBuffer(string.length());
		// true if last char was blank
		boolean lastWasBlankChar = false;
		int len = string.length();
		char c;

		for (int i = 0; i < len; i++)
		{
			c = string.charAt(i);
			if (c == ' ') {
				// blank gets extra work,
				// this solves the problem you get if you replace all
				// blanks with &nbsp;, if you do that you loss
				// word breaking
				if (lastWasBlankChar) {
					lastWasBlankChar = false;
					sb.append("&nbsp;");
				}
				else {
					lastWasBlankChar = true;
					sb.append(' ');
				}
			}
			else {
				lastWasBlankChar = false;
				//
				// HTML Special Chars
				if (c == '"') {
					sb.append("&quot;");
				} else if (c == '&') {
					sb.append("&amp;");
				} else if (c == '<') {
					sb.append("&lt;");
				} else if (c == '>') {
					sb.append("&gt;");
				} else if (c == '\n') {
					// Handle Newline
					sb.append("&lt;br/&gt;");
				} else {
					int ci = 0xffff & c;
					if (ci < 160 ) {
						// nothing special only 7 Bit
						sb.append(c);
					} else {
						// Not 7 Bit use the unicode system
						sb.append("&#");
						sb.append(new Integer(ci).toString());
						sb.append(';');
					}
				}
			}
		}
		return sb.toString();
	}




}