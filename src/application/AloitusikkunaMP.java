package application;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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

public class AloitusikkunaMP {
	// static StackPane stack;
	// static keskipalkki = new HBox();
	// static HBox otsikkopalkki = new HBox();
	// static VBox ohjepalkki = new VBox();

	int parienmaara;
	int rivienmaara;

	/**
	 * Luo aloitusikkunan, jossa voi valita vaikeustason
	 * 
	 * @return stackpane, jossa kaikki muistipelin ikkunat
	 */

	public static StackPane createContent(Stage primaryStage, Scene mainMenu, String username) {
		HBox keskipalkki = new HBox();
		HBox otsikkopalkki = new HBox();
		VBox ohjepalkki = new VBox();
		

		Image tausta = new Image("application/resources/tausta7.jpeg");
		
		//asetetaan taustakuvalle mitat
		 BackgroundSize bSize = new BackgroundSize(1200, 800, false, false, true, true);

		ImageView koti = new ImageView(new Image("application/resources/koti.png"));
		koti.setFitHeight(30);
		koti.setFitWidth(30);
		
		ImageView raksi = new ImageView(new Image("application/resources/raksi.png"));
		raksi.setFitHeight(30);
		raksi.setFitWidth(30);
		
		// Luodaan uusi stackpane, johon lis‰t‰‰n vaikeustasosivu, pelisivu ja tulossivu
		StackPane stack = new StackPane();
		// Luodaan peliIkkuna
		MuistipeliApp peliIkkunaolio = new MuistipeliApp(stack, primaryStage, username);
		// luodaan t‰ll‰ ikkunalle borberpane johon sitten lis‰t‰‰n muita paneeleja
		BorderPane alotusIkkuna = new BorderPane();
		// Asetetaan alotusIkkuna stackiin ja asetetaan stackpanelle koko jota ei
		// myˆhemmin muuteta
		//stack.getChildren().add(tausta);
		stack.getChildren().add(alotusIkkuna);
		stack.setMaxHeight(700);
		stack.setPrefHeight(700);
		
		//asetetaan aloitusIkkunalle taustakuva
		alotusIkkuna.setBackground(new Background(new BackgroundImage(tausta, BackgroundRepeat.NO_REPEAT,
	            BackgroundRepeat.NO_REPEAT,
	            BackgroundPosition.CENTER,
	            bSize)));
		
		// Asetetaan boderpane paneelit, joihin vasta laitetaan varsinaiset komponentit
		alotusIkkuna.setTop(otsikkopalkki);
		alotusIkkuna.setCenter(keskipalkki);
		alotusIkkuna.setBottom(ohjepalkki);
		// Asetetaan stackpane t‰m‰n luokan stackiin jotta voidaan k‰ytt‰‰ myˆhemminkin
		// koodissa
		// this.stack = stack;

		// Luodaan otsikkoon tarvittavat tiedot ja muokataan otsikon sijaintia ja
		// ulkon‰kˆ‰
		Label valitse = new Label("Valitse parien m‰‰r‰");
		valitse.setFont(new Font("Arial", 40));
		valitse.setStyle("-fx-font-size: 30pt;");
		otsikkopalkki.getChildren().add(valitse);
		otsikkopalkki.setAlignment(Pos.TOP_CENTER);
		otsikkopalkki.setPadding(new Insets(80, 0, 0, 0));

		// Asetetaan napit keskipaneeliin sek‰ muokataan niiden tyyli‰ ja sijaintia
		Button parinappi1 = new Button("6");
		Button parinappi2 = new Button("10");
		Button parinappi3 = new Button("12");
		parinappi1.setStyle("-fx-font-size: 25pt; -fx-pref-width: 120px;");
		parinappi2.setStyle("-fx-font-size: 25pt; -fx-pref-width: 120px;");
		parinappi3.setStyle("-fx-font-size: 25pt; -fx-pref-width: 120px;");
		keskipalkki.setAlignment(Pos.TOP_CENTER);
		keskipalkki.setSpacing(40);
		keskipalkki.getChildren().addAll(parinappi1, parinappi2, parinappi3);
		keskipalkki.setPadding(new Insets(80, 10, 0, 10));

		// Asetetaan ohjeet alapalkkiin
		Text ohjeL = new Text("Ohje muistipeliin:");
		ohjeL.setStyle("-fx-font-family: Tahoma; -fx-font-size: 36px; -fx-fill: #ffffff;"
				+ "-fx-stroke: #191919; -fx-stroke-width: 1px; -fx-font-weight: bold;");
		// TƒHƒM KOHTAAN OHJEESEENTULEVA TEKSTI
		Text ohjelaatikko = new Text(
				"\nPelaaja voi s‰‰t‰‰ vaikeustasoa p‰‰tt‰m‰ll‰ montako paria h‰n valitsee peliins‰. Peli alkaa vaikeustason valinnan j‰lkeen, jonka j‰lkeen pelaajan tulee klikkailla kortteja ja lˆyt‰‰ kullekin oikea pari. ");
		//Taustalle j‰‰ jostain syyst‰ pieni harmaa viiva? Taustakuva ratkaisee t‰m‰n?
		ohjelaatikko.setStyle("-fx-font-size: 26px; -fx-fill: #191919; -fx-stroke:#ffffff;"
				+ "-fx-stroke-width: 0px; -fx-font-weight: normal;");
		ohjelaatikko.setWrappingWidth(1130);
		ohjelaatikko.setTranslateX(30);
		
		//HBox napeille joilla siirryt‰‰n paaikkunaan ja pois ohjelmasta
		HBox napeillePalkki = new HBox();
		napeillePalkki.setPrefWidth(1180);
		napeillePalkki.setMinWidth(1180);

		// Luodaan nappulat lopeta ja paluu p‰‰ikkunaan. Laitetaan ne omaan hbox
		// paneeliin
		
		Button paaikkunaan = new Button(" Palaa p‰‰ikkunaan", koti);

		paaikkunaan.setOnAction(e -> {
			primaryStage.setScene(mainMenu);
		});

		Button lopetaB = new Button("Lopeta", raksi);
		lopetaB.setOnAction(e -> {Platform.exit();});
		//Purkkaa, mutta alignment ei toimi jostain syyst‰?
		//lopetaB.setTranslateX(850);
		
		napeillePalkki.getChildren().addAll(paaikkunaan, lopetaB);
		napeillePalkki.setPadding(new Insets(0, 10, 10, 10));
		//MISSƒ KOHDASSA NƒKYY LOPETA JA PALAA PƒƒIKKUNAAN NAPPULAT
		napeillePalkki.setSpacing(760);

		// Asetetaan ohjepaneeliin ohjeeseen liittyv‰t komponentit sek‰ viimeisen‰
		// palkki jossa on napit
		ohjepalkki.getChildren().addAll(ohjeL, ohjelaatikko, napeillePalkki);
		ohjepalkki.setAlignment(Pos.BOTTOM_LEFT);
		ohjepalkki.setPadding(new Insets(0, 10, 10, 10));
		ohjepalkki.setSpacing(10);

		// Ensimm‰isen vaikeustaso napin toiminnallisuus
		parinappi1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// suoritetaan peli ikkunan metodi jolle annetaan parametrina parien m‰‰r‰,
				// sarakkeidenm‰‰r‰ ja vaikeustaso
				peliIkkunaolio.createContent(6, 4, 1);
				// laitetaan peli ikkuna n‰kyville
				changeTop(stack);
			}

		});

		// Toisen vaikeustaso napin toiminnallisuus
		parinappi2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// suoritetaan peli ikkunan metodi jolle annetaan parametrina parien m‰‰r‰,
				// sarakkeidenm‰‰r‰ ja vaikeustaso
				peliIkkunaolio.createContent(10, 5, 2);
				// laitetaan peli ikkuna n‰kyville
				changeTop(stack);
			}

		});

		// Kolmannen vaikeustaso napin toiminnallisuus
		parinappi3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// suoritetaan peli ikkunan metodi jolle annetaan parametrina parien m‰‰r‰,
				// sarakkeidenm‰‰r‰ ja vaikeustaso
				peliIkkunaolio.createContent(12, 6, 3);
				// laitetaan peli ikkuna n‰kyville
				changeTop(stack);
			}

		});

		// Lopeta toiminnallisuus lopeta -napille
		lopetaB.setOnAction(actionEvent -> Platform.exit());

		return stack;
	}

	// T‰ll‰ voidaan yhdist‰‰ p‰‰ohjelmaan
	public static Scene start(Stage primaryStage, Scene mainMenu, String username) {

		// Luodaan muistipelille yl‰otsikko, joka n‰kyy kaikilla sivuilla
		Text parit = new Text("Muistipeli");
		parit.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		VBox otsikko = new VBox();
		otsikko.setStyle(""
				+ "-fx-border-width: 0 0 2 0;"
				+ "-fx-border-color: black;");
		otsikko.setPrefHeight(100);
		otsikko.setAlignment(Pos.CENTER);
		otsikko.getChildren().add(parit);
		BorderPane.setMargin(otsikko, new Insets(0, 0, 0, 0));

		// Luodaan aloitusikkuna
		StackPane testPane = createContent(primaryStage, mainMenu, username);
		BorderPane border = new BorderPane();
		BorderPane.setAlignment(testPane, Pos.CENTER);
		border.setCenter(testPane);
		border.setTop(otsikko);
		// border.setBottom(napit);

		Scene testScene = new Scene(border, 1200, 800);
		testScene.getStylesheets().add("application/application.css");

		return testScene;
	}

	// Vaihtaa peliIkkunan n‰kyville ottamalla alimpana pinossa olevan ja
	// laittamalla sen p‰‰limm‰iseksi
	private static void changeTop(StackPane stack) {
		ObservableList<Node> childs = stack.getChildren();

		if (childs.size() > 1) {
			//
			Node topNode = childs.get(0);
			topNode.toFront();
		}
	}

}
