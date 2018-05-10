package bdfh.gui.model;

import bdfh.net.client.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.System.exit;

public class Connection extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Parent root = FXMLLoader.load(getClass().getResource("/view/loginRegister.fxml"));
		primaryStage.setTitle("Cheseaux-Poly");
		primaryStage.setScene(new Scene(root, 600, 400));
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
	}
	
	public static void launcher() {
		
		launch();
	}
}
