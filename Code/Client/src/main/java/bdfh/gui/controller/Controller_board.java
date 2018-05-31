package bdfh.gui.controller;

import bdfh.gui.model.windowManager;
import bdfh.logic.usr.Player;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
	@FXML private JFXButton buy_button;
	@FXML private JFXButton sell_button;
	@FXML private JFXButton hyp_button;
	@FXML private JFXButton rollDice_button;
	@FXML private JFXButton endTurn_button;
	
	private ArrayList<FlowPane> cases = new ArrayList<>();
	
	private HashMap<Integer,pawnDisplay> displayerList;
	
	private int pos = 0;
	
	private Stage thisStage = null;
	
	
	public static void endGame(){
		windowManager.getInstance().displayLobbyList();
	}
	
	@Override public void hide() {
		if(thisStage == null){
			thisStage = (Stage) buy_button.getScene().getWindow();
		}
		thisStage.hide();
	}
	
	@Override public void show() {
		if(thisStage == null){
			thisStage = (Stage) buy_button.getScene().getWindow();
		}
		thisStage.show();
	}
	
	public class pawnDisplay extends AnchorPane {
		
		public pawnDisplay(String color/*TODO Mettre le joueur en parametre*/) {
			this.setStyle(
					"-fx-pref-width: 25px; -fx-background-radius: 25px; -fx-pref-height: 25px;-fx-border-radius: 25px; -fx-border-width: 4px; -fx-background-color: "
							+ color + ";");
		}
	}
	
	private void initCases() {
		
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
	}
	
	public void movePawn(int idPlayer, List<Integer> dices) {
		int sumDice = 0;
		for(int i : dices){
			sumDice += i;
		}
		
		int tmp = (pos + sumDice)%40;
		pawnDisplay tmpPD = displayerList.get(idPlayer);
		cases.get(pos).getChildren().removeAll(tmpPD);
		cases.get(tmp).getChildren().add(tmpPD);
		pos = tmp;
	}
	
	private void rollDice(){
		Player.getInstance().rollDice();
	}
	
	private void endTurn() {
	
	}
	
	@Override public void initialize(URL location, ResourceBundle resources) {
		
		displayerList = new HashMap<>();
		
		initCases();
		
		pawnDisplay pd = new pawnDisplay("RED");
		cases.get(0).getChildren().add(pd);
		displayerList.put(1,pd);
		
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
	}
}
