package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Paavalikko {

	public static Scene start(Stage primaryStage, String username) {

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setVgap(100);

		HBox paavalikkoteksti = new HBox();
		paavalikkoteksti.setPadding(new Insets(20));
		paavalikkoteksti.setSpacing(50);
		paavalikkoteksti.setAlignment(Pos.TOP_CENTER);

		Text teksti = new Text(" Päävalikko ");
		teksti.setFont(Font.font("Tahoma", FontWeight.NORMAL, 60));
		teksti.setFill(Color.BLACK);
		paavalikkoteksti.getChildren().add(teksti);

		HBox pelikuvakkeet = new HBox();
		pelikuvakkeet.setId("pelit");
		pelikuvakkeet.setPadding(new Insets(10));
		pelikuvakkeet.setSpacing(20);
		pelikuvakkeet.setAlignment(Pos.CENTER);
		
		ImageView muistikuva = new ImageView(new Image("application/resources/muistikuva.jpg"));

		Button muisti = new Button(" ", muistikuva);
		muisti.setStyle("-fx-font-size: 2em;");
		muisti.setStyle("-fx-background-color: null;");
		muistikuva.setFitHeight(300);
		muistikuva.setFitWidth(455);
		//muisti.setMinSize(450, 300);
		
		ImageView varikuva = new ImageView(new Image("application/resources/varikuvavalmis.jpg"));
		Button vari = new Button(" ", varikuva);
		vari.setStyle("-fx-font-size: 2em;");
		vari.setStyle("-fx-background-color: null;");
		varikuva.setFitHeight(300);
		varikuva.setFitWidth(455);
		//vari.setMinSize(450, 300);

		pelikuvakkeet.getChildren().addAll(muisti, vari);

		HBox napit = new HBox();
		napit.setPadding(new Insets(10,0,0,0));
		napit.setSpacing(95);
		napit.setAlignment(Pos.BOTTOM_CENTER);
		
		//Kuvake uloskirjautumis nappulaan
		ImageView uloskuva = new ImageView(new Image("application/resources/UlosLogo.png"));
		uloskuva.setFitHeight(35);
		uloskuva.setFitWidth(25);
		
		Button kirjulos = new Button(" Kirjaudu ulos", uloskuva);
		kirjulos.setStyle("-fx-font-size: 2em; ");

		ImageView infokuva = new ImageView(new Image("application/resources/info.png"));
		infokuva.setFitHeight(60);
		infokuva.setFitWidth(60);

		Button info = new Button("", infokuva);
		info.setStyle("-fx-background-color: null;");	//Hävitetään "reunat" ikonista
		info.setMaxSize(60, 60);
		info.setMinSize(10, 10);

		ImageView palkinto = new ImageView(new Image("application/resources/palkinto2.png"));
		palkinto.setFitHeight(40);
		palkinto.setFitWidth(40);
		Button parhaat = new Button(" Parhaat tulokset ", palkinto);
		parhaat.setStyle("-fx-font-size: 2em; ");

		info.setOnAction(e -> PelienInfot.display("Pelien kulku", "MUISTIPELI: Etsi parit"));
		
		//Siirretään tyhja labeleilla napit oikeisiin kohtiin
		Label tyhja = new Label("          ");
		Label tyhja2 = new Label(" ");
		napit.getChildren().addAll(kirjulos,tyhja, info,tyhja2, parhaat);

		grid.add(paavalikkoteksti, 1, 0);
		grid.add(pelikuvakkeet, 1, 1);
		grid.add(napit, 1, 2);

		ImageView tausta = new ImageView(new Image("application/resources/tausta7.jpeg"));

		StackPane keskus = new StackPane();
		keskus.getChildren().addAll(tausta, grid);

		Scene scene = new Scene(keskus, 1200, 800);
		
		vari.setOnAction(e -> {	//Siirtymä väripeliin.
			Scene toVaripeli = VaripeliAloitus.start(primaryStage, scene, username);
			primaryStage.setScene(toVaripeli);
		});
		
		muisti.setOnAction(e -> {
			Scene toMuistipeli = AloitusikkunaMP.start(primaryStage, scene, username);
			primaryStage.setScene(toMuistipeli);
		});
		
		parhaat.setOnAction(e -> {	//Siirtymä parhaisiin tuloksiin.
			Scene toParhaatTulokset = ParhaatTul.start(primaryStage, scene);
			primaryStage.setScene(toParhaatTulokset);
		});
		
		kirjulos.setOnAction(e -> {
			Scene toLogin = Login.showLogin(primaryStage);
			primaryStage.setScene(toLogin);
			//Platform.exit();
		});
		
		scene.getStylesheets().add("application/application.css"); // css-tiedosto
		primaryStage.setTitle(" Päävalikko ");
		
		return scene;

	}
}
