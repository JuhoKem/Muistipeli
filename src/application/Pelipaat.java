package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Pelipaat extends Application {

	@Override
	public void start(Stage primaryStage) {

		BorderPane paneeli = new BorderPane();
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(5);
		grid.setVgap(60);
		grid.setPadding(new Insets(10, 10, 10, 10));

		grid.setGridLinesVisible(false);

		Text pelipaat = new Text(" Peli p‰‰ttyi! ");
		pelipaat.setFont(Font.font("Tahoma", FontWeight.LIGHT, 60));
		pelipaat.setStroke(Color.BLACK);
		pelipaat.setFill(Color.RED);

		Label pisteet = new Label(" Pisteesi:");
		pisteet.setStyle("-fx-font-size: 40px;");	//Ylikirjoittaa css-tiedoston
		pisteet.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		TextField esimerkkipisteet = new TextField("300"); //TODO: Pelien pisteiden lis‰‰minen. t‰m‰ on siis aluksi vain esimerkki.
		esimerkkipisteet.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		esimerkkipisteet.setPrefWidth(100);
		esimerkkipisteet.setPrefHeight(100);
		esimerkkipisteet.setStyle("-fx-background-color: #eeeeee;");

		grid.add(pelipaat, 0, 0, 2, 1);
		grid.add(pisteet, 0, 1);
		grid.add(esimerkkipisteet, 1, 1);

		HBox napit = new HBox();
		napit.setPadding(new Insets(20));
		napit.setSpacing(500);
		napit.setAlignment(Pos.CENTER);

		Button uusipeli = new Button(" Uusi peli ");
		uusipeli.setStyle("-fx-font-size: 2em; ");
		
		Button valikko = new Button(" P‰‰valikko ");
		valikko.setStyle("-fx-font-size: 2em; ");

		ImageView tausta = new ImageView(new Image(getClass().getResourceAsStream("resources/tausta3.jpeg")));

		napit.getChildren().addAll(valikko, uusipeli);

		paneeli.setCenter(grid);
		paneeli.setBottom(napit);

		StackPane keskus = new StackPane();
		keskus.getChildren().addAll(tausta, paneeli);

		Scene scene = new Scene(keskus, 1200, 850, Color.RED);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm()); // css-tiedosto
		primaryStage.setTitle(" Peli p‰‰ttyi! ");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);

	}

}
