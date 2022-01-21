package application;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class PelienInfot  {

	public static void display(String title, String message) {
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(550);
        window.setMinHeight(250);
        
        TextArea ohjelaatikko = new TextArea(
				"Kognitiiviset taidot ovat aivojen keskeiset taidot, joita ihminen hyödyntää esimerkiksi lukemisessa, "
				+ "ajattelussa, muistamisessa ja huomion keskittämisessä. Jokainen kognitiivinen taito on ihmiselle erittäin tärkeä uuden "
				+ "tiedon prosessoinnissa ja tästä johtuen jokapäiväisessä elämässä näiden taitojen tärkeyttä ei kannata aliarvioida. "
				+ "\n\nTämän sovelluksen tarkoituksena on simuloida aivojasi ja näin ylläpitää ja harjaannuttaa yllä mainittuja taitoja, jotta "		
				+ "voit paremmin suoriutua elämäsi haasteista! ");
		ohjelaatikko.setEditable(false);
		ohjelaatikko.setWrapText(true);
        
        Button closeButton = new Button(" Selvä ");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(ohjelaatikko, closeButton);
        layout.setAlignment(Pos.CENTER);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
	

}
	public static void main(String[] args) {


	}

}
