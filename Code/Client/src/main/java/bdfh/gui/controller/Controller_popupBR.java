package bdfh.gui.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller_popupBR implements Initializable {
	
	@FXML JFXButton button_ok;
	
	private Controller_board cb;
	
	public Controller_popupBR(Controller_board cb) {
		
		this.cb = cb;
	}
	
	private void ok() {
		
		cb.unloadPopup();
	}
	
	@Override public void initialize(URL location, ResourceBundle resources) {
		
		button_ok.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent event) {
				
				ok();
			}
		});
	}
}
