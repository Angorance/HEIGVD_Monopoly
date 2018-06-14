package bdfh;

import bdfh.net.notification.NotificationServer;
import bdfh.net.server.ClientServer;
import bdfh.protocol.AppConfig;

/**
 * Hello world!
 */
public class App {
	
	public static void main(String[] args) {
		
		// Read and set the configurations of connection
		AppConfig config = new AppConfig();
		config.setDatabaseConfig();
		config.setServerConfig();
		
		// Start the server
		Thread main = new Thread(ClientServer.getInstance());
		Thread notification = new Thread(NotificationServer.getInstance());
		
		main.start();
		notification.start();
	}
}
