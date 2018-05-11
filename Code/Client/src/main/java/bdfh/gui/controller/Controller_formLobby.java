package bdfh.gui.controller;

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
	
	@FXML private AnchorPane paneFond;
	@FXML private Label random_label;
	@FXML private JFXTextField nameLobby;
	@FXML private JFXTextField moneyStart;
	@FXML private JFXComboBox<Integer> numberDice;
	@FXML private JFXCheckBox randomCheck;
	@FXML private JFXButton returnButton;
	@FXML private JFXButton accepteButton;
	
	private Controller_lobbyList cl;
	
	public Controller_formLobby(Controller_lobbyList cl) {
		this.cl = cl;
	}
	
	/**
	 * Create the lobby
	 */
	private void formValidation(){
		if(checkValidation()) {
			String name = nameLobby.getText();
			int nbrDice = numberDice.getValue();
			int money = Integer.parseInt(moneyStart.getText());
			boolean random = randomCheck.isSelected();
			
			//TODO création du lobby
			cl.createItem(5/*TODO mettre l'objet lobby ici*/);
		}
		
	}
	
	boolean checkValidation(){
		boolean check = true;
		String name = nameLobby.getText();
		String money =moneyStart.getText();
		
		if(name.isEmpty()){
			nameLobby.setStyle("-jfx-unfocus-color: red;");
			check = false;
		}
		
		if(money.isEmpty() || !isNumber(money)){
			moneyStart.setStyle("-jfx-unfocus-color: red;");
			check = false;
		}
		
		return check;
	}
	
	private boolean isNumber(String str){
		try {
			Integer.parseInt(str);
			return true;
		}catch (Exception e){
			return false;
		}
	}
	
	/**
	 * Return to the list of lobby page
	 */
	private void formReturn() {
		cl.createItem(null);
	}
	
	/**
	 * Generate de item in comboBox
	 */
	private void generateItemComboBox(){
		ObservableList<Integer> items = FXCollections.observableArrayList();
		
		/*TODO retrieve of the limit of the number of dice */
		for(int i = 2; i <= 4; ++i){
			items.add(i);
		}
		numberDice.setItems(items);
		numberDice.getSelectionModel().selectFirst();
	}
	
	
	@Override public void initialize(URL location, ResourceBundle resources) {
		generateItemComboBox();
		
		accepteButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent event) {
				formValidation();
			}
		});
		
		returnButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent event) {
				formReturn();
			}
		});
		
		nameLobby.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override public void handle(MouseEvent event) {
				
				nameLobby.setStyle("-jfx-unfocus-color: black;-fx-text-fill: black;");
			}
		});
		
		moneyStart.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override public void handle(MouseEvent event) {
				
				moneyStart.setStyle("-jfx-unfocus-color: black;-fx-text-fill: black;");
			}
		});
	}
	

}
