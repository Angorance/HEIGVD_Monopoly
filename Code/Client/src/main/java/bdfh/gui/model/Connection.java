package bdfh.gui.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Connection extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Parent root = FXMLLoader.load(getClass().getResource("/view/loginRegister.fxml"));
		primaryStage.setTitle("Cheseaux-Poly");
		primaryStage.setScene(new Scene(root, 650, 400));
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public static void launcher() {
		
		launch();
	}
}
