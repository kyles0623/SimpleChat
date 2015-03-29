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

public class LoginBox extends HBox {

	private Label usernameLabel;
	
	private TextField usernameInput;
	
	private Button loginButton;
	
	private ChatController controller;
	
	public LoginBox(ChatController c)
	{
		super(8);
		
		this.setPadding(new Insets(30,10,10,10));
		
		controller = c;
		usernameLabel = new Label("username: ");
		usernameInput = new TextField();
		usernameLabel.setAlignment(Pos.BOTTOM_CENTER);
		
		loginButton = new Button("Log in");
		
		
		usernameInput.setOnKeyPressed(new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent key) {
				if(key.getCode().equals(KeyCode.ENTER))
				{
					loginButton.fire();
				}
			}
			
			
		});
		
		
		getChildren().addAll(usernameLabel,usernameInput,loginButton);
		
		loginButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	controller.login(usernameInput.getText());
		    }
		});
		
		
	}
}
