package application;

import javafx.animation.AnimationTimer;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class YlaPalkkiMuistiPeli {
	StackPane stack;
	HBox ylapalkki;
	HBox alapalkki;
	private AnimationTimer kello;
    private Label          kelloL = new Label("Kulunut aika");
    Label kelloAikaL = new Label("00:00");
    private Label pisteetL = new Label("Pisteet");
    private Label pisteetArvoL = new Label("0");
    private int            sekuntit;
    private int kokonaispisteet;
    private pelipisteetMP pelipisteetolio;
    private int minuutit = 0;
    
	
	public YlaPalkkiMuistiPeli(StackPane stack, HBox ylapalkki, HBox alapalkki, pelipisteetMP pelipisteetolio, Stage primaryStage,
			String username) {
		
	this.stack = stack;	
	this.ylapalkki = ylapalkki;
	this.alapalkki = alapalkki;
	this.pelipisteetolio = pelipisteetolio;
	
	
	//Asetetaan pisteiden tarvitsemat komponentit	
	pisteetL.setFont(new Font("Arial", 65));	
	pisteetL.setStyle("-fx-font-weight: bold");	
	pisteetArvoL.setStyle("-fx-font-weight: bold");
	pisteetArvoL.setFont(new Font("Arial", 30));
	
	VBox pisteetVbox = new VBox();
	HBox pisteetHbox = new HBox();	//Koska allekkain kaksi labelia
	pisteetHbox.getChildren().add(pisteetArvoL);
	pisteetHbox.setAlignment(Pos.CENTER);
	pisteetVbox.getChildren().addAll(pisteetL, pisteetHbox);
	//pisteetVbox.setPadding(new Insets(0, 0, 0, 30));

	//Asetetaan kellon tarvitsemat komponentit
	kelloL.setFont(new Font("Arial", 65));
	kelloL.setStyle("-fx-font-weight: bold");
	kelloAikaL.setFont(new Font("Arial", 30));
	kelloAikaL.setStyle("-fx-font-weight: bold");
	
	VBox kelloLaatikko = new VBox();
	HBox aikaLaatikko = new HBox(); //Koska allekkain kaksi labelia
	aikaLaatikko.getChildren().add(kelloAikaL);
	aikaLaatikko.setAlignment(Pos.CENTER);
	kelloLaatikko.getChildren().addAll(kelloL, aikaLaatikko);
	
	
	ImageView vaihto = new ImageView(new Image("application/resources/nuolet2.png"));
	vaihto.setFitHeight(40);
	vaihto.setFitWidth(40);
	
	//Luodaan yl‰palkkiin tarvittavat komponentit
	Button vaihdaB = new Button(" Vaihda parienm‰‰r‰‰", vaihto);	
	vaihdaB.setStyle("-fx-font-size: 12pt;");	
	ylapalkki.getChildren().add(vaihdaB);
	ylapalkki.getChildren().add(kelloLaatikko);
	ylapalkki.getChildren().add(pisteetVbox);
	
	//YLƒ REUNAA ALASPƒIN JOTTA SAADAA PELI KENTTƒ KESKITETTYƒ
	ylapalkki.setPadding(new Insets(10, 0, 40, 10));
	ylapalkki.setAlignment(Pos.TOP_LEFT);
	//M‰‰ritt‰‰ miss‰ kohdassa kello n‰kyy
	ylapalkki.setSpacing(290);

	ImageView koti = new ImageView(new Image("application/resources/koti.png"));
	koti.setFitHeight(30);
	koti.setFitWidth(30);
	
	ImageView raksi = new ImageView(new Image("application/resources/raksi.png"));
	raksi.setFitHeight(30);
	raksi.setFitWidth(30);
	
	//Alapalkkiin palaa p‰‰ikkunaan ja lopeta napit.
	Button p‰‰ikkunaanB = new Button(" Palaa p‰‰ikkunaan", koti);
	
	//Siirtym‰ p‰‰ikkunaan
	p‰‰ikkunaanB.setOnAction(e -> {
		Scene toMain = Paavalikko.start(primaryStage, username);
		primaryStage.setScene(toMain);
	});
	
	p‰‰ikkunaanB.setStyle("-fx-font-size: 10pt;");
	Button lopetaB = new Button(" Lopeta", raksi);
	lopetaB.setStyle("-fx-font-size: 10pt;");
	alapalkki.getChildren().addAll(p‰‰ikkunaanB, lopetaB);
	alapalkki.setPadding(new Insets(10, 0, 20, 10));
	//lopetaB napin paikka m‰‰r‰ytyy t‰ll‰
	alapalkki.setSpacing(885);
	alapalkki.setAlignment(Pos.BOTTOM_LEFT);
	
	//kellomuuttuja renderˆi koko ajan t‰t‰ muuttujaa ja kasvattaa labelissa olevan muuttujan kokoa
	kello = new AnimationTimer() {
        private long lastTime = 0;

        @Override
        public void handle(long now) {
            if (lastTime != 0) {
                if (now > lastTime + 1_000_000_000) {
                    sekuntit++;
                    //Lis‰t‰‰n minuutteja
                    if(sekuntit>=60) {
                    	sekuntit=0;
                    	minuutit++;
                    }
                    //K‰sitell‰‰n ett‰ kellon ulkon‰kˆ n‰ytt‰‰ oikealta eli kaksi lukua minuuteissa ja sekunneissa kokoaja
                    if(sekuntit<10 && minuutit<10) {
                    	kelloAikaL.setText("0"+minuutit+":0"+sekuntit );
                    }
                    else if(minuutit<10) {
                    	kelloAikaL.setText("0"+minuutit+":"+sekuntit );
                    }
                    else if(sekuntit<10) {
                    	kelloAikaL.setText(minuutit+":0"+sekuntit );
                    }
                    else {
                    	kelloAikaL.setText(minuutit+":"+sekuntit );
                    }

                    lastTime = now;
                    pelipisteetolio.vahennaPisteet();
                    pisteetArvoL.setText(""+pelipisteetolio.getvalipisteet());
                }
            } else {
                lastTime = now;

            }
        }

    };
       

	
	//Lopeta toiminnallisuus lopeta -napille
	lopetaB.setOnAction(actionEvent -> Platform.exit());
	
	//vaihda parien m‰‰r‰‰ napin toiminnallisuus
	vaihdaB.setOnAction(new EventHandler<ActionEvent>() {
      	 
        @Override
        public void handle(ActionEvent event) {
            changeTop();
        }

    });
	}
	
	
	//Vaihdetaan vaikeustaso n‰kyviin, jos k‰ytt‰j‰ painaa vaiha parien m‰‰r‰ nappia
    private void changeTop() {
        ObservableList<Node> childs = this.stack.getChildren();
 
        if (childs.size() > 1) {
            //
            Node topNode = childs.get(childs.size()-1);
            topNode.toBack();
            pysaytaKello();
            pelipisteetolio.nollaapisteet();
        }
    }
    
    public void kelloPaalle() {
        kello.start();
    }
    
    public int pysaytaKello() {
        kello.stop();
        int aika = sekuntit;
        sekuntit = 0;
        minuutit=0;
        
        return aika;
    }
    public void paivitaPisteet() {
    	pisteetArvoL.setText(""+pelipisteetolio.getvalipisteet());
    }

}


