package edu.fau.simplechat.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import edu.fau.simplechat.controller.ChatController;

/**
 * A ConnectionPane is a GridPane with all
 * of the necessary components to retrieve
 * server connection info from user.
 * @author kyle
 *
 */
public class ConnectionPane extends GridPane {

	/**
	 * Label for Host
	 */
	private final Label hostLabel;

	/**
	 * Label for Port
	 */
	private final Label portLabel;

	/**
	 * Text Input Field for Host
	 */
	private final TextField hostInput;

	/**
	 * Text Input Field for port
	 */
	private final TextField portInput;

	/**
	 * Title of Pane
	 */
	private final Label title;

	/**
	 * ChatController associated with current chat system.
	 * Used to communicate to server
	 */
	private final ChatController controller;

	/**
	 * Connection button to connect to server
	 */
	private final Button button;

	/**
	 * Initliaze ConnectionPane with chatController
	 * @param controller ChatController associated with chat system
	 */
	public ConnectionPane(final ChatController controller)
	{
		super();
		setHgap(10);
		setVgap(10);
		setPadding(new Insets(-10,0,0,-10));
		this.controller = controller;

		this.setAlignment(Pos.CENTER);

		hostLabel = new Label("Host ");
		portLabel = new Label("Port ");
		button = new Button("Connnect");
		title = new Label("Simple Chat");


		hostInput = new TextField();
		portInput = new TextField();

		title.setId("title");
		hostLabel.setAlignment(Pos.CENTER_RIGHT);
		hostInput.setOnKeyPressed(new EventHandler<KeyEvent>(){

			@Override
			public void handle(final KeyEvent key) {
				if(key.getCode().equals(KeyCode.ENTER))
				{
					portInput.requestFocus();
				}
			}
		});

		portInput.setOnKeyPressed(new EventHandler<KeyEvent>(){

			@Override
			public void handle(final KeyEvent key) {
				if(key.getCode().equals(KeyCode.ENTER))
				{
					button.fire();
				}
			}
		});






		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(final ActionEvent e) {

				try
				{
					controller.connect(hostInput.getText(), Integer.parseInt(portInput.getText()));
				}
				catch(NumberFormatException exc)
				{
					controller.onError("Please make sure your host and port are of the proper format.");
				}
			}
		});

		add(title,1,0,1,1);
		add(hostLabel,1,1);
		add(portLabel,1,2);

		add(hostInput,2,1);
		add(portInput,2,2);

		add(button,2,3);
	}


}
