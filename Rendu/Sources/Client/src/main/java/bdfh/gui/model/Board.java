package bdfh.gui.model;

import bdfh.logic.usr.Player;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.System.exit;

public class Board {
	
	public Board() {
		
		Parent root = null;
		Stage primaryStage = new Stage();
		try {
			root = FXMLLoader
					.load(getClass().getResource("/gui/view/board_test.fxml"));
			primaryStage.setTitle("Cheseaux-Poly");
			primaryStage.setScene(new Scene(root, 1300, 800));
			primaryStage.setOnCloseRequest(event -> {
				
				Player.getInstance().exitGame();
				
				exit(0);
			});
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
