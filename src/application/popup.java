package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
/*
 * T�ss� on valmis popup-luokka, jota voi uudelleenk�ytt�� muuttamalla sit� tarpeiden mukaan.
 */

public class popup {

	public static void display(Stage primaryStage, Scene loginScene) {
		// TODO Auto-generated method stub
		Stage popup = new Stage();
		popup.setTitle("Rekister�inti");
		popup.initModality(Modality.APPLICATION_MODAL);
		popup.setMinWidth(350);
		popup.setMinHeight(150);

		Label label = new Label();
		label.setText("     Rekister�inti onnistui! \n Ole hyv� ja kirjaudu sis��n \n");
		Button btn = new Button("Ok");

		// toiminallisuus Ok-napille
		btn.setOnAction(e -> {
			popup.close();
			primaryStage.setScene(loginScene); // siirtym� login-ikkunaan
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
		label.setText("   Salasanan p�ivitys onnistui! \n    Ole hyv� ja kirjaudu sis��n \n");
		Button btn = new Button("Ok");

		// toiminallisuus Ok-napille
		btn.setOnAction(e -> {
			popup.close();
			primaryStage.setScene(loginScene); // siirtym� login-ikkunaan
		});

		VBox layout = new VBox();
		layout.getChildren().addAll(label, btn);
		layout.setAlignment(Pos.CENTER);

		Scene scene_popup = new Scene(layout);
		popup.setScene(scene_popup);
		popup.showAndWait();

	}

}
