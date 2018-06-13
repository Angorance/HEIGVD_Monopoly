package bdfh;

import bdfh.exceptions.ConnectionException;
import bdfh.gui.model.Connection;
import bdfh.net.client.Client;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
	
    	// Get the client's configuration
	    
    	
	    try {
		    Client.getInstance().connect();
	    } catch (ConnectionException e) {
		    e.printStackTrace();
	    } catch (IOException e) {
		    e.printStackTrace();
	    }
	    
	    Connection.launcher();
    }
}
