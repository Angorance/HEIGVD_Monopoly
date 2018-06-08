package bdfh.gui.controller;

import bdfh.logic.usr.Parameter;
import bdfh.logic.usr.Player;
import bdfh.serializable.BoundParameters;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller_formLobby implements Initializable {
	
	@FXML
	private AnchorPane paneFond;
	@FXML
	private JFXTextField moneyStart;
	@FXML
	private JFXComboBox<Integer> numberDice;
	@FXML
	private JFXCheckBox randomCheck;
	@FXML
	private JFXButton returnButton;
	@FXML
	private JFXButton accepteButton;
	@FXML
	private Label random_label;
	@FXML
	private JFXComboBox<String> mode;
	@FXML
	private JFXTextField time;
	
	private Controller_lobbyList cl;
	
	public Controller_formLobby(Controller_lobbyList cl) {
		
		this.cl = cl;
	}
	
	/**
	 * Create the lobby
	 */
	private void formValidation() {
		
		if (checkValidation()) {
			int nbrDice = numberDice.getValue();
			int money = Integer.parseInt(moneyStart.getText());
			int gameMode = Parameter.getModesValues().get(mode.getValue());
			boolean random = randomCheck.isSelected();
			int gameTime;
			
			if (gameMode == 1) {
				gameTime = Integer.parseInt(time.getText());
			} else {
				gameTime = 0;
			}
			
			Player.getInstance()
					.createLobby(nbrDice, money, gameMode, gameTime, random);
			cl.returnForm();
		}
		
	}
	
	boolean checkValidation() {
		
		boolean check = true;
		String money = moneyStart.getText();
		
		if (money.isEmpty() || !isNumber(money)
				|| Integer.parseInt(money) < Player.getBounds()
				.getMinMoneyAtTheStart() || Integer.parseInt(money) > Player
				.getBounds().getMaxMoneyAtTheStart()) {
			moneyStart.setStyle("-jfx-unfocus-color: red;");
			check = false;
		}
		
		if (mode.getSelectionModel().getSelectedItem()
				.equals(Parameter.getStringModes().get(1))) {
			String timeInt = time.getText();
			if (timeInt.isEmpty() || !isNumber(timeInt)
					|| Integer.parseInt(timeInt) < Player.getBounds()
					.getMinTime() || Integer.parseInt(timeInt) > Player
					.getBounds().getMaxTime()) {
				check = false;
				time.setStyle("-jfx-unfocus-color: red;");
			}
		}
		
		{
			return check;
		}
	}
	
	private boolean isNumber(String str) {
		
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Return to the list of lobby page
	 */
	private void formReturn() {
		
		cl.returnForm();
	}
	
	/**
	 * Generate de item in comboBox
	 */
	private void generateItemComboBox() {
		
		ObservableList<Integer> items = FXCollections.observableArrayList();
		BoundParameters b = Player.getBounds();
		int min = b.getMinDice();
		int max = b.getMaxDice();
		for (int i = min; i <= max; ++i) {
			items.add(i);
		}
		numberDice.setItems(items);
		numberDice.getSelectionModel().selectFirst();
		
		ObservableList<String> items2 = FXCollections.observableArrayList();
		items2.addAll(Parameter.getStringModes());
		mode.setItems(items2);
		mode.getSelectionModel().selectFirst();
		
	}
	
	private void checkMode() {
		
		boolean check = mode.getSelectionModel().getSelectedItem()
				.equals("Limite de temps");
		time.setVisible(check);
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		generateItemComboBox();
		
		BoundParameters b = Player.getBounds();
		
		moneyStart.setPromptText("De " + b.getMinMoneyAtTheStart() + " à " + b
				.getMaxMoneyAtTheStart());
		time.setPromptText(
				"De " + b.getMinTime() + " à " + b.getMaxTime() + " min");
		
		if (!b.isRandomGameGeneration()) {
			random_label.setVisible(false);
			randomCheck.setVisible(false);
		}
		
		accepteButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				formValidation();
			}
		});
		
		returnButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				formReturn();
			}
		});
		
		moneyStart.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				
				moneyStart.setStyle(
						"-jfx-unfocus-color: black;-fx-text-fill: black;");
			}
		});
		
		mode.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				checkMode();
				time.setStyle("-jfx-unfocus-color: black;");
			}
		});
		
		time.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				
				time.setStyle("-jfx-unfocus-color: black;");
			}
		});
	}
	
}
