package bdfh.gui.controller;

import bdfh.logic.usr.Player;
import bdfh.net.client.Client;
import bdfh.serializable.BoundParameters;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller_zoneAdmin implements Initializable {
	
	@FXML private JFXCheckBox random;
	@FXML private JFXTextField minCapital;
	@FXML private JFXTextField maxCapital;
	@FXML private JFXTextField minDice;
	@FXML private JFXTextField maxDice;
	@FXML private JFXTextField minTime;
	@FXML private JFXTextField maxTime;
	@FXML private JFXButton validate;
	
	public void bound() {
		if (checkForm()) {
			boolean rdm = random.isSelected();
			int minCap = Integer.parseInt(minCapital.getText());
			int maxCap = Integer.parseInt(maxCapital.getText());
			int minDic = Integer.parseInt(minDice.getText());
			int maxDic = Integer.parseInt(maxDice.getText());
			int minTim = Integer.parseInt(minTime.getText());
			int maxTim = Integer.parseInt(maxTime.getText());
			
			//TODO Appel de la méthode avec les paramètre
			BoundParameters created = new BoundParameters(minDic, maxDic, minCap, maxCap, minTim, maxTim, rdm);
			Client.getInstance().sendNewParameter(created);
			
			//Fermeture
			random.getScene().getWindow().hide();
		}
	}
	
	public boolean checkForm() {
		
		String minCap = minCapital.getText();
		String maxCap = maxCapital.getText();
		String minDic = minDice.getText();
		String maxDic = maxDice.getText();
		String minTim = minTime.getText();
		String maxTim = maxTime.getText();
		
		boolean check = true;
		
		if (minCap.isEmpty() || !isNumber(minCap)) {
			minCapital.setStyle("-jfx-unfocus-color: red;-fx-text-fill: red;");
			check = false;
		}
		
		if (maxCap.isEmpty() || !isNumber(maxCap)) {
			maxCapital.setStyle("-jfx-unfocus-color: red;-fx-text-fill: red;");
			check = false;
		}
		
		if (minDic.isEmpty() || !isNumber(minDic)) {
			minDice.setStyle("-jfx-unfocus-color: red;-fx-text-fill: red;");
			check = false;
		}
		
		if (maxDic.isEmpty() || !isNumber(maxDic)) {
			maxDice.setStyle("-jfx-unfocus-color: red;-fx-text-fill: red;");
			check = false;
		}
		
		if (minTim.isEmpty() || !isNumber(minTim)) {
			minTime.setStyle("-jfx-unfocus-color: red;-fx-text-fill: red;");
			check = false;
		}
		
		if (maxTim.isEmpty() || !isNumber(maxTim)) {
			maxTime.setStyle("-jfx-unfocus-color: red;-fx-text-fill: red;");
			check = false;
		}
		
		return check;
	}
	
	private boolean isNumber(String str) {
		
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override public void initialize(URL location, ResourceBundle resources) {
		
		BoundParameters b = Player.getBounds();
		
		
		validate.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent event) {
				
				bound();
			}
		});
		
		minDice.setText(Integer.toString(b.getMinDice()));
		minDice.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override public void handle(MouseEvent event) {
				
				minDice.setStyle("-jfx-unfocus-color: black;");
			}
		});
		
		maxDice.setText(Integer.toString(b.getMaxDice()));
		maxDice.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override public void handle(MouseEvent event) {
				
				maxDice.setStyle("-jfx-unfocus-color: black;");
			}
		});
		
		minCapital.setText(Integer.toString(b.getMinMoneyAtTheStart()));
		minCapital.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override public void handle(MouseEvent event) {
				
				minCapital.setStyle("-jfx-unfocus-color: black;");
			}
		});
		
		maxCapital.setText(Integer.toString(b.getMaxMoneyAtTheStart()));
		maxCapital.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override public void handle(MouseEvent event) {
				
				maxCapital.setStyle("-jfx-unfocus-color: black;");
			}
		});
		
		minTime.setText(Integer.toString(b.getMinTime()));
		minTime.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override public void handle(MouseEvent event) {
				
				minTime.setStyle("-jfx-unfocus-color: black;");
			}
		});
		
		maxTime.setText(Integer.toString(b.getMaxTime()));
		maxTime.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override public void handle(MouseEvent event) {
				
				maxTime.setStyle("-jfx-unfocus-color: black;");
			}
		});
		
		random.setSelected(b.isRandomGameGeneration());
	}
}
