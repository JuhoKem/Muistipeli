package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
/*
 * Tässä on valmis popup-luokka, jota voi uudelleenkäyttää muuttamalla sitä tarpeiden mukaan.
 */

public class popup {

	public static void display(Stage primaryStage, Scene loginScene) {
		// TODO Auto-generated method stub
		Stage popup = new Stage();
		popup.setTitle("Rekisteröinti");
		popup.initModality(Modality.APPLICATION_MODAL);
		popup.setMinWidth(350);
		popup.setMinHeight(150);

		Label label = new Label();
		label.setText("     Rekisteröinti onnistui! \n Ole hyvä ja kirjaudu sisään \n");
		Button btn = new Button("Ok");

		// toiminallisuus Ok-napille
		btn.setOnAction(e -> {
			popup.close();
			primaryStage.setScene(loginScene); // siirtymä login-ikkunaan
		});

		VBox layout = new VBox();
		layout.getChildren().addAll(label, btn);
		layout.setAlignment(Pos.CENTER);

		Scene scene_popup = new Scene(layout);
		popup.setScene(scene_popup);
		popup.showAndWait();

	}
	
	public static void displayPasswordUpdate(Stage primaryStage, Scene loginScene) {
		// TODO Auto-generated method stub
		Stage popup = new Stage();
		popup.setTitle("Salasanan palautus:");
		popup.initModality(Modality.APPLICATION_MODAL);
		popup.setMinWidth(350);
		popup.setMinHeight(150);

		Label label = new Label();
		label.setText("   Salasanan päivitys onnistui! \n    Ole hyvä ja kirjaudu sisään \n");
		Button btn = new Button("Ok");

		// toiminallisuus Ok-napille
		btn.setOnAction(e -> {
			popup.close();
			primaryStage.setScene(loginScene); // siirtymä login-ikkunaan
		});

		VBox layout = new VBox();
		layout.getChildren().addAll(label, btn);
		layout.setAlignment(Pos.CENTER);

		Scene scene_popup = new Scene(layout);
		popup.setScene(scene_popup);
		popup.showAndWait();

	}

}
