package application;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class VaripeliAloitus {

	public static Scene start(Stage primaryStage, Scene mainMenu, String username) {
		
		// Asettelu kopsattu muistipelistä
		HBox keskipalkki = new HBox();
		HBox otsikkopalkki = new HBox();
		VBox ohjepalkki = new VBox();
	
		Image tausta = new Image("application/resources/tausta7.jpeg");
		 BackgroundSize bSize = new BackgroundSize(1200, 800, false, false, true, true);
		
		ImageView koti = new ImageView(new Image("application/resources/koti.png"));
		koti.setFitHeight(30);
		koti.setFitWidth(30);
		
		ImageView raksi = new ImageView(new Image("application/resources/raksi.png"));
		raksi.setFitHeight(30);
		raksi.setFitWidth(30);
		
		StackPane stack = new StackPane();
		BorderPane alotusIkkuna = new BorderPane();
		
		alotusIkkuna.setBackground(new Background(new BackgroundImage(tausta, BackgroundRepeat.NO_REPEAT,
	            BackgroundRepeat.NO_REPEAT,
	            BackgroundPosition.CENTER,
	            bSize)));
		
		stack.setMaxHeight(700);
		stack.setPrefHeight(700);
		
		stack.getChildren().add(alotusIkkuna);
		alotusIkkuna.setTop(otsikkopalkki);
		alotusIkkuna.setCenter(keskipalkki);
		alotusIkkuna.setBottom(ohjepalkki);
		Text valitse = new Text("Valitse vaikeustaso");
		valitse.setStyle("-fx-font-family: Tahoma; -fx-font-size: 40px; -fx-fill: #191919;");
		otsikkopalkki.getChildren().add(valitse);
		otsikkopalkki.setAlignment(Pos.TOP_CENTER);
		otsikkopalkki.setPadding(new Insets(80, 0, 0, 0));

		Button helppo = new Button("Helppo"), normaali = new Button("Normaali"), vaikea = new Button("Vaikea");
				
		helppo.setStyle("-fx-font-size: 20pt; -fx-pref-width: 200px;");
		normaali.setStyle("-fx-font-size: 20pt; -fx-pref-width: 200px;");
		vaikea.setStyle("-fx-font-size: 20pt; -fx-pref-width: 200px;");
		keskipalkki.setAlignment(Pos.TOP_CENTER);
		keskipalkki.setSpacing(40);
		keskipalkki.getChildren().addAll(helppo, normaali, vaikea);
		keskipalkki.setPadding(new Insets(80, 10, 0, 10));

		Text ohjeL = new Text("Ohje väripeliin:");
		ohjeL.setStyle("-fx-font-family: Tahoma; -fx-font-size: 36px; -fx-fill: #ffffff;"
				+ "-fx-stroke: #191919; -fx-stroke-width: 1px; -fx-font-weight: bold;");
		Text ohjelaatikko = new Text(
				"\nPelaaja voi säätää vaikeustasoa päättämällä kuinka paljon arvaamiseen annetaan aikaa. Pelissä näytetään sana. Pelaajan tulee valita alla olevista vaihtoehdoista se, joka kertoo millä värillä yllä oleva sana on kirjoitettu.");
		ohjelaatikko.setWrappingWidth(1130);
		ohjelaatikko.setTranslateX(30);
		ohjelaatikko.setStyle("-fx-font-size: 26px; -fx-fill: #191919; -fx-stroke:#ffffff;"
				+ "-fx-stroke-width: 0px; -fx-font-weight: normal;");

		Button paaikkunaan = new Button(" Palaa pääikkunaan", koti);

		paaikkunaan.setOnAction(e -> {
			Scene toMain = Paavalikko.start(primaryStage, username);
			primaryStage.setScene(toMain);
		});

		HBox napeillePalkki = new HBox();
		napeillePalkki.setPrefWidth(1180);
		Button lopetaB = new Button(" Lopeta", raksi);
		//Purkkaa, mutta alignment ei toimi jostain syystä?
		lopetaB.setTranslateX(760);
		napeillePalkki.getChildren().addAll(paaikkunaan, lopetaB);
		napeillePalkki.setPadding(new Insets(0, 10, 10, 10));

		ohjepalkki.getChildren().addAll(ohjeL, ohjelaatikko, napeillePalkki);
		ohjepalkki.setAlignment(Pos.BOTTOM_LEFT);
		ohjepalkki.setPadding(new Insets(0, 10, 10, 10));
		ohjepalkki.setSpacing(10);

		helppo.setOnAction(e -> {
			aloitaUusiPeli(primaryStage, mainMenu, 1, username);
		});
		normaali.setOnAction(e -> {
			aloitaUusiPeli(primaryStage, mainMenu, 2, username);
		});
		vaikea.setOnAction(e -> {
			aloitaUusiPeli(primaryStage, mainMenu, 3, username);
		});

		lopetaB.setOnAction(actionEvent -> Platform.exit());

		Text parit = new Text("Väripeli");
		parit.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		VBox otsikko = new VBox();
		otsikko.setStyle(""
				+ "-fx-border-width: 0 0 2 0;"
				+ "-fx-border-color: black;");
		otsikko.setPrefHeight(100);
		otsikko.setAlignment(Pos.CENTER);
		otsikko.getChildren().add(parit);
		BorderPane.setMargin(otsikko, new Insets(0, 0, 0, 0));
		
		BorderPane border = new BorderPane();
		BorderPane.setAlignment(stack, Pos.CENTER);
		border.setCenter(stack);
		border.setTop(otsikko);

		Scene scene = new Scene(border, 1200, 800);
		scene.getStylesheets().add("application/application.css");

		return scene;
	}

	public static void aloitaUusiPeli(Stage ps, Scene mainMenu, int vaikeustaso, String username) {
		ps.setScene(Varipeli.start(ps, mainMenu, vaikeustaso, username));
	}

}
