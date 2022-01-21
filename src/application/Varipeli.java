package application;

import application.database.Database;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Varipeli {

	public static Scene start(Stage primaryStage, Scene mainMenu, int vaikeustaso, String username) {
		// Päivitetään peliin käytettävä eika vaikeustason mukaan:
		double gameTime = 2000;
		gameTime = updateTime(gameTime, vaikeustaso); // Muokkaa aikaa.
		VaripeliLogic varipeli = new VaripeliLogic();
//		ikkuna.setWidth(1200);
//		ikkuna.setHeight(800);
		BorderPane komponenttiryhma = new BorderPane();
		
		Text otsikko = new Text("Väripeli");
		otsikko.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		VBox otsikkoboxi = new VBox();
		otsikkoboxi.setStyle(""
				+ "-fx-border-width: 0 0 2 0;"
				+ "-fx-border-color: black;");
		otsikkoboxi.setPrefHeight(100);
		otsikkoboxi.setAlignment(Pos.CENTER);
		otsikkoboxi.getChildren().add(otsikko);
		BorderPane.setMargin(otsikkoboxi, new Insets(0, 0, 0, 0));
		komponenttiryhma.setTop(otsikkoboxi);
		
		Button nappi1 = new Button("");
		Button nappi2 = new Button("");
		Button nappi3 = new Button("");
		Button nappi4 = new Button("");

		nappi1.setStyle("-fx-font-size:30px;");
		nappi1.setMinWidth(250);
		nappi1.setMinHeight(80);
		nappi2.setStyle("-fx-font-size:30px;");
		nappi2.setMinWidth(250);
		nappi2.setMinHeight(80);
		nappi3.setStyle("-fx-font-size:30px;");
		nappi3.setMinWidth(250);
		nappi3.setMinHeight(80);
		nappi4.setStyle("-fx-font-size:30px;");
		nappi4.setMinWidth(250);
		nappi4.setMinHeight(80);

		GridPane nappiryhma = new GridPane();
		nappiryhma.add(nappi1, 1, 1);
		nappiryhma.add(nappi2, 2, 1);
		nappiryhma.add(nappi3, 1, 2);
		nappiryhma.add(nappi4, 2, 2);

		Label sana = new Label("");
		sana.setFont(new javafx.scene.text.Font(80));
		ProgressBar timeri = new ProgressBar(100);
		nappiryhma.add(timeri, 1, 0, 2, 1);
		timeri.setMaxSize(520, 40);

		ImageView tausta = new ImageView(new Image(varipeli.getTausta()));

		StackPane keskus = new StackPane();
		keskus.getChildren().addAll(tausta, sana);

		nappiryhma.setVgap(10);
		nappiryhma.setHgap(10);
		nappiryhma.setAlignment(Pos.CENTER);
		
		komponenttiryhma.setCenter(keskus);
		komponenttiryhma.setMaxSize(1200, 800);
		komponenttiryhma.setPrefSize(1200, 800);

		Scene nakyma = new Scene(komponenttiryhma);

		Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);

		timeline.getKeyFrames().add(new KeyFrame(Duration.ZERO, event -> {
			if (varipeli.getKierros() >= 15) {
				timeline.stop();
				timeline.getKeyFrames().clear();
				nappi1.setDisable(true);
				nappi2.setDisable(true);
				nappi3.setDisable(true);
				nappi4.setDisable(true);
				// tähän lopetusruutu
				String pisteet = String.valueOf(varipeli.getPisteet());
				sana.setTextFill(Color.BLACK);
				sana.setFont(new javafx.scene.text.Font(40));
				sana.setText("Peli loppui!\nPisteesi: " + pisteet);
				Database.lisaaVaripelitulos(vaikeustaso, username, varipeli.getPisteet());
				Database.paivitaParhaatTulokset(0, vaikeustaso, username, varipeli.getPisteet(), 2);
				primaryStage.setScene(VaripeliTulos.start(primaryStage, mainMenu, vaikeustaso, username, pisteet));
			} else {
				varipeli.luoVarit();
				Image kuva = new Image(varipeli.getTausta());
				tausta.setImage(kuva);
				sana.setText(varipeli.getSana());
				sana.setTextFill(varipeli.getTekstinVari());
				nappi1.setText(varipeli.getNappi(1));
				nappi2.setText(varipeli.getNappi(2));
				nappi3.setText(varipeli.getNappi(3));
				nappi4.setText(varipeli.getNappi(4));
				nappi1.setDisable(false);
				nappi2.setDisable(false);
				nappi3.setDisable(false);
				nappi4.setDisable(false);
				varipeli.setKierros();
			}
		}, new KeyValue(timeri.progressProperty(), 1)));

		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(gameTime), event -> {
			PauseTransition pause = new PauseTransition(Duration.millis(1000));
			Image taustakuva = new Image(varipeli.getVastauksenTausta());
			tausta.setImage(taustakuva);
			sana.setTextFill(Color.RED);
			sana.setText("VÄÄRIN");
			nappi1.setDisable(true);
			nappi2.setDisable(true);
			nappi3.setDisable(true);
			nappi4.setDisable(true);
			pause.setOnFinished(e -> {
				timeline.playFromStart();
			});
			timeline.pause();
			pause.play();
		}, new KeyValue(timeri.progressProperty(), 0)));

		timeline.play();

		nappi1.setOnAction((e) -> {
			varipeli.tarkistaVastaus(1);
			PauseTransition pause = new PauseTransition(Duration.millis(1000));
			Image taustakuva = new Image(varipeli.getVastauksenTausta());
			tausta.setImage(taustakuva);
			nappi1.setDisable(true);
			nappi2.setDisable(true);
			nappi3.setDisable(true);
			nappi4.setDisable(true);
			if (varipeli.oikein) {
				sana.setTextFill(Color.LIMEGREEN);
				sana.setText("OIKEIN");
			} else {
				sana.setTextFill(Color.RED);
				sana.setText("VÄÄRIN");
			}
			pause.setOnFinished(event -> {
				timeline.playFromStart();
			});
			timeline.pause();
			pause.play();
		});
		nappi2.setOnAction((e) -> {
			varipeli.tarkistaVastaus(2);
			PauseTransition pause = new PauseTransition(Duration.millis(1000));
			Image taustakuva = new Image(varipeli.getVastauksenTausta());
			tausta.setImage(taustakuva);
			nappi1.setDisable(true);
			nappi2.setDisable(true);
			nappi3.setDisable(true);
			nappi4.setDisable(true);
			if (varipeli.oikein) {
				sana.setTextFill(Color.LIMEGREEN);
				sana.setText("OIKEIN");
			} else {
				sana.setTextFill(Color.RED);
				sana.setText("VÄÄRIN");
			}
			pause.setOnFinished(event -> {
				timeline.playFromStart();
			});
			timeline.pause();
			pause.play();
		});
		nappi3.setOnAction((e) -> {
			varipeli.tarkistaVastaus(3);
			PauseTransition pause = new PauseTransition(Duration.millis(1000));
			Image taustakuva = new Image(varipeli.getVastauksenTausta());
			tausta.setImage(taustakuva);
			nappi1.setDisable(true);
			nappi2.setDisable(true);
			nappi3.setDisable(true);
			nappi4.setDisable(true);
			if (varipeli.oikein) {
				sana.setTextFill(Color.LIMEGREEN);
				sana.setText("OIKEIN");
			} else {
				sana.setTextFill(Color.RED);
				sana.setText("VÄÄRIN");
			}
			pause.setOnFinished(event -> {
				timeline.playFromStart();
			});
			timeline.pause();
			pause.play();
		});
		nappi4.setOnAction((e) -> {
			varipeli.tarkistaVastaus(4);
			PauseTransition pause = new PauseTransition(Duration.millis(1000));
			Image taustakuva = new Image(varipeli.getVastauksenTausta());
			tausta.setImage(taustakuva);
			nappi1.setDisable(true);
			nappi2.setDisable(true);
			nappi3.setDisable(true);
			nappi4.setDisable(true);
			if (varipeli.oikein) {
				sana.setTextFill(Color.LIMEGREEN);
				sana.setText("OIKEIN");
			} else {
				sana.setTextFill(Color.RED);
				sana.setText("VÄÄRIN");
			}
			pause.setOnFinished(event -> {
				timeline.playFromStart();
			});
			timeline.pause();
			pause.play();
		});

		ImageView koti = new ImageView(new Image("application/resources/koti.png"));
		koti.setFitHeight(30);
		koti.setFitWidth(30);
		// Demopurkkaa:
		Button demoExit = new Button("Palaa päävalikkoon", koti);
		demoExit.setOnAction(e -> {
			timeline.setCycleCount(0);
			Scene toMain = Paavalikko.start(primaryStage, username);
			primaryStage.setScene(toMain);
		});
		ImageView raksi = new ImageView(new Image("application/resources/raksi.png"));
		raksi.setFitHeight(30);
		raksi.setFitWidth(30);
		Button lopeta = new Button("Lopeta", raksi);
		lopeta.setOnAction(actionEvent -> Platform.exit());

		demoExit.getStylesheets().add("application/application.css");
		lopeta.getStylesheets().add("application/application.css");
		
		demoExit.setStyle("-fx-font-size: 10pt;");
		lopeta.setStyle("-fx-font-size: 10pt;");

		GridPane nappienAsettelu = new GridPane();
		nappienAsettelu.setPadding(new Insets(10, 10, 10, 10));
		
		nappienAsettelu.add(nappiryhma, 1, 0);
		nappienAsettelu.add(demoExit, 0, 1);
		nappienAsettelu.add(lopeta, 2, 1);
		
		GridPane.setHalignment(lopeta, HPos.RIGHT);
		GridPane.setHalignment(demoExit, HPos.LEFT);
		GridPane.setHalignment(nappiryhma, HPos.CENTER);
		
		ColumnConstraints cc1 = new ColumnConstraints();
		cc1.setPercentWidth(25);
		ColumnConstraints cc2 = new ColumnConstraints();
		cc2.setPercentWidth(50);
		ColumnConstraints cc3 = new ColumnConstraints();
		cc3.setPercentWidth(25);
		nappienAsettelu.getColumnConstraints().addAll(cc1, cc2, cc3);
		
		komponenttiryhma.setBottom(nappienAsettelu);

		return nakyma;
	}

	private static double updateTime(double t, int multiplier) {
		switch (multiplier) {
		case 1: // Helpoin
			t = t * 1.3;
			return t;
		case 2:
			t = t * 1.0;
			return t;
		case 3: // Vaikein
			t = t * 0.7;
			return t;
		}
		return -1; // Jotta eclipse lakkaa itkemästä :)
	}
}
