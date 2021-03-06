package edu.fau.simplechat.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Custom Alert Dialog.
 * Alerts the user of a message.
 * 
 * Source: http://tech.chitgoks.com/2013/06/19/how-to-create-alert-dialog-like-joptionpane-in-java-fx-2/
 * @author kyle
 *
 */
public class ConfirmDialog extends Stage {


	/**
	 * Default width of confirm dialog ox
	 */
	private final double WIDTH_DEFAULT = 300 ;

	/**
	 * Confirm Button
	 */
	private final Button confirm = new Button("Confirm");

	/**
	 * Cancel button
	 */
	private final Button cancel = new Button("Cancel");

	/**
	 * Initialize Confirm Dialog with owner and message
	 * @param owner Owner of dialog box
	 * @param msg Message to be displayed
	 */
	public ConfirmDialog(final Stage owner, final String msg) {
		setResizable(false);
		initModality(Modality.APPLICATION_MODAL);
		initStyle(StageStyle.TRANSPARENT);

		Label label = new Label(msg);
		label.setWrapText(true);
		label.setGraphicTextGap(20);
		confirm.setMinWidth(100);
		cancel.setMinWidth(100);
		BorderPane borderPane = new BorderPane();
		borderPane.getStylesheets().add(getClass().getResource("/style/alert.css").toExternalForm());
		borderPane.setTop(label);

		HBox hbox2 = new HBox(40);
		hbox2.setAlignment(Pos.CENTER);
		hbox2.getChildren().addAll(confirm,cancel);
		borderPane.setBottom(hbox2);

		// calculate width of string
		final Text text = new Text(msg);
		text.snapshot(null, null);
		// + 20 because there is padding 10 left and right
		double width = text.getLayoutBounds().getWidth() + 40;

		if (width < WIDTH_DEFAULT) {
			width = WIDTH_DEFAULT;
		}

		double height = 200;

		final Scene scene = new Scene(borderPane, width, height);
		scene.setFill(Color.TRANSPARENT);
		setScene(scene);

		// make sure this stage is centered on top of its owner
		setX(owner.getX() + (owner.getWidth() / 2 - width / 2));
		setY(owner.getY() + (owner.getHeight() / 2 - height / 2));
	}


	/**
	 * Set what happens when the user clicks confirm
	 * @param eventHandler EventHandler to handle confirmation event
	 * @precondition eventHandler is not null
	 * @postcondition eventHandler will execute when Confirm button is clicked
	 */
	public void setOnConfirmHandler(final EventHandler<ActionEvent> eventHandler)
	{
		confirm.setOnAction(eventHandler);
	}

	/**
	 * Set what happens when the user clicks cancel
	 * @param eventHandler EventHandler to handle cancellation event
	 * @precondition eventHandler is not null
	 * @postcondition eventHandler will execute when Cancel button is clicked
	 */
	public void setOnCancelHandler(final EventHandler<ActionEvent> eventHandler)
	{
		cancel.setOnAction(eventHandler);
	}
}