package bdfh.gui.controller;

import bdfh.serializable.LightLobby;
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
import java.util.HashMap;
import java.util.List;
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
	
	private HashMap<Integer, LobbyDisplayer> displayerList;
	
	class LobbyDisplayer extends GridPane {
		
		private final static int MAX_PLAYER = 4;
		private final static int HEIGHT = 40;
		private final static int WIDTH = 40;
		private Label name;
		private Label nbPlayer;
		
		LightLobby lobby;
		
		public LobbyDisplayer(LightLobby lobby) {
			
			this.lobby = lobby;
			int nbPlayers = 0;
			for (String str : lobby.getUsernames()) {
				if (!str.isEmpty()) {
					nbPlayers++;
				}
			}
			name = new Label("Mon salon" + lobby.getID());
			nbPlayer = new Label(String.valueOf(nbPlayers) + "/" + MAX_PLAYER + " joueur");
			
			this.add(name, 0, 0);
			this.add(nbPlayer, 0, 1);
			
			this.setPrefSize(WIDTH, HEIGHT);
			
			this.setStyle("-fx-background-color:  gainsboro");
			
			this.setOnMouseClicked(new EventHandler<MouseEvent>() {
				
				@Override public void handle(MouseEvent event) {
					
					detailLobby(lobby);
				}
			});
		}
		
		public void redraw() {
			
			int nbPlayers = 0;
			for (String str : lobby.getUsernames()) {
				if (!str.isEmpty()) {
					nbPlayers++;
				}
			}
			nbPlayer.setText(String.valueOf(nbPlayers) + "/" + MAX_PLAYER + " joueur");
		}
		
	}
	
	private void detailLobby(LightLobby lobby) {
		
		List<String> players = lobby.getUsernames();
		List<Boolean> readys = lobby.getAreReady();
		Label[] labelsPlayers = { name_player1, name_player2, name_player3, name_player4 };
		Label[] labelsReadys = { ready_player1, ready_player2, ready_player3, ready_player4 };
		
		for (int i = 0; i < players.size(); ++i) {
			labelsPlayers[i].setText(players.get(i));
			labelsReadys[i].setText(readys.get(i) ? "Prêt" : "-");
		}
	}
	
	private void refresh(){
	
	}
	
	private void createLobby() {
		/* we load the form fxml*/
		FXMLLoader loader = new FXMLLoader(getClass().getResource(
				"/gui/view/formlobby.fxml"));
		
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
	
	public void createItem() {
		
		returnForm();
		
		//TODO création du lobbyDisplayer
		/*LobbyDisplayer ld = new LobbyDisplayer(l);
		lobby.getChildren().add(ld);
		displayerList.put(lobby.getID(),ld);*/
		
	}
	
	public void returnForm() {
		
		unloadForm();
	}
	
	
	@Override public void initialize(URL location, ResourceBundle resources) {
		
		paneform.setVisible(false);
		paneform.setMouseTransparent(true);
		
		displayerList = new HashMap<>();
		
		/*for(Lobby lobby : tableau de lobby){
			LobbyDisplayer ld = new LobbyDisplayer(lobby);
			lobby.getChildren().add(ld);
			displayerList.put(lobby.getID(),ld);
		}*/
		
		
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
