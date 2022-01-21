package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.animation.FadeTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MuistipeliApp {

	// Parien lkm ja rivien lkm -> arvot otetaan valitusta vaikeustasosta
	private static int NUM_OF_PAIRS = 4;
	private static int NUM_PER_ROW = 2;

	private BufferedImage kokoKuva;
	final int width = 69;
	final int height = 94;


	private Tile selected = null;
	// Valittujen korttien max m‰‰r‰ = 2
	private int clickCount = 2;

	//Lˆydetyt kortit
	private int oikein = 0;

	private int vaikeustaso;
	private StackPane stack;
	private BorderPane peliIkkuna;
	private BorderPane tulosIkkuna;
	private YlaPalkkiMuistiPeli ylapalkkiolio;

	//Kuvien leveys ja korkeus suoraan muuttujissa t‰ss‰ eli vaiha n‰it‰ jos haluat muokata kokoa
	private int kuvienkorkeus = 129; 
	private int kuvienleveys = 129;		
	private int korttienkorkeus = 130; 
	private int korttienleveys = 130;

	private pelipisteetMP pelipisteetolio;
	private HBox ylapalkki;
	private HBox alapalkki;

	protected String user;
	protected Stage primaryStage;
	private int tiilinNumero = 0;
	private boolean lippuKaannetty = false;

	public MuistipeliApp(StackPane stack, Stage primaryStage, String username) {
		this.stack = stack;
		user = username;
		this.primaryStage = primaryStage;

		peliIkkuna = new BorderPane();

		//Peli ikkunan ylapalkki ja alapalkki
		ylapalkki = new HBox();
		alapalkki = new HBox();

		//Lis‰t‰‰n peli-ikkuna ja tulosikkuna stackii, jolla voidaan vaihella sivujen v‰lill‰
		stack.getChildren().add(peliIkkuna);
		tulosIkkuna = new BorderPane();
		stack.getChildren().add(tulosIkkuna);
		tulosIkkuna.setStyle("-fx-background-color: #F4F4F4;");


		//asetetaan pohja v‰ri ettei n‰y l‰vite, t‰ss‰ valkoinen
		peliIkkuna.setStyle("-fx-background-color: #F4F4F4;");

		//piste olion luonti
		pelipisteetolio = new pelipisteetMP();

		//Luodaan yl‰ ja alapalkeille oma olio. nimi virheellisesti ylapalkki...
		ylapalkkiolio = new YlaPalkkiMuistiPeli(stack, ylapalkki, alapalkki, pelipisteetolio, primaryStage, username);

		//astetaan alapalkki alas ja yl‰palkki ylˆs			
		peliIkkuna.setTop(ylapalkki);
		peliIkkuna.setBottom(alapalkki);
	}



	// Pelilauta
	public void createContent(int parienmaara, int rivienmaara, int vaikeustaso) {	

		//laitetaan kellop‰‰lle 
		ylapalkkiolio.kelloPaalle();

		//TƒSSƒ VAIKEUSTASO TIETO TONILLE TIETOKANTAA VARTEN!!!!
		this.vaikeustaso = vaikeustaso;

		NUM_OF_PAIRS=parienmaara;		
		NUM_PER_ROW=rivienmaara;
		//Muuttujan avulla lasketaan milloin on kaikki kortit lˆydetty
		oikein = 0;
		//Luodaan pelilaudalle borderpane jotta sen keskitys onnistuisi paremmin
		BorderPane vasenroot= new BorderPane();
		Pane root= new Pane();
		vasenroot.setCenter(root);

		peliIkkuna.setCenter(root);
		peliIkkuna.setLeft(vasenroot);
		
		/**
		 * Siirret‰‰n peliruudukko keskelle ruudukon koon mukaan
		 */
		if(vaikeustaso==1) {
			vasenroot.setPadding(new Insets(0, 340, 0, 0));
			ylapalkki.setPadding(new Insets(10, 0, 70, 10));
		}
		else if(vaikeustaso==2) {
			vasenroot.setPadding(new Insets(0, 290, 0, 0));
			ylapalkki.setPadding(new Insets(10, 0, 30, 10));
		}
		else {
			vasenroot.setPadding(new Insets(0, 250, 0, 0));
			ylapalkki.setPadding(new Insets(10, 0, 40, 10));
		}


		// Kirjaimia k‰ytet‰‰n tunnistamaan parit jokaisella parilla on oma kirjain.
		char c = 'A';
		//Haetaan kuva kokoKuva muuttujaan
		try {
			kokoKuva = ImageIO.read(getClass().getResourceAsStream("resources/kokoKuva11.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		//Tiilille, jotka toimii korttina luodaan arraylist
		List<Tile> tiles = new ArrayList<>();

		//M‰‰ritt‰‰ mit‰ kuvatiedoston rivi‰ ollaan menossa. Maksimi 4 rivi‰
		int t=0;
		//M‰‰ritt‰‰ kuinka monta kuvaa(korttia) tiedostosta otetaan eli parien m‰‰r‰n verran
		int kierrokset =0;
		//M‰‰ritt‰‰ kuinka mones kortti rivill‰ k‰sittelyss‰ spritesheetiss‰ 13 kuvaa rivill‰
		int ii = 0;
		//Lopetetaan kun parien m‰‰r‰n verran on otettu kuvia tai kun kuvatiedostosta on kaikki kuvat jo otettu
		//T‰ss‰ k‰yd‰‰n spritesheetti‰ rivitasolla eli mones rivi on menossa
		while(t<4 && kierrokset<NUM_OF_PAIRS) {
			ii = 0;
			//Lopetetaan kun parien m‰‰r‰ t‰yttyy tai kun ollaan p‰‰sty rivin loppuun 
			while(ii<13 && kierrokset<NUM_OF_PAIRS) {
				//Otetaan kuva siit‰ kohdasta miss‰ ollaan menossa ja toistetaan sama uudestaan jotta saadaan myˆs toiselle parille kuva
				Image kuvapala = SwingFXUtils.toFXImage(kokoKuva.getSubimage((ii)*width+((ii)*4), t*height+(t*4), width, height), null);
				Image kuvapala2 = SwingFXUtils.toFXImage(kokoKuva.getSubimage((ii)*width+((ii)*4), t*height+(t*4), width, height), null);
				ImageView kuva = new ImageView(kuvapala); 
				ImageView kuva2 = new ImageView(kuvapala2); 

				kuva.setId(String.valueOf(c));
				kuva2.setId(String.valueOf(c));
				//System.out.print(kuva.setI)


				//Asetetaan kuville leveys ja korkeus
				kuva2.setFitHeight(kuvienkorkeus);
				kuva2.setFitWidth(kuvienleveys);
				kuva.setFitHeight(kuvienkorkeus);
				kuva.setFitWidth(kuvienleveys);
				//Luodaan tile luokasta olioita ja laitetaan ne tiles arraylistiin
				//annetaan oliolle parametrina kuva sek‰ jokin kirjain, jotta voidaan vertailla onko kuvat samat
				tiles.add(new Tile(kuva,String.valueOf(c)));
				tiles.add(new Tile(kuva2,String.valueOf(c)));
				c++;
				ii++;
				kierrokset++;
			}
			t++;
		}
		//Sekoitetaan tiles listan oliot, jotta saadaan kortit erij‰rjestykseen
		Collections.shuffle(tiles);




		// Asetetaan ruudut paikalleen
		for (int i = 0; i < tiles.size(); i++) {
			Tile tile = tiles.get(i);
			tile.setTranslateX(korttienleveys * (i % NUM_PER_ROW));
			tile.setTranslateY(korttienkorkeus * (i / NUM_PER_ROW));
			root.getChildren().add(tile);
		}

	}





	private class Tile extends StackPane {
		private Text text = new Text();
		private ImageView kuva;
		private int tarkistusNumero;
		// Ruudut 50x50 ja niiden rajaukset
		public Tile(ImageView kuva, String value) {
			Rectangle border = new Rectangle(korttienleveys,korttienkorkeus);
			border.setFill(null);
			border.setStroke(Color.BLACK);

			text.setText(value);
			text.setFont(Font.font(30));
			this.kuva = kuva;
			tiilinNumero++;
			tarkistusNumero = tiilinNumero;
			setAlignment(Pos.CENTER);
			getChildren().addAll(border, kuva);

			//Kuvat piiloon heti ilman ett‰ n‰kyy peliin tultaessa puol sekunttia
			kuva.setOpacity(0);

			// Klikkauksen kuuntelija
			setOnMouseClicked(this::handleMouseClick);
			//kortit pois n‰kyvist‰ ilman ett‰ odotetaan joka kerta puol sekunttia ett‰ kortti on kadonnut
			close();	
		}

		// Klikkauksen kuuntelija
		public void handleMouseClick(MouseEvent event) {
			//Jos samaa korttia r‰mpytet‰‰n asetetaan lippuKaannetty trueksi
			if(selected!=null) {
				lippuKaannetty= tarkistusNumero==selected.tarkistusNumero;
			}

			if (isOpen() || clickCount == 0 ||lippuKaannetty) {
				return;
			}	

			clickCount--;
			
			if (selected == null) {
				selected = this;
				open(() -> {});
			} 
			else {
				open(() -> {

					if (!hasSameValue(selected)) {
						
						selected.close();
						this.close();

						//Jos pari lˆytyi
					}
					else if (hasSameValue(selected)) {
						oikein++;
						//Kasvatetaan pisteit‰
						pelipisteetolio.lisaaPisteet(vaikeustaso);
						//P‰ivitet‰‰n pisteet
						ylapalkkiolio.paivitaPisteet();
						//Tarkastetaan onko kaikki parit lˆytynyt
						onkoOikein();

					}

					selected = null;
					clickCount = 2;
				});
				
			}
			lippuKaannetty = false;
		};

		//Tarkistetaan onko kaikki kortit lˆydetty
		public void onkoOikein() {
			if(oikein>=NUM_OF_PAIRS) {
				//Pys‰ytet‰‰n kello ja otetaan kulunut aika talteen
				int kulunutaika = ylapalkkiolio.pysaytaKello();

				//luodaan tulosikkuna luomalla uusi olio ja antamalla sille parametrit
				TulosikkunaMP tulosikkunaolio = new TulosikkunaMP(stack, tulosIkkuna, vaikeustaso, kulunutaika, pelipisteetolio.getLoppupisteet(), primaryStage, user);
			}
		}


		// Tarkastaa onko kortti k‰‰nnetty oikeinp‰in			
		public boolean isOpen() {
			return kuva.getOpacity() == 1;
		}
		// K‰‰nt‰‰ kortin oikeinp‰in
		public void open(Runnable action) {
			FadeTransition ft = new FadeTransition(Duration.seconds(0.5), kuva);
			ft.setToValue(1);
			ft.setOnFinished(e -> action.run());
			ft.play();
		}
		// K‰‰nt‰‰ kortin v‰‰rinp‰in
		public void close() {
			FadeTransition ft = new FadeTransition(Duration.seconds(0.5), kuva);
			ft.setToValue(0);
			ft.play();
		}

		// Onko k‰‰nnetyill‰ korteilla sama arvo eli ovatko sama kortti
		public boolean hasSameValue(Tile other) {

			/**
			 * Tarkastetaan onko lˆytynyt pari sek‰ se ettei kyseinen kortti ole sama mihin sit‰ verrataan ett‰
			 * samaa korttia ei ole vain painettu kaksi kertaa
			 */
			boolean totuus = (text.getText().equals(other.text.getText())&&tarkistusNumero!=other.tarkistusNumero);

			return totuus;
		}



	}



}
