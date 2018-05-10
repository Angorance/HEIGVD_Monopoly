package bdfh.gui.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller_lobbyList implements Initializable {
	
	@FXML private VBox lobby;
	@FXML private Label name_player1;
	@FXML private Label name_player2;
	@FXML private Label name_player3;
	@FXML private Label name_player4;
	@FXML private Label ready_player1;
	@FXML private Label ready_player2;
	@FXML private Label ready_player3;
	@FXML private Label ready_player4;
	@FXML private JFXButton add_button;
	@FXML private AnchorPane paneform;
	@FXML private JFXButton join_button;
	@FXML private JFXButton quit_button;
	@FXML private JFXButton ready_button;
	
	class LobbyDisplayer extends GridPane {
		
		private final static int MAX_PLAYER = 4;
		private final static int HEIGHT = 40;
		private final static int WIDTH = 40;
		private Label name;
		private Label nbPlayer;
		
		public LobbyDisplayer() {
			
			int nbPlayers = 0;
			name = new Label("Mon salon");
			nbPlayer = new Label(String.valueOf(nbPlayers) + "/" + MAX_PLAYER + " joueur");
			
			this.add(name, 0, 0);
			this.add(nbPlayer, 0, 1);
			
			this.setPrefSize(WIDTH, HEIGHT);
			
			this.setStyle("-fx-background-color:  gainsboro");
			
			this.setOnMouseClicked(new EventHandler<MouseEvent>() {
				
				@Override public void handle(MouseEvent event) {
					
					System.out.println(name.getText());
				}
			});
		}
		
	}
	
	private void createLobby() {
		/* we load the form fxml*/
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/formlobby.fxml"));
		
		/*Create a instance of the controller of bank account form*/
		Controller_formLobby cba = new Controller_formLobby(this);
		
		/*Sets the controller associated with the root object*/
		loader.setController(cba);
		paneform.setVisible(true);
		paneform.setMouseTransparent(false);
		paneform.getChildren().clear();
		try {
			AnchorPane pane = loader.load();
			paneform.getChildren().add(pane);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void unloadForm() {
		
		paneform.getChildren().clear();
		paneform.setVisible(false);
		paneform.setMouseTransparent(true);
	}
	
	public void createItem(Object result) {
		
		unloadForm();
		
		if (result != null) {
			//TODO création du lobbyDisplayer avec l'objet result
			lobby.getChildren().add(new LobbyDisplayer());
			
		}
	}
	
	
	@Override public void initialize(URL location, ResourceBundle resources) {
		
		paneform.setVisible(false);
		paneform.setMouseTransparent(true);
		
		add_button.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent event) {
				
				createLobby();
			}
		});
		
		join_button.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent event) {
				//TODO
			}
		});
		
		quit_button.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent event) {
				//TODO
			}
		});
		
		ready_button.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent event) {
				//TODO
			}
		});
		
	}
}
