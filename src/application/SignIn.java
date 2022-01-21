package application;

import application.database.Database;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SignIn {

	public static Scene signInScene(Stage primaryStage, Scene loginScene) {
		
		ImageView tausta = new ImageView(new Image("application/resources/tausta7.jpeg"));
		StackPane keskus = new StackPane();

		GridPane grid = new GridPane(); // gridlayout luottaa rivi-sarake layoutiin, gridpane grid toimii pohjana
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(50, 50, 50, 50));

		grid.setGridLinesVisible(false);
		
		keskus.getChildren().addAll(tausta, grid);

		Scene scene = new Scene(keskus, 1200, 800); // ikkunan koko
		scene.getStylesheets().add("application/application.css"); // css-tiedosto
		Text rekisteroidy = new Text(" Rekisteröidy ");
		rekisteroidy.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		
		Label kayttajanimiteksti = new Label(" Käyttäjätunnus: ");
		TextField kayttajanimibox = new TextField();
		
		Label salasanateksti = new Label(" Salasana: ");
		PasswordField salasanabox = new PasswordField();
		
		Label lisakysymyslabel = new Label(" Lisäkysymys: ");
		Text lisakysymys = new Text(" Mikä on lempikaljasi?");
		lisakysymys.setFont(Font.font("Tahoma", FontWeight.LIGHT, 25));
		
		Label vastaus = new Label(" Vastaus:");
		TextField vastausbox = new TextField();
		Label actionTarget = new Label();

		grid.add(rekisteroidy, 1, 0, 1, 1);
		grid.add(kayttajanimiteksti, 0, 3); // määritellään jokaiselle komponentille rivi ja sarake
		grid.add(kayttajanimibox, 1, 3);
		grid.add(salasanateksti, 0, 4);
		grid.add(salasanabox, 1, 4);
		grid.add(lisakysymys, 1, 6);
		grid.add(lisakysymyslabel, 0, 6);
		grid.add(vastaus, 0, 7);
		grid.add(vastausbox, 1, 7);
		grid.add(actionTarget, 1, 8);
		
		//Siirretään tekstejä
		GridPane.setHalignment(kayttajanimiteksti, HPos.RIGHT);
		GridPane.setHalignment(salasanateksti, HPos.RIGHT);
		GridPane.setHalignment(lisakysymyslabel,  HPos.RIGHT);
		GridPane.setHalignment(vastaus, HPos.RIGHT);

		Button tal = new Button(" Tallenna ");
		
		/* Toiminnallisuus käyttäjätietojen tallentamiselle. */
		tal.setOnAction(e -> {
			
			// kaikki kentät eivät ole täytettynä
			if (vastausbox.getText().isEmpty() || kayttajanimibox.getText().isEmpty() || salasanabox.getText().isEmpty()) {

				actionTarget.setId("loginFailed");
				actionTarget.setText("Täytä kaikki kentät");
			}
			
			// negaatio(kaikki kentät eivät ole täytettynä) :)
			else {
				int ret = Database.lisaaTunnus(kayttajanimibox.getText(), salasanabox.getText(), lisakysymys.getText(),
						vastausbox.getText());
				System.out.println(ret);
				switch (ret) {
				case -1:
					actionTarget.setId("loginFailed");
					actionTarget.setText("Käyttäjätietojen lisäämisessä tapahtui virhe.");
					break;
				case -0:
					actionTarget.setId("loginFailed");
					actionTarget.setText("Käyttäjänimi on jo käytössä.");
					break;
				case 1:
					actionTarget.setVisible(false);
					popup.display(primaryStage, loginScene);
					break;
				}
			}
			

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
