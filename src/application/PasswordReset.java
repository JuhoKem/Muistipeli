package application;

import application.database.Database;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PasswordReset {

	public static Scene passResetScene(Stage primaryStage, Scene loginScene, String user) {

		ImageView tausta = new ImageView(new Image("application/resources/tausta7.jpeg"));
		tausta.setFitWidth(1200);
		tausta.setFitHeight(800);
		
		GridPane grid = new GridPane(); // gridlayout luottaa rivi-sarake layoutiin, gridpane grid toimii pohjana
		grid.getChildren().add(tausta);
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(50, 50, 50, 50));

		grid.setGridLinesVisible(false);
		
		StackPane keskus = new StackPane();
		keskus.getChildren().addAll(tausta, grid);

		Scene scene = new Scene(keskus, 1200, 800); // ikkunan koko
		scene.getStylesheets().add("application/application.css"); // css-tiedosto
		Text rekisteroidy = new Text(" Uusi salasana ");
		rekisteroidy.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		Label salasanateksti = new Label(" Salasana ");
		PasswordField salasanabox = new PasswordField();

		grid.add(rekisteroidy, 1, 0, 1, 1);
		grid.add(salasanateksti, 0, 4);
		grid.add(salasanabox, 1, 4);

		Button tal = new Button(" Tallenna ");
		tal.setOnAction(e -> {
			Database.updatePassword(user, salasanabox.getText());
			popup.displayPasswordUpdate(primaryStage, loginScene);
		});
		Button palaa = new Button(" Palaa ");
		palaa.setOnAction(e -> {
			primaryStage.setScene(loginScene);
		});
		HBox hbox = new HBox(50);
		hbox.setAlignment(Pos.BOTTOM_CENTER); // nappien sijainti ikkunassa
		hbox.getChildren().add(tal);
		hbox.getChildren().add(palaa);
		grid.add(tal, 1, 9);
		grid.add(palaa, 2, 9);

		return scene;
	}

}
