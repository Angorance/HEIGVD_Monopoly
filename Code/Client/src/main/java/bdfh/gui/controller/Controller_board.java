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
import javafx.stage.Stage;
import javafx.util.Pair;

import java.net.URL;
import java.util.*;

public class Controller_board implements Initializable, IWindow {
	
	
	@FXML private FlowPane case1;
	@FXML private FlowPane case2;
	@FXML private FlowPane case3;
	@FXML private FlowPane case4;
	@FXML private FlowPane case5;
	@FXML private FlowPane case6;
	@FXML private FlowPane case7;
	@FXML private FlowPane case8;
	@FXML private FlowPane case9;
	@FXML private FlowPane case10;
	@FXML private FlowPane case11;
	@FXML private FlowPane case12;
	@FXML private FlowPane case13;
	@FXML private FlowPane case14;
	@FXML private FlowPane case15;
	@FXML private FlowPane case16;
	@FXML private FlowPane case17;
	@FXML private FlowPane case18;
	@FXML private FlowPane case19;
	@FXML private FlowPane case20;
	@FXML private FlowPane case21;
	@FXML private FlowPane case22;
	@FXML private FlowPane case23;
	@FXML private FlowPane case24;
	@FXML private FlowPane case25;
	@FXML private FlowPane case26;
	@FXML private FlowPane case27;
	@FXML private FlowPane case28;
	@FXML private FlowPane case29;
	@FXML private FlowPane case30;
	@FXML private FlowPane case31;
	@FXML private FlowPane case32;
	@FXML private FlowPane case33;
	@FXML private FlowPane case34;
	@FXML private FlowPane case35;
	@FXML private FlowPane case36;
	@FXML private FlowPane case37;
	@FXML private FlowPane case38;
	@FXML private FlowPane case39;
	@FXML private FlowPane case40;
	
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
		
		public squareDisplayer(LightSquare square) {
			
			String famility = square.getFamily();
			
			fp = new FlowPane();
			ap = new AnchorPane();
			label_House = new Label("0");
			
			ap.getChildren().add(label_House);
			
			switch (famility) {
				case GameProtocol.SQUA_BROWN:
					break;
				case GameProtocol.SQUA_CYAN:
					break;
				case GameProtocol.SQUA_PINK:
					break;
				case GameProtocol.SQUA_ORANGE:
					break;
				case GameProtocol.SQUA_RED:
					break;
				case GameProtocol.SQUA_YELLOW:
					break;
				case GameProtocol.SQUA_GREEN:
					break;
				case GameProtocol.SQUA_BLUE:
					break;
				default:
					break;
				
			}
		}
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
		
		cases.add(case1);
		cases.add(case2);
		cases.add(case3);
		cases.add(case4);
		cases.add(case5);
		cases.add(case6);
		cases.add(case7);
		cases.add(case8);
		cases.add(case9);
		cases.add(case10);
		cases.add(case11);
		cases.add(case12);
		cases.add(case13);
		cases.add(case14);
		cases.add(case15);
		cases.add(case16);
		cases.add(case17);
		cases.add(case18);
		cases.add(case19);
		cases.add(case20);
		cases.add(case21);
		cases.add(case22);
		cases.add(case23);
		cases.add(case24);
		cases.add(case25);
		cases.add(case26);
		cases.add(case27);
		cases.add(case28);
		cases.add(case29);
		cases.add(case30);
		cases.add(case31);
		cases.add(case32);
		cases.add(case33);
		cases.add(case34);
		cases.add(case35);
		cases.add(case36);
		cases.add(case36);
		cases.add(case38);
		cases.add(case39);
		cases.add(case40);
		
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
		
		init();
		
		label_username.setText(Player.getInstance().getUsername());
		
		String[] color = { "RED", "BLUE", "GREEN", "YELLOW" };
		synchronized (GameHandler.getInstance()) {
			if (GameHandler.getInstance().getPlayers().isEmpty()) {
				try {
					GameHandler.getInstance().wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println(GameHandler.getInstance().getPlayers());
		int cnt = 0;
		for (int idPlayer : GameHandler.getInstance().getPlayers().keySet()) {
			pawnDisplay pd = new pawnDisplay(color[cnt]);
			cases.get(0).getChildren().add(pd);
			displayerList.put(idPlayer, pd);
			posPlayer.put(idPlayer, 0);
			
			Pair<String, Integer> pair = GameHandler.getInstance().getPlayers().get(idPlayer);
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
	}
}
