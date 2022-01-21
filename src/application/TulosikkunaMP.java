package application;

import java.sql.ResultSet;
import java.sql.SQLException;

import application.database.Database;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TulosikkunaMP {
	private StackPane stack;
	private BorderPane tulosIkkuna;
	private int vaikeustaso;
	private int kulunutaika;
	private int pisteet;
	
		public TulosikkunaMP(StackPane stack, BorderPane tulosIkkuna, int vaikeustaso, int kulunutaika, int pisteet, Stage primaryStage, String username) {
			this.stack=stack;
			this.tulosIkkuna=tulosIkkuna;
			//Kauan meni aikaa pelin suorittamiseen
			this.kulunutaika = kulunutaika;
			this.pisteet=pisteet;
			
			//TƒSSƒ KIEROKSEN VAIKEUS TASO TONILLE TIETOKANTAA VARTEN!!!!!
			this.vaikeustaso = vaikeustaso;
			
			Database.lisaaMuistipelitulos(kulunutaika, vaikeustaso, username, pisteet);
			Database.paivitaParhaatTulokset(kulunutaika, vaikeustaso, username, pisteet, 1);
			
			ImageView tausta = new ImageView(new Image("application/resources/tausta7.jpeg"));
			//Luodaan tulosikkunaan paneelit, joihin komponentit laitetaan ja asetetaan ne borderpaneen
			HBox otsikkopalkki = new HBox();			
			VBox tuloksetpalkki = new VBox();
			HBox alapalkki = new HBox();
			
			tulosIkkuna.getChildren().add(tausta);
			tulosIkkuna.setTop(otsikkopalkki);
			tulosIkkuna.setCenter(tuloksetpalkki);
			tulosIkkuna.setBottom(alapalkki);
			
			//Luodaan otsikko palkkiin tarvittavat komponentit ja muokataan niiden tyyli‰ ja sijaintia
			Label otsikkoL = new Label("Tulokset");
			otsikkoL.setFont(new Font("Arial", 40));
			otsikkoL.setStyle("-fx-font-size: 40pt;");
			
			otsikkopalkki.getChildren().add(otsikkoL);
			otsikkopalkki.setAlignment(Pos.TOP_CENTER);
			otsikkopalkki.setPadding(new Insets(40, 0, 40, 0));
			
			//Luodaan tulosten n‰ytt‰miseen tarvittavat komponenti
			Label otsikkotulosL = new Label("T‰m‰n kierroksen tulos:");
			otsikkotulosL.setFont(new Font("Arial", 30));
			otsikkotulosL.setStyle("-fx-font-size: 30pt;");

			Text tulosL = new Text(""+pisteet);
			tulosL.setStyle("-fx-font-weight: bold; -fx-font-size: 26px;");
			Label otsikkoparasL = new Label("Paras tulos:");
			otsikkoparasL.setFont(new Font("Arial", 30));
			otsikkoparasL.setStyle("-fx-font-size: 30pt;");
			
			Text parasL = null;
			ResultSet parasTulos = Database.haeParasPistemaara(1, vaikeustaso);	//Haetaan paras tulos tietokannasta
			try {
				if (parasTulos != null) {
					parasL = new Text(parasTulos.getString(1) + ": " + parasTulos.getString(2));
				} else {
					parasL = new Text("Ei lˆytynyt");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			parasL.setStyle("-fx-font-weight: bold; -fx-font-size: 26px;");
			
			Button uudestaan = new Button("Pelaa uudestaan");
			uudestaan.setStyle("-fx-font-size: 20pt;");
			
			tuloksetpalkki.getChildren().addAll(otsikkotulosL,tulosL,otsikkoparasL,parasL,uudestaan);
			tuloksetpalkki.setAlignment(Pos.TOP_CENTER);
			tuloksetpalkki.setPadding(new Insets(20, 0, 10, 0));
			tuloksetpalkki.setSpacing(30);
			
			ImageView koti = new ImageView(new Image("application/resources/koti.png"));
			koti.setFitHeight(30);
			koti.setFitWidth(30);
			
			//NAPPI JOHON PITƒƒ LAITTAA TOIMINNALLISUUS JOLLA PALATAA PƒƒIKKUNAAN
			Button paaikkunaan = new Button(" Palaa p‰‰ikkunaan", koti);
			paaikkunaan.setOnAction(e -> {
				Scene toMain = Paavalikko.start(primaryStage, username);
				primaryStage.setScene(toMain);
			});
			paaikkunaan.setStyle("-fx-font-size: 12pt;");
			
			//lopetanappiin raksi
			ImageView raksi = new ImageView(new Image("application/resources/raksi.png"));
			raksi.setFitHeight(30);
			raksi.setFitWidth(30);
			
			Button lopetaB = new Button("Lopeta", raksi);
			lopetaB.setStyle("-fx-font-size: 12pt;");
			
			// Lopeta toiminnallisuus lopeta -napille
			lopetaB.setOnAction(actionEvent -> Platform.exit());
			
			alapalkki.getChildren().addAll(paaikkunaan, lopetaB);
			alapalkki.setAlignment(Pos.BOTTOM_LEFT);
			alapalkki.setPadding(new Insets(20, 10, 10, 10));
			alapalkki.setSpacing(850);
			
			//Vaihdetaan luokkaan tultaessa tulosikkuna n‰kyviin peliIkkunan tilalle.
			changeTopFront();
			
			
			
			//Vaihdetaan vaikeustasoikkuna esille
			uudestaan.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					//otetaan kaksi kertaa jotta p‰‰st‰‰n aloitussivulle
				//changeTopFront();
					changeTop();
				}

			});
		}
		
		//Vaihtaa tulosikkunan n‰kyviin
		private void changeTop() {
			ObservableList<Node> childs = this.stack.getChildren();
			if (childs.size() > 1) {
				//System.out.print(childs.size());
				Node topNode = childs.get(0);

		           topNode.toFront();
		          

				
			}
		}
		
		//Vaihdetaan vaikeustasoikkuna esille
		private void changeTopFront() {
			ObservableList<Node> childs = this.stack.getChildren();
			if (childs.size() > 1) {
				//System.out.print(childs.size());
				Node topNode = childs.get(0);

		           topNode.toFront();
		          

				
			}
		}
}

