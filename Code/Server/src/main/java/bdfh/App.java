package bdfh;

import bdfh.net.ConnectionServer;

/**
 * Hello world!
 */
public class App {
	
	public static void main(String[] args) {
		
		Thread main = new Thread(ConnectionServer.getInstance());
		main.start();
		
	}
}
