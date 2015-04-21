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
public class AlertDialog extends Stage {


	private final double WIDTH_DEFAULT = 300;

	private final Button button;

	/**
	 * Initialize the AlertDialog with the owner and message to display
	 * @param owner Stage owning this AlertDialog
	 * @param msg Message to display
	 */
	public AlertDialog(final Stage owner, final String msg) {
		setResizable(false);
		initModality(Modality.APPLICATION_MODAL);
		initStyle(StageStyle.TRANSPARENT);

		Label label = new Label(msg);
		label.setWrapText(true);
		label.setGraphicTextGap(20);

		button = new Button("OK");
		button.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(final ActionEvent arg0) {
				AlertDialog.this.close();
			}
		});

		button.setMinWidth(100);
		BorderPane borderPane = new BorderPane();
		borderPane.getStylesheets().add(getClass().getResource("/style/alert.css").toExternalForm());
		borderPane.setTop(label);

		HBox hbox2 = new HBox(10);
		hbox2.setAlignment(Pos.CENTER);
		hbox2.getChildren().add(button);
		borderPane.setBottom(hbox2);

		// calculate width of string
		final Text text = new Text(msg);
		text.snapshot(null, null);
		// + 20 because there is padding 10 left and right
		double width = ( text.getLayoutBounds().getWidth() + 40);

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
	 * Set the handler when the user clicks the ok button.
	 * Default implementation closes dialog.
	 * @param handler Handler to handle clicking confirm
	 * @precondition none
	 * @postcondition AlertDialog will do action on confirm
	 */
	public void setOnConfirmHandler(final EventHandler<ActionEvent> handler)
	{
		button.setOnAction(handler);
	}

}