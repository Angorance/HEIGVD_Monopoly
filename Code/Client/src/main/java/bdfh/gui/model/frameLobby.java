package bdfh.gui.model;

import bdfh.net.client.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.System.exit;

public class frameLobby {
	
	public frameLobby() {
		
		Parent root = null;
		Stage primaryStage = new Stage();
		try {
			root = FXMLLoader.load(getClass().getResource("/view/lobbylist.fxml"));
			primaryStage.setTitle("Cheseaux-Poly");
			primaryStage.setScene(new Scene(root, 800, 600));
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
