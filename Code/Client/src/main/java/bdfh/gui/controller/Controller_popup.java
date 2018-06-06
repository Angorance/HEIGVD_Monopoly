package bdfh.gui.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller_popup implements Initializable {
	
	@FXML private JFXButton button_useCard;
	@FXML private JFXButton button_rollDice;
	@FXML private JFXButton button_payTax;
	
	private Controller_board cb;
	
	public Controller_popup(Controller_board cb) {
		
		this.cb = cb;
	}
	
	private void useCard() {
		cb.useCard();
	}
	
	private void rollDice() {
		cb.rollDice();
	}
	
	private void payTax() {
		cb.payTax();
	}
	
	@Override public void initialize(URL location, ResourceBundle resources) {
		
		button_useCard.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent event) {
				useCard();
			}
		});
		
		button_rollDice.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent event) {
				rollDice();
			}
		});
		
		button_payTax.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent event) {
				payTax();
			}
		});
	}
}
