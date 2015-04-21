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
import javafx.scene.layout.HBox;
import edu.fau.simplechat.controller.ChatController;

/**
 * A LoginBox is an HBox with all of the
 * necessary components to have the user login.
 * @author kyle
 *
 */
public class LoginBox extends HBox {

	/**
	 * Label for the username
	 */
	private final Label usernameLabel;

	/**
	 * Text Input for the username
	 */
	private final TextField usernameInput;

	/**
	 * Button to login
	 */
	private final Button loginButton;

	/**
	 * ChatController connected to server
	 */
	private final ChatController controller;

	/**
	 * Initialize and display the Login Box
	 * @param chatController ChatController connected to server
	 */
	public LoginBox(final ChatController chatController)
	{
		super(8);

		this.setPadding(new Insets(30,10,10,10));

		controller = chatController;
		usernameLabel = new Label("username: ");
		usernameInput = new TextField();
		usernameLabel.setAlignment(Pos.BOTTOM_CENTER);

		loginButton = new Button("Log in");


		usernameInput.setOnKeyPressed(new EventHandler<KeyEvent>(){

			@Override
			public void handle(final KeyEvent key) {
				if(key.getCode().equals(KeyCode.ENTER))
				{
					loginButton.fire();
				}
			}


		});


		getChildren().addAll(usernameLabel,usernameInput,loginButton);

		loginButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(final ActionEvent e) {
				controller.login(usernameInput.getText());
			}
		});


	}
}
