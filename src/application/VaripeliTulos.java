package application;

import java.sql.ResultSet;
import java.sql.SQLException;

import application.database.Database;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class VaripeliTulos {
	public static Scene start(Stage primaryStage, Scene mainMenu, int vaikeustaso, String username, String pisteet) {
		
		String textCSS = "-fx-font-family: Tahoma; -fx-font-size: 30px;"
				+ "-fx-fill: #191919; -fx-stroke: #ededed; -fx-stroke-width: 0px;"
				+ "-fx-font-weight: normal;";

		Text otsikkoL = new Text("Tulokset");
		otsikkoL.setStyle("-fx-font-family: Tahoma; -fx-font-size: 40px; -fx-fill: #191919;");

		Text kierrosTulosHead = new Text("Tämän kierroksen tulos:");
		kierrosTulosHead.setStyle("-fx-font-family: Tahoma; -fx-font-size: 40px; -fx-fill: #191919;");

		Text kierrosTulos = new Text(pisteet);
		kierrosTulos.setStyle(textCSS);

		Text parasTulosHead = new Text("Paras tulos:");
		parasTulosHead.setStyle("-fx-font-family: Tahoma; -fx-font-size: 40px; -fx-fill: #191919;");

		Text parasTulosLabel = null; // Label parhaalle tulokselle.
		ResultSet parasTulos = Database.haeParasPistemaara(2, vaikeustaso); // Paras tulos tietokannasta
		try {
			if (parasTulos != null) {
				parasTulosLabel = new Text(parasTulos.getString(1) + ": " + parasTulos.getString(2));
			} else {
				parasTulosLabel = new Text("Ei löytynyt."); // Virhe, jos tulosta ei löytynyt.
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		parasTulosLabel.setStyle(textCSS);
		
		Button uusiPeli = new Button("Pelaa uudestaan");

		uusiPeli.setOnAction(e -> {
			primaryStage.setScene(VaripeliAloitus.start(primaryStage, mainMenu, username));
		});

		ImageView koti = new ImageView(new Image("application/resources/koti.png"));
		koti.setFitHeight(30);
		koti.setFitWidth(30);
		Button paavalikko = new Button(" Palaa päävalikkoon", koti);

		paavalikko.setOnAction(e -> {
			primaryStage.setScene(Paavalikko.start(primaryStage, username));
		});
		
		ImageView tausta = new ImageView(new Image("application/resources/tausta7.jpeg"));
		tausta.setFitHeight(800);
		tausta.setFitWidth(1200);
		StackPane keskus = new StackPane();
		

		

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(50);
		grid.setVgap(50);
		grid.setPadding(new Insets(50, 50, 50, 50));
		grid.setPrefSize(1200, 800);
		grid.setMinSize(1200, 800);

		grid.add(otsikkoL, 1, 0);
		grid.add(kierrosTulosHead, 1, 1);
		grid.add(kierrosTulos, 1, 2);
		grid.add(parasTulosHead, 1, 3);
		grid.add(parasTulosLabel, 1, 4);
		grid.add(uusiPeli, 0, 5);
		grid.add(paavalikko, 2, 5);
		
		keskus.getChildren().addAll(tausta, grid);

		Scene scene = new Scene(keskus);
		scene.getStylesheets().add("application/application.css");

		return scene;
	}
}