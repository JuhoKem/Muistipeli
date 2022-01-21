package application;

import com.sun.jmx.snmp.tasks.Task;

import application.database.Database;
import javafx.application.Application;
import javafx.application.Platform;
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

public class Login extends Application {

	@Override
	public void start(Stage primaryStage) {
		
		ImageView tausta = new ImageView(new Image("application/resources/tausta7.jpeg"));
		StackPane keskus = new StackPane();
		
		final double SCREEN_WIDTH = 1200;
		final double SCREEN_HEIGHT = 800;

		GridPane grid = new GridPane(); // gridlayout luottaa rivi-sarake layoutiin, gridpane grid toimii pohjana
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(50, 50, 50, 50));

		grid.setGridLinesVisible(false);
		
		keskus.getChildren().addAll(tausta, grid);

		Scene scene = new Scene(keskus, SCREEN_WIDTH, SCREEN_HEIGHT);
		Text tervetuloa = new Text(" Tervetuloa! ");
		tervetuloa.setId("loginWelcome");
		tervetuloa.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		Label kayttajanimiteksti = new Label(" Käyttäjätunnus ");
		TextField kayttajanimibox = new TextField();
		Label salasanateksti = new Label(" Salasana ");
		PasswordField salasanabox = new PasswordField();
		Text unohditko = new Text(" Unohditko salasanasi?");
		unohditko.setId("infoText");
		//unohditko.setFont(Font.font("Tahoma", FontWeight.LIGHT, 15));
		Text eiviela = new Text("Ei vielä tunnuksia?");
		eiviela.setId("info");
		//eiviela.setFont(Font.font("Tahoma", FontWeight.LIGHT, 15));
		Label lisateksti = new Label(" Lisäkysymys: ");
		Label lisatekstiVinkki = new Label(" Mikä on lempi kaljasi? ");
		lisatekstiVinkki.setVisible(false);
		TextField lisakys = new TextField();

		grid.add(tervetuloa, 1, 0, 1, 1);
		grid.add(kayttajanimiteksti, 0, 3); // määritellään jokaiselle komponentille rivi ja sarake
		grid.add(kayttajanimibox, 1, 3);
		grid.add(salasanateksti, 0, 4);
		grid.add(salasanabox, 1, 4);
		grid.add(eiviela, 1, 12);
		
		//Siirretään tekstejä
		GridPane.setHalignment(kayttajanimiteksti, HPos.RIGHT);
		GridPane.setHalignment(salasanateksti, HPos.RIGHT);

		Button kirj = new Button(" Kirjaudu ");
		Button lop = new Button(" Lopeta");
		Button rek = new Button("Rekisteröidy");
		Button unohdit = new Button("Unohditko salasanasi? ");
		Button uusiSalasana = new Button("Palauta");

		Label actionTarget = new Label();
		grid.add(actionTarget, 1, 7);

		// toiminallisuus Rekisteröidy-napille
		rek.setOnAction(e -> {
			primaryStage.setScene(SignIn.signInScene(primaryStage, scene));
			kayttajanimibox.clear(); // pyyhkii käyttäjännimen ja salasanan siirtyessään rekisteröintiin
			salasanabox.clear();
			actionTarget.setText("");
			// piilotettaan lisäkysymys ja putsataan kenttä
			lisatekstiVinkki.setVisible(false);
			lisateksti.setVisible(false);
			lisakys.setVisible(false);
			lisakys.setText("");
			uusiSalasana.setVisible(false);

		});
		HBox hbox = new HBox(100);
		hbox.setAlignment(Pos.BOTTOM_RIGHT); // nappien sijainti ikkunassa
		hbox.getChildren().addAll(kirj, lop);

		grid.add(hbox, 1, 6);
		grid.add(rek, 1, 15);
		grid.add(unohdit, 1, 10);
		grid.add(lisateksti, 0, 7);
		grid.add(lisatekstiVinkki, 0, 8);
		grid.add(lisakys, 1, 8);
		grid.add(uusiSalasana, 1, 9);
		lisateksti.setVisible(false);
		lisakys.setVisible(false);
		uusiSalasana.setVisible(false);

		// toiminallisuus Kirjaudu-napille
		kirj.setOnAction(event -> {
			// jos kentät ovat tyhjät
			if (kayttajanimibox.getText().isEmpty() || salasanabox.getText().isEmpty()) {
				actionTarget.setId("loginFailed");
				actionTarget.setText("Kirjoittapas se käyttäjänimi ja salasana");
			}

			else {
				int i = Database.tarkastaTunnus(kayttajanimibox.getText(), salasanabox.getText());
				switch (i) {
				case 0:
					actionTarget.setId("loginFailed");
					actionTarget.setText("Virhe käyttäjätietoja tarkistaessa.");
					break;
				case 1:
					actionTarget.setId("loginFailed");
					actionTarget.setText("käyttäjänimeä ei löytynyt.");
					break;
				case 2:
					actionTarget.setId("loginSuccess");
					actionTarget.setText("Kirjautuminen onnistui!");
					String username = kayttajanimibox.getText();
					System.out.println("Login.java: Tämä on käyttäjänimi: " + username);
					Scene toMain = Paavalikko.start(primaryStage, username); // siirtymä Päävalikkoon ja välitys parametrina (käyttäjänimi)
					primaryStage.setScene(toMain);
					break;
				case 3:
					actionTarget.setId("loginFailed");
					actionTarget.setText("Väärä salasana tai käyttäjänimi.");
					salasanabox.clear();
					break;
				}
			}
		});

		lop.setOnAction(event -> {
			Platform.exit();
		});

		// toiminallisuus Unohditko sanasanan -napille
		unohdit.setOnAction(event -> {
			lisateksti.setVisible(true);
			lisatekstiVinkki.setVisible(true);
			lisakys.setVisible(true);
			uusiSalasana.setVisible(true);
			//lisakys.setPromptText("Mikä on lempikaljasi?"); testi?
		});
		
		// toiminnallisuus Palauta-napille
		uusiSalasana.setOnAction(e -> {
			String user = kayttajanimibox.getText();
			String answer = lisakys.getText();
			int result = Database.tarkistaLisakysymys(user, answer);
			switch (result) {
			case -1:	//Jokin muu virhe tapahtui
				actionTarget.setId("loginFailed");
				actionTarget.setText("Virhe käyttäjätietoja tarkistaessa.");
				break;
			case 0:	//Käyttäjänimeä ei löytynyt tietokannasta
				actionTarget.setId("loginFailed");
				actionTarget.setText("Käyttäjänimeä ei löytynyt.");
				break;
			case 1:	//Vastaus oli väärä
				actionTarget.setId("loginFailed");
				actionTarget.setText("Lisäkysymyksen vastaus oli väärin. Tarkista tiedot.");
				break;
			case 2:	//Vastaus oli oikea
				System.out.println(2 + ": Vastaus oikein");
				primaryStage.setScene(PasswordReset.passResetScene(primaryStage, scene, user));
				// piilotetaan kaikki turha
				lisateksti.setVisible(false);
				lisatekstiVinkki.setVisible(false);
				lisakys.setVisible(false);
				lisakys.setText("");
				uusiSalasana.setVisible(false);
				actionTarget.setVisible(false);
				break;
			}
		});		
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm()); // css-tiedosto
		primaryStage.setTitle(" Tervetuloa! ");
		try {
			primaryStage.getIcons().add(new Image("application/resources/Logo11.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		Thread t = new Thread(new Task() {
			
			@Override
			public void run() {
				Database.main();
				
			}
			
			@Override
			public void cancel() {
				// TODO Auto-generated method stub
				
			}
		});
		
		try {
			t.start();
		} finally {}

		launch(args);
	}
	
	class runDatabase implements Runnable {
		public runDatabase() {}
		
		public void run() {Database.main();}
	}
	
	/**
	 * Päävalikosta uloskirjautumiselle.
	 * 
	 * @param primaryStage
	 * @return login-scene
	 */
	public static Scene showLogin(Stage primaryStage) {

		ImageView tausta = new ImageView(new Image("application/resources/tausta7.jpeg"));
		StackPane keskus = new StackPane();
		
		final double SCREEN_WIDTH = 1200;
		final double SCREEN_HEIGHT = 800;

		GridPane grid = new GridPane(); // gridlayout luottaa rivi-sarake layoutiin, gridpane grid toimii pohjana
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(50, 50, 50, 50));

		grid.setGridLinesVisible(false);
		
		keskus.getChildren().addAll(tausta, grid);

		Scene scene = new Scene(keskus, SCREEN_WIDTH, SCREEN_HEIGHT);
		Text tervetuloa = new Text(" Tervetuloa! ");
		tervetuloa.setId("loginWelcome");
		tervetuloa.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		Label kayttajanimiteksti = new Label(" Käyttäjätunnus ");
		TextField kayttajanimibox = new TextField();
		Label salasanateksti = new Label(" Salasana ");
		PasswordField salasanabox = new PasswordField();
		Text unohditko = new Text(" Unohditko salasanasi?");
		unohditko.setId("infoText");
		//unohditko.setFont(Font.font("Tahoma", FontWeight.LIGHT, 15));
		Text eiviela = new Text("Ei vielä tunnuksia?");
		eiviela.setId("info");
		//eiviela.setFont(Font.font("Tahoma", FontWeight.LIGHT, 15));
		Label lisateksti = new Label(" Lisäkysymys: ");
		Label lisatekstiVinkki = new Label(" Mikä on lempi kaljasi? ");
		lisatekstiVinkki.setVisible(false);
		TextField lisakys = new TextField();

		grid.add(tervetuloa, 1, 0, 1, 1);
		grid.add(kayttajanimiteksti, 0, 3); // määritellään jokaiselle komponentille rivi ja sarake
		grid.add(kayttajanimibox, 1, 3);
		grid.add(salasanateksti, 0, 4);
		grid.add(salasanabox, 1, 4);
		grid.add(eiviela, 1, 12);
		
		//Siirretään tekstejä
		GridPane.setHalignment(kayttajanimiteksti, HPos.RIGHT);
		GridPane.setHalignment(salasanateksti, HPos.RIGHT);

		Button kirj = new Button(" Kirjaudu ");
		Button lop = new Button(" Lopeta");
		Button rek = new Button("Rekisteröidy");
		Button unohdit = new Button("Unohditko salasanasi? ");
		Button uusiSalasana = new Button("Palauta");

		Label actionTarget = new Label();
		grid.add(actionTarget, 1, 7);

		// toiminallisuus Rekisteröidy-napille
		rek.setOnAction(e -> {
			primaryStage.setScene(SignIn.signInScene(primaryStage, scene));
			kayttajanimibox.clear(); // pyyhkii käyttäjännimen ja salasanan siirtyessään rekisteröintiin
			salasanabox.clear();
			actionTarget.setText("");
			// piilotettaan lisäkysymys ja putsataan kenttä
			lisatekstiVinkki.setVisible(false);
			lisateksti.setVisible(false);
			lisakys.setVisible(false);
			lisakys.setText("");
			uusiSalasana.setVisible(false);

		});
		HBox hbox = new HBox(100);
		hbox.setAlignment(Pos.BOTTOM_RIGHT); // nappien sijainti ikkunassa
		hbox.getChildren().addAll(kirj, lop);

		grid.add(hbox, 1, 6);
		grid.add(rek, 1, 15);
		grid.add(unohdit, 1, 10);
		grid.add(lisateksti, 0, 7);
		grid.add(lisatekstiVinkki, 0, 8);
		grid.add(lisakys, 1, 8);
		grid.add(uusiSalasana, 1, 9);
		lisateksti.setVisible(false);
		lisakys.setVisible(false);
		uusiSalasana.setVisible(false);

		// toiminallisuus Kirjaudu-napille
		kirj.setOnAction(event -> {
			// jos kentät ovat tyhjät
			if (kayttajanimibox.getText().isEmpty() || salasanabox.getText().isEmpty()) {
				actionTarget.setId("loginFailed");
				actionTarget.setText("Kirjoittapas se käyttäjänimi ja salasana");
			}

			else {
				int i = Database.tarkastaTunnus(kayttajanimibox.getText(), salasanabox.getText());
				switch (i) {
				case 0:
					actionTarget.setId("loginFailed");
					actionTarget.setText("Virhe käyttäjätietoja tarkistaessa.");
					break;
				case 1:
					actionTarget.setId("loginFailed");
					actionTarget.setText("käyttäjänimeä ei löytynyt.");
					break;
				case 2:
					actionTarget.setId("loginSuccess");
					actionTarget.setText("Kirjautuminen onnistui!");
					String username = kayttajanimibox.getText();
					System.out.println("Login.java: Tämä on käyttäjänimi: " + username);
					Scene toMain = Paavalikko.start(primaryStage, username); // siirtymä Päävalikkoon ja välitys parametrina (käyttäjänimi)
					primaryStage.setScene(toMain);
					break;
				case 3:
					actionTarget.setId("loginFailed");
					actionTarget.setText("Väärä salasana tai käyttäjänimi.");
					salasanabox.clear();
					break;
				}
			}
		});

		lop.setOnAction(event -> {
			Platform.exit();
		});

		// toiminallisuus Unohditko sanasanan -napille
		unohdit.setOnAction(event -> {
			lisateksti.setVisible(true);
			lisatekstiVinkki.setVisible(true);
			lisakys.setVisible(true);
			uusiSalasana.setVisible(true);
			//lisakys.setPromptText("Mikä on lempikaljasi?"); testi?
		});
		
		// toiminnallisuus Palauta-napille
		uusiSalasana.setOnAction(e -> {
			String user = kayttajanimibox.getText();
			String answer = lisakys.getText();
			int result = Database.tarkistaLisakysymys(user, answer);
			switch (result) {
			case -1:	//Jokin muu virhe tapahtui
				actionTarget.setId("loginFailed");
				actionTarget.setText("Virhe käyttäjätietoja tarkistaessa.");
				break;
			case 0:	//Käyttäjänimeä ei löytynyt tietokannasta
				actionTarget.setId("loginFailed");
				actionTarget.setText("Käyttäjänimeä ei löytynyt.");
				break;
			case 1:	//Vastaus oli väärä
				actionTarget.setId("loginFailed");
				actionTarget.setText("Lisäkysymyksen vastaus oli väärin. Tarkista tiedot.");
				break;
			case 2:	//Vastaus oli oikea
				System.out.println(2 + ": Vastaus oikein");
				primaryStage.setScene(PasswordReset.passResetScene(primaryStage, scene, user));
				// piilotetaan kaikki turha
				lisateksti.setVisible(false);
				lisatekstiVinkki.setVisible(false);
				lisakys.setVisible(false);
				lisakys.setText("");
				uusiSalasana.setVisible(false);
				actionTarget.setVisible(false);
				break;
			}
		});		
		scene.getStylesheets().add("application/application.css"); // css-tiedosto
		primaryStage.setTitle(" Tervetuloa! ");
		return scene;
	}
}
