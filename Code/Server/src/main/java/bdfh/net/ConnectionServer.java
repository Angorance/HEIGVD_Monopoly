package bdfh.net;

import bdfh.protocol.Protocoly;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * ConnectionServer class.
 * Implements methods to wait for client connections and create workers to handle
 * them.
 *
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class ConnectionServer implements Runnable {
	
	ServerSocket srv;
	
	private final Logger LOG = Logger.getLogger("Server");
	
	private ConnectionServer() {
		
		try {
			srv = new ServerSocket(Protocoly.PORT);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private static class Instance {
		
		static final ConnectionServer instance = new ConnectionServer();
	}
	
	public static ConnectionServer getInstance() {
		
		return Instance.instance;
	}
	
	/**
	 * Wait for clients connection and create client workers to handle the clients.
	 * TODO - Need to keep a reference of the workers?
	 */
	@Override
	public void run() {
		
		while (true) {
			
			try {
				Socket newClient = srv.accept();
				
				ClientWorker cw = new ClientWorker(newClient);
				
				Thread worker = new Thread(cw);
				worker.start();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}
