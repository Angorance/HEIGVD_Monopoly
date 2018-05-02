package bdfh.gui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Paint;

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
			username.setStyle("-fx-text-inner-color: red;");
		} else if (code == -1) {
			password.setStyle("-fx-text-inner-color: red;");
		} else {
			login.setStyle("-fx-background-color: blue");
		}
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
				username.setStyle("-fx-text-inner-color: red;");
			} else {
				register.setStyle("-fx-background-color: blue");
			}
		}
	}
	
	@Override public void initialize(URL location, ResourceBundle resources) {
		
		username.setOnMouseClicked(e -> {
			username.setStyle("-fx-text-inner-color: black;");
		});
		
		password.setOnMouseClicked(e -> {
			password.setStyle("-fx-text-inner-color: black;");
		});
		
		confirmPassword.setOnMouseClicked(e -> {
			confirmPassword.setStyle("-fx-text-inner-color: black;");
		});
		
		
	}
}
