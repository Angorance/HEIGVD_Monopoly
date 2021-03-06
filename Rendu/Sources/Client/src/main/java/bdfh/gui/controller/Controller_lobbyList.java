package bdfh.gui.controller;

import bdfh.gui.model.Board;
import bdfh.gui.model.windowManager;
import bdfh.logic.usr.Player;
import bdfh.net.client.Notification;
import bdfh.serializable.LightLobbies;
import bdfh.serializable.LightLobby;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class Controller_lobbyList implements Initializable, IWindow {
	
	@FXML private Label nameLobby;
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
	@FXML private AnchorPane detailLobby;
	
	private HashMap<Integer, LobbyDisplayer> displayerList;
	
	private LightLobby lightLobby;
	
	private Stage thisStage = null;
	
	class LobbyDisplayer extends GridPane {
		
		private final static int MAX_PLAYER = 4;
		private final static int HEIGHT = 40;
		private final static int WIDTH = 40;
		private Label name;
		private Label nbPlayer;
		
		private LightLobby lLobby;
		
		public LobbyDisplayer(LightLobby lobby) {
			
			this.lLobby = lobby;
			int nbPlayers = 0;
			for (String str : lobby.getUsernames()) {
				if (!str.isEmpty()) {
					nbPlayers++;
				}
			}
			name = new Label("Salon " + lLobby.getID());
			nbPlayer = new Label(String.valueOf(nbPlayers) + "/" + MAX_PLAYER + " joueurs");
			
			this.add(name, 0, 0);
			this.add(nbPlayer, 0, 1);
			
			this.setPrefSize(WIDTH, HEIGHT);
			
			this.setStyle("-fx-background-color:  gainsboro");
			
			this.setOnMouseClicked(new EventHandler<MouseEvent>() {
				
				@Override public void handle(MouseEvent event) {
					
					detailLobby(lLobby);
				}
			});
		}
		
		public void redraw() {
			
			int nbPlayers = 0;
			
			for (String str : lLobby.getUsernames()) {
				if (!str.isEmpty()) {
					nbPlayers++;
				}
			}
			nbPlayer.setText(String.valueOf(nbPlayers) + "/" + MAX_PLAYER + " joueurs");
		}
		
	}
	
	public void updateLobbyList(){
		
		Platform.runLater(()->{
			add_button.setDisable(false);
			join_button.setDisable(false);
			quit_button.setDisable(true);
			ready_button.setDisable(true);
			
			lobby.getChildren().clear();
			displayerList.clear();
			
			for (LightLobby l : LightLobbies.getInstance().getLobbies().values()) {
				LobbyDisplayer ld = new LobbyDisplayer(l);
				lobby.getChildren().add(ld);
				displayerList.put(l.getID(), ld);
			}
			
			detailLobby.setVisible(false);
		});

	}
	
	/**
	 * Mise à jour du lobby. Si le joueur à cliquer dans un lobby
	 * il met a jour aussi les information de se lobby
	 *
	 * @param l lobby à mettre à jour
	 */
	public void updateLobby(LightLobby l) {
		
		Platform.runLater(() -> {
			displayerList.get(l.getID()).redraw();
			
			if (lightLobby != null && l.getID() == lightLobby.getID()) {
				detailLobby(l);
			}
		});
	}
	
	/**
	 * Ajout du lobby dans la liste des lobby
	 *
	 * @param l lobby à ajouter
	 */
	public void newLobby(LightLobby l) {
		
		Platform.runLater(() -> {
			
			LobbyDisplayer ld = new LobbyDisplayer(l);
			lobby.getChildren().add(ld);
			displayerList.put(l.getID(), ld);
			
			
			if (Player.getInstance().getLobbyID() != -1) {
				join_button.setDisable(true);
			}
		});
	}
	
	public void clearLobbies() {
		
		Platform.runLater(() -> {
			
			lobby.getChildren().clear();
		});
	}
	
	/**
	 * Suppression d'un lobby de la liste
	 *
	 * @param l lobby à supprimer
	 */
	public void removeLobby(LightLobby l) {
		
		Platform.runLater(() -> {
			if (l.getUsernames().size() == 0 && l.getID() == lightLobby.getID()) {
				detailLobby.setVisible(false);
			}
			lobby.getChildren().removeAll(displayerList.get(l.getID()));
			displayerList.remove(l.getID());
		});
	}
	
	public static void startGame() {
		
		Platform.runLater(() -> {
			windowManager wm = windowManager.getInstance();
			
			if (!wm.hasGameBoard()) {
				new Board();
			}
			
			wm.displayBoard();
		});
	}
	
	/**
	 * hide the main frame
	 */
	@Override public void hide() {
		
		if (thisStage == null) {
			thisStage = (Stage) lobby.getScene().getWindow();
		}
		thisStage.hide();
	}
	
	/**
	 * show the window and reset the display
	 */
	@Override public void show() {
		
		if (thisStage == null) {
			thisStage = (Stage) lobby.getScene().getWindow();
		}
		thisStage.show();
	}
	
	
	/**
	 * Affichage du detail d'un lobby
	 *
	 * @param lobby lobby que l'on veut afficher les détails
	 */
	private void detailLobby(LightLobby lobby) {
		
		lightLobby = lobby;
		
		nameLobby.setText("Salon " + lobby.getID());
		int currendIDLoby = Player.getInstance().getLobbyID();
		
		detailLobby.setVisible(true);
		
		// Handle the visibility of the buttons
		if (currendIDLoby == -1) {
			join_button.setDisable(false);
			quit_button.setDisable(true);
			ready_button.setDisable(true);
			
		} else if (currendIDLoby != lightLobby.getID()) {
			join_button.setDisable(true);
			quit_button.setDisable(true);
			ready_button.setDisable(true);
			
		} else {
			join_button.setDisable(true);
			quit_button.setDisable(false);
			ready_button.setDisable(false);
		}
		
		List<String> players = lobby.getUsernames();
		List<Boolean> readys = lobby.getAreReady();
		Label[] labelsPlayers = { name_player1, name_player2, name_player3, name_player4 };
		Label[] labelsReadys = { ready_player1, ready_player2, ready_player3, ready_player4 };
		
		for (int i = 0; i < labelsPlayers.length; ++i) {
			labelsPlayers[i].setText("-");
			labelsReadys[i].setText("-");
		}
		
		for (int i = 0; i < players.size(); ++i) {
			labelsPlayers[i].setText(players.get(i));
			labelsReadys[i].setText(readys.get(i) ? "Prêt" : "-");
		}
		
	}
	
	/**
	 * Methode qui "crée" un lobby. Ca appelle le formulaire de création
	 * d'un lobby
	 */
	private void createLobby() {
		/* we load the form fxml*/
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/formlobby.fxml"));
		
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
	
	/**
	 * ferme de formulaire
	 */
	private void unloadForm() {
		
		paneform.getChildren().clear();
		paneform.setVisible(false);
		paneform.setMouseTransparent(true);
	}
	
	/**
	 * Methode qui permet de joindre un lobby
	 */
	private void join() {
		
		/*Si le joueur est dans aucun lobby*/
		if (Player.getInstance().getLobbyID() == -1) {
			
			/*On laisse la possibilité de rejoindre*/
			join_button.setDisable(true);
			quit_button.setDisable(false);
			ready_button.setDisable(false);
			
			/*On rejoint le lobby*/
			boolean check = Player.getInstance().joinLobby(lightLobby.getID());
			add_button.setDisable(check);
		}
	}
	
	/**
	 * Methode qui permet de se mettre prêt lorsque l'on a rejoint un lobby
	 */
	private void ready() {
		
		/*Si le player n'est pas prêt*/
		if (!Player.getInstance().isReady()) {
			
			/*On désactive le bouton ready*/
			ready_button.setDisable(true);
			
			/*On met le joueur prêt*/
			Player.getInstance().setReady();
		}
	}
	
	/**
	 * Methode qui permet de quitter un lobby lorsque l'on est a l'intérieur
	 */
	private void quit() {
		
		//Si le joueur est dans un lobby
		if (Player.getInstance().getLobbyID() != -1) {
			
			/*Si la liste des user du lobby est vide et que la id du lobby dans lequel on a cliquer est égal a notre lobby*/
			if (lightLobby.getUsernames().isEmpty() && lightLobby.getID() == Player.getInstance().getLobbyID()) {
				detailLobby.setVisible(false);
			}
			
			/*On réactive les boutons*/
			join_button.setDisable(false);
			ready_button.setDisable(false);
			
			boolean check = Player.getInstance().quitLobby();
			add_button.setDisable(!check);
		}
	}
	
	public void validationForm(){
		
		add_button.setDisable(true);
		unloadForm();
	}
	
	public void returnForm() {
		
		unloadForm();
	}
	
	@Override public void initialize(URL location, ResourceBundle resources) {
		
		paneform.setVisible(false);
		paneform.setMouseTransparent(true);
		
		displayerList = new HashMap<>();
		
		detailLobby.setVisible(false);
		
		for (LightLobby l : LightLobbies.getInstance().getLobbies().values()) {
			LobbyDisplayer ld = new LobbyDisplayer(l);
			lobby.getChildren().add(ld);
			displayerList.put(l.getID(), ld);
		}
		
		
		add_button.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent event) {
				
				createLobby();
			}
		});
		
		join_button.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent event) {
				
				join();
			}
		});
		
		quit_button.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent event) {
				
				quit();
			}
		});
		
		ready_button.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent event) {
				
				ready();
			}
		});
		
		Notification.getInstance().addSubscriber(this);
		
		windowManager.getInstance().setLobbyList(this);
	}
}
