package bdfh;

import bdfh.exceptions.ConnectionException;
import bdfh.gui.model.Connection;
import bdfh.net.client.Client;
import bdfh.net.protocol.AppConfig;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;

import javax.swing.*;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
	
	    // Read and set the configurations of connection
	    AppConfig config = new AppConfig();
	    config.setClientConfig();
    	
	    try {
		    Client.getInstance().connect();
		    Connection.launcher();
		    
	    } catch (ConnectionException e) {
		    JOptionPane.showMessageDialog(null, "A problem came up while the connection.\n"
				    + "Please retry.", "ERROR", JOptionPane.ERROR_MESSAGE);
		    e.printStackTrace();
		    
	    } catch (IOException e) {
		    JOptionPane.showMessageDialog(null, "The client can't connect to the server.\n"
				    + "Please verify your configuration and retry.", "ERROR", JOptionPane.ERROR_MESSAGE);
		    e.printStackTrace();
	    }
    }
}