package bdfh.gui.model;

import bdfh.gui.controller.Konami;
import bdfh.net.client.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.System.exit;

public class Connection extends Application {
	Konami konami = new Konami();
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Parent root = FXMLLoader.load(getClass().getResource(
				"/gui/view/loginRegister.fxml"));
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
		
		primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
			//System.out.println("pressed : " + event.getText());
			if(konami.typed(event.getCode())){
				//.out.println("accès zone admin");
				new zoneAdmin();
			}
		});
		
		primaryStage.show();
	}
	
	public static void launcher() {
		
		launch();
	}
}
