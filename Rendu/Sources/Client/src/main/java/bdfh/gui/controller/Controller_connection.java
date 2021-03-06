package bdfh.gui.controller;

import bdfh.gui.model.frameLobby;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static bdfh.logic.conn.Authentication.*;

public class Controller_connection implements Initializable {
	
	@FXML private JFXTextField username;
	@FXML private JFXPasswordField password;
	@FXML private JFXPasswordField confirmPassword;
	@FXML private JFXCheckBox enregistrementCheckBox;
	@FXML private JFXButton login;
	@FXML private JFXButton register;
	@FXML private Label message;
	@FXML private Pane window;
	
	
	Konami konami = new Konami();
	/**
	 * Event check if checkBox is ticked
	 *
	 * @param event -
	 */
	@FXML void checkEnregistrement(ActionEvent event) {
		
		if (enregistrementCheckBox.isSelected()) {
			confirmPassword.setVisible(true);
			register.setVisible(true);
			login.setVisible(false);
		} else {
			confirmPassword.setVisible(false);
			login.setVisible(true);
			register.setVisible(false);
		}
	}
	
	/**
	 * Event click on login button
	 *
	 * @param event -
	 */
	@FXML void loginButton(ActionEvent event) {
		
		String usernameText = username.getText();
		String passwordText = password.getText();
		int code = login(usernameText, passwordText);
		if (code == 0) {
			setError("Username inconnue");
		} else if (code == -1) {
			setError("Mot de passe incorrect");
		} else{
			loadFrame();
		}
		
	}
	
	void setError(String errorMessage){
		message.setText(errorMessage);
		message.setStyle("-fx-text-fill: red;-fx-border-color: red;");
	}
	/**
	 * Event click on register button
	 *
	 * @param event TODO
	 */
	@FXML void registerButton(ActionEvent event) {
		
		String usernameText = username.getText();
		String passwordText = password.getText();
		String confPasswordText = confirmPassword.getText();
		boolean check = true;
		
		/*Check if passwords are equal*/
		if (checkPassword(passwordText, confPasswordText)) {
			/*Check if username is not already used*/
			if (!register(usernameText, passwordText)) {
				setError("Username déjà utilisé");
			}else{
				loadFrame();
			}
		} else {
			setError("Les mots de passes ne correspondent pas");
		}
	}
	
	/**
	 *
	 */
	private void loadFrame(){
		((Stage)username.getScene().getWindow()).close();
		new frameLobby();
	
	}
	
	@Override public void initialize(URL location, ResourceBundle resources) {
			password.setOnAction(event -> {
				if(!enregistrementCheckBox.isSelected()){
					loginButton(event);
				}
			});
			
			confirmPassword.setOnAction(event -> {
				if(enregistrementCheckBox.isSelected()){
					registerButton(event);
				}
			});
	}
}
