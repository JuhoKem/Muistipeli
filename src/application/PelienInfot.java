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
				"Kognitiiviset taidot ovat aivojen keskeiset taidot, joita ihminen hy�dynt�� esimerkiksi lukemisessa, "
				+ "ajattelussa, muistamisessa ja huomion keskitt�misess�. Jokainen kognitiivinen taito on ihmiselle eritt�in t�rke� uuden "
				+ "tiedon prosessoinnissa ja t�st� johtuen jokap�iv�isess� el�m�ss� n�iden taitojen t�rkeytt� ei kannata aliarvioida. "
				+ "\n\nT�m�n sovelluksen tarkoituksena on simuloida aivojasi ja n�in yll�pit�� ja harjaannuttaa yll� mainittuja taitoja, jotta "		
				+ "voit paremmin suoriutua el�m�si haasteista! ");
		ohjelaatikko.setEditable(false);
		ohjelaatikko.setWrapText(true);
        
        Button closeButton = new Button(" Selv� ");
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
