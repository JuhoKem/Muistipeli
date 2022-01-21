package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.database.Database;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ParhaatTul {

	public static Scene start(Stage primaryStage, Scene mainMenu) {

		BorderPane paneeli = new BorderPane();
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);

		grid.setGridLinesVisible(false);

		VBox pelit = new VBox();
		pelit.setPadding(new Insets(20));
		pelit.setSpacing(200);
		pelit.setAlignment(Pos.CENTER_RIGHT);

		VBox tasot = new VBox();
		tasot.setPadding(new Insets(20));
		tasot.setSpacing(50);
		tasot.setAlignment(Pos.CENTER_RIGHT);

		VBox tulokset = new VBox();
		tulokset.setPadding(new Insets(20));
		tulokset.setSpacing(55);
		tulokset.setAlignment(Pos.CENTER);

		VBox nimet = new VBox();
		nimet.setPadding(new Insets(20));
		nimet.setSpacing(55);
		nimet.setAlignment(Pos.CENTER);

		HBox nappi = new HBox();
		nappi.setPadding(new Insets(20));
		nappi.setSpacing(10);
		nappi.setAlignment(Pos.CENTER);

		HBox teksti = new HBox();
		teksti.setPadding(new Insets(20));
		teksti.setSpacing(10);
		teksti.setAlignment(Pos.CENTER);

		Font valmisfontti = Font.font("Tahoma", FontWeight.LIGHT, 30);

		Text parhaat = new Text(" Parhaat tulokset: ");
		parhaat.setFont(Font.font("Tahoma", FontWeight.LIGHT, 60));

		Text muistipeli = new Text(" Muistipeli:");
		muistipeli.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));

		Label taso1muisti = new Label(" Helppo: ");
		taso1muisti.setFont(valmisfontti);
		TextField tulos1muisti = new TextField();
		TextField tulos1muistiPiste = new TextField();

		Label taso2muisti = new Label(" Normaali: ");
		taso2muisti.setFont(valmisfontti);
		TextField tulos2muisti = new TextField();
		TextField tulos2muistiPiste = new TextField();

		Label taso3muisti = new Label(" Vaikea: ");
		taso3muisti.setFont(valmisfontti);
		TextField tulos3muisti = new TextField();
		TextField tulos3muistiPiste = new TextField();

		Text varipeli = new Text("Väripeli:");
		varipeli.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));

		Label taso1vari = new Label(" Helppo: ");
		taso1vari.setFont(valmisfontti);
		TextField tulos1vari = new TextField();
		TextField tulos1variPiste = new TextField();

		Label taso2vari = new Label(" Normaali: ");
		taso2vari.setFont(valmisfontti);
		TextField tulos2vari = new TextField();
		TextField tulos2variPiste = new TextField();

		Label taso3vari = new Label(" Vaikea: ");
		taso3vari.setFont(valmisfontti);
		TextField tulos3vari = new TextField();
		TextField tulos3variPiste = new TextField();

		//Haetaan tulokset tietokannasta
		ArrayList<TextField> muistiList = new ArrayList<TextField>();
		ArrayList<TextField> variList = new ArrayList<TextField>();
		muistiList.add(tulos1muisti);
		muistiList.add(tulos2muisti);
		muistiList.add(tulos3muisti);
		muistiList.add(tulos1muistiPiste);
		muistiList.add(tulos2muistiPiste);
		muistiList.add(tulos3muistiPiste);
		variList.add(tulos1vari);
		variList.add(tulos2vari);
		variList.add(tulos3vari);
		variList.add(tulos1variPiste);
		variList.add(tulos2variPiste);
		variList.add(tulos3variPiste);
		
		haeTulokset(muistiList, variList);
		
		for (TextField tf: muistiList) {
			tf.setEditable(false);
		}
		for (TextField tf: variList) {
			tf.setEditable(false);
		}
		
		ImageView tausta = new ImageView(new Image("application/resources/tausta7.jpeg"));
		StackPane keskus = new StackPane();

		Button palaa = new Button(" Palaa");
		palaa.setMinSize(300, 100);
		palaa.setStyle("-fx-font-size: 2em;");
		
		palaa.setOnAction(e -> {
			primaryStage.setScene(mainMenu);
		});

		teksti.getChildren().add(parhaat);

		pelit.getChildren().addAll(muistipeli, varipeli);

		tasot.getChildren().addAll(taso1muisti, taso2muisti, taso3muisti, taso1vari, taso2vari, taso3vari);

		tulokset.getChildren().addAll(tulos1muisti, tulos2muisti, tulos3muisti, tulos1vari, tulos2vari, tulos3vari);

		nimet.getChildren().addAll(tulos1muistiPiste, tulos2muistiPiste, tulos3muistiPiste, tulos1variPiste,
				tulos2variPiste, tulos3variPiste);

		nappi.getChildren().add(palaa);

		grid.add(pelit, 1, 0);
		grid.add(tasot, 2, 0);
		grid.add(tulokset, 3, 0);
		grid.add(nimet, 4, 0);
		
		paneeli.setCenter(grid);
		paneeli.setTop(teksti);
		paneeli.setBottom(nappi);
		
		keskus.getChildren().addAll(tausta, paneeli);

		Scene scene = new Scene(keskus, 1200, 800);
		scene.getStylesheets().add("application/application.css"); // css-tiedosto
		primaryStage.setTitle(" Parhaat tulokset ");
		
		return scene;
	}

	private static void haeTulokset(ArrayList<TextField> muisti, ArrayList<TextField> vari) {
		ResultSet rs = Database.haeKaikkiParhaatTulokset();	//Sisältää kaikki tulokset, joten vaatii läpikäyntejä
		System.out.println("Haetaan tuloksia");
		try {
			rs.beforeFirst();
			while (rs.next()) {
				for (int peli = 1; peli < 3; peli++) {
					for (int taso = 1; taso < 4; taso++) {
						if (rs.getInt(3) == peli && rs.getInt(4) == taso) {
							if (peli == 1) {
								TextField tf = muisti.get(taso - 1);
								tf.setText(rs.getString(1));
								tf = muisti.get(taso + 2);
								tf.setText(rs.getString(2));
							} else {
								TextField tf = vari.get(taso - 1);
								tf.setText(rs.getString(1));
								tf = vari.get(taso + 2);
								tf.setText(rs.getString(2));
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
