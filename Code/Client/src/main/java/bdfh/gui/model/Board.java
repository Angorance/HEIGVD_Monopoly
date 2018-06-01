package bdfh.gui.model;

import bdfh.net.client.Client;
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
			root = FXMLLoader.load(getClass().getResource(
					"/gui/view/board_test.fxml"));
			primaryStage.setTitle("Cheseaux-Poly");
			primaryStage.setScene(new Scene(root, 1000, 800));
			primaryStage.setOnCloseRequest(event -> {
				try {
					Client.getInstance().disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
				exit(0);
			});
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
