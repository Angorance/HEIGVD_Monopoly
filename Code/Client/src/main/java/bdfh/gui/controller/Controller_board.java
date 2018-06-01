package bdfh.gui.controller;

import bdfh.gui.model.windowManager;
import bdfh.logic.usr.Player;
import bdfh.net.client.GameHandler;
import bdfh.net.protocol.GameProtocol;
import bdfh.serializable.LightSquare;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.apache.commons.lang3.tuple.MutablePair;

import java.net.URL;
import java.util.*;

public class Controller_board implements Initializable, IWindow {
	@FXML private GridPane board;
	
	@FXML private Label label_username;
	@FXML private Label label_player1;
	@FXML private Label label_player2;
	@FXML private Label label_player3;
	@FXML private Label label_player4;
	@FXML private Label label_capital1;
	@FXML private Label label_capital2;
	@FXML private Label label_capital3;
	@FXML private Label label_capital4;
	
	@FXML private JFXButton buy_button;
	@FXML private JFXButton sell_button;
	@FXML private JFXButton hyp_button;
	@FXML private JFXButton rollDice_button;
	@FXML private JFXButton endTurn_button;
	
	private static ArrayList<FlowPane> cases = new ArrayList<>();
	private Label[] labelPlayers = new Label[4];
	private Label[] labelCapitals = new Label[4];
	private static HashMap<Integer, pawnDisplay> displayerList = new HashMap<>();
	
	private static HashMap<Integer, Integer> posPlayer = new HashMap<>();
	
	
	private Stage thisStage = null;
	
	public class pawnDisplay extends AnchorPane {
		
		public pawnDisplay(String color) {
			
			this.setStyle(
					"-fx-pref-width: 25px; -fx-background-radius: 25px; -fx-pref-height: 25px;-fx-border-radius: 25px; -fx-border-width: 4px; -fx-background-color: "
							+ color + ";");
		}
	}
	
	public class squareDisplayer extends BorderPane {
		
		private FlowPane fp;
		private AnchorPane ap;
		private Label label_House;
		
		private void add(String color, FlowPane fp, AnchorPane ap) {
			
			ap.setStyle("-fx-background-color: " + color + "; -fx-border-color: BLACK ; -fx-border-width: 1px");
			this.setTop(ap);
			this.setCenter(fp);
		}
		
		public squareDisplayer(LightSquare square) {
			
			String famility = square.getFamily();
			this.setStyle("-fx-border-color: BLACK; -fx-border-width: 1px");
			
			fp = new FlowPane();
			ap = new AnchorPane();
			label_House = new Label("0");
			ap.getChildren().add(label_House);
			
			switch (famility) {
				case GameProtocol.SQUA_BROWN:
					add(famility, fp, ap);
					break;
				case GameProtocol.SQUA_CYAN:
					add(famility, fp, ap);
					break;
				case GameProtocol.SQUA_PINK:
					add(famility, fp, ap);
					break;
				case GameProtocol.SQUA_ORANGE:
					add(famility, fp, ap);
					break;
				case GameProtocol.SQUA_RED:
					add(famility, fp, ap);
					break;
				case GameProtocol.SQUA_YELLOW:
					add(famility, fp, ap);
					break;
				case GameProtocol.SQUA_GREEN:
					add(famility, fp, ap);
					break;
				case GameProtocol.SQUA_BLUE:
					add(famility, fp, ap);
					break;
				default:
					this.setCenter(fp);
					break;
			}
		}
		
		public FlowPane getFP(){
			return fp;
		}
	}
	
	public void updateBoard(){
		
		Platform.runLater(() -> {
			int cnt = 0;
			for (int idPlayer : GameHandler.getInstance().getPlayers().keySet()) {
				int capital = GameHandler.getInstance().getPlayers().get(idPlayer).getValue();
				labelCapitals[cnt].setText(String.valueOf(capital));
				cnt++;
			}
		});
	}
	
	public static void endGame() {
		
		windowManager.getInstance().displayLobbyList();
	}
	
	@Override public void hide() {
		
		if (thisStage == null) {
			thisStage = (Stage) buy_button.getScene().getWindow();
		}
		thisStage.hide();
	}
	
	@Override public void show() {
		
		if (thisStage == null) {
			thisStage = (Stage) buy_button.getScene().getWindow();
		}
		thisStage.show();
	}
	
	private void init() {
		
		int col = 0;
		int row = 10;
		int line = 0;
		int nbCase = 0;
		
		for(LightSquare square : GameHandler.getInstance().getBoard().getSquares()){
			
			squareDisplayer sd = new squareDisplayer(square);
			cases.add(sd.getFP());
			if(nbCase == 10){
				nbCase = 0;
				line++;
			}
			if(line == 0) {
				board.add(sd, col, row--);
			}else if(line == 1){
				board.add(sd, col++, row);
			}else if(line == 2){
				board.add(sd, col, row++);
			}else {
				board.add(sd, col--, row);
			}
			nbCase++;
		}
		
		
		labelPlayers[0] = label_player1;
		labelPlayers[1] = label_player2;
		labelPlayers[2] = label_player3;
		labelPlayers[3] = label_player4;
		
		labelCapitals[0] = label_capital1;
		labelCapitals[1] = label_capital2;
		labelCapitals[2] = label_capital3;
		labelCapitals[3] = label_capital4;
		
	}
	
	public void movePawn(int idPlayer, List<Integer> dices) {
		
		Platform.runLater(() -> {
			int pos = posPlayer.get(idPlayer);
			int sumDice = 0;
			
			for (int i : dices) {
				sumDice += i;
			}
			
			int tmp = (pos + sumDice) % 40;
			pawnDisplay tmpPD = displayerList.get(idPlayer);
			cases.get(pos).getChildren().removeAll(tmpPD);
			cases.get(tmp).getChildren().add(tmpPD);
			posPlayer.put(idPlayer, tmp);
			if (Player.getInstance().isMyTurn()) {
				rollDice_button.setDisable(true);
				endTurn_button.setDisable(false);
			}
		});
	}
	
	private void rollDice() {
		
		Player.getInstance().rollDice();
	}
	
	private void endTurn() {
		
		Player.getInstance().endTurn();
		
	}
	
	public void notifyTurn() {
		
		if (Player.getInstance().isMyTurn()) {
			rollDice_button.setDisable(false);
			endTurn_button.setDisable(true);
		} else {
			rollDice_button.setDisable(true);
			endTurn_button.setDisable(true);
		}
	}
	
	@Override public void initialize(URL location, ResourceBundle resources) {
		
		rollDice_button.setDisable(true);
		endTurn_button.setDisable(true);
		label_username.setText(Player.getInstance().getUsername());
		
		synchronized (GameHandler.getInstance()){
			if(GameHandler.getInstance().getBoard() == null){
				try {
					GameHandler.getInstance().wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		init();
		
		
		String[] color = { "RED", "BLUE", "GREEN", "YELLOW" };
		
		int cnt = 0;
		for (int idPlayer : GameHandler.getInstance().getPlayers().keySet()) {
			pawnDisplay pd = new pawnDisplay(color[cnt]);
			cases.get(0).getChildren().add(pd);
			displayerList.put(idPlayer, pd);
			posPlayer.put(idPlayer, 0);
			
			MutablePair<String, Integer> pair = GameHandler.getInstance().getPlayers().get(idPlayer);
			String username = pair.getKey();
			int capital = pair.getValue();
			
			labelPlayers[cnt].setText(username);
			labelCapitals[cnt].setText(String.valueOf(capital));
			cnt++;
		}
		
		rollDice_button.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent event) {
				
				rollDice();
			}
		});
		
		endTurn_button.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent event) {
				
				endTurn();
			}
		});
		
		windowManager.getInstance().setBoard(this);
		
		GameHandler.getInstance().setSub(this);
		synchronized (GameHandler.getInstance()) {
			GameHandler.getInstance().notify();
		}
	}
}
