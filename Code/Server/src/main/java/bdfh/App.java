package bdfh;

import bdfh.net.notification.NotificationServer;
import bdfh.net.server.ClientServer;

/**
 * Hello world!
 */
public class App {
	
	public static void main(String[] args) {
		
		Thread main = new Thread(ClientServer.getInstance());
		Thread notification = new Thread(NotificationServer.getInstance());
		
		main.start();
		notification.start();
	}
}
