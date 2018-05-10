package bdfh.net;

import bdfh.database.DatabaseConnect;
import bdfh.database.ParameterDB;
import bdfh.protocol.Protocoly;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ConnectionServer class.
 * Implements methods to wait for client connections and create workers to
 * handle
 * them.
 *
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class ConnectionServer implements Runnable {
	
	ServerSocket srv;
	
	private final static Logger LOG = Logger.getLogger("Server");
	
	private ConnectionServer() {
		
		try {
			srv = new ServerSocket(Protocoly.PORT);
			LOG.log(Level.INFO,
					"Server connected\nAddress::" + srv.getLocalSocketAddress()
							+ "\nPort::" + Protocoly.PORT);
			
			// Update the limits of the game
			ParameterDB.getInstance().updateLimits();
			
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Exception creating server socket: " + e);
			e.printStackTrace();
		}
	}
	
	private static class Instance {
		
		static final ConnectionServer instance = new ConnectionServer();
	}
	
	public static ConnectionServer getInstance() {
		
		return Instance.instance;
	}
	
	/**
	 * Wait for clients connection and create client workers to handle the
	 * clients.
	 */
	@Override
	public void run() {
		
		while (true) {
			
			try {
				
				LOG.log(Level.INFO, "Waiting for new client to connect");
				
				Socket newClient = srv.accept();
				
				ClientWorker cw = new ClientWorker(newClient);
				
				Thread worker = new Thread(cw);
				worker.start();
				
				LOG.log(Level.INFO, "Client accepted. Worker created and started");
				
			} catch (Exception e) {
				LOG.log(Level.SEVERE, "Exception accepting client connection: " + e);
				e.printStackTrace();
			}
		}
	}
}
