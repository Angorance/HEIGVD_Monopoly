package bdfh.net.server;

import bdfh.database.DatabaseConnect;
import bdfh.net.Worker;

import java.net.*;
import java.util.logging.*;

import static bdfh.protocol.Protocoly.CPORT;

/**
 * ClientServer class.
 * Implements methods to wait for client connections and create workers to
 * handle them.
 *
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class ClientServer implements Runnable {
	
	private ServerSocket srv;
	
	private final static Logger LOG = Logger.getLogger("Server");
	
	private ClientServer() {
		
		try {
			srv = new ServerSocket(CPORT);
			LOG.log(Level.INFO,
					"Server connected\nAddress::" + srv.getLocalSocketAddress()
							+ "\nPort::" + CPORT);
			
			// Update the limits of the logic
			DatabaseConnect.getInstance().getParameterDB().updateLimits();
			
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Exception creating server socket: " + e);
			e.printStackTrace();
		}
	}
	
	private static class Instance {
		
		static final ClientServer instance = new ClientServer();
	}
	
	public static ClientServer getInstance() {
		
		return Instance.instance;
	}
	
	/**
	 * Wait for clients connection and create client workers to handle the
	 * clients.
	 */
	@Override
	public void run() {
		
		int threadID = 0;
		
		while (true) {
			
			try {
				
				LOG.log(Level.INFO, "Waiting for new client to connect");
				
				Socket newClient = srv.accept();
				
				Worker cw = new Worker(newClient, new ClientHandler());
				
				Thread worker = new Thread(cw, "ClientThread" + threadID++);
				worker.start();
				
				LOG.log(Level.INFO,
						"Client accepted. " + worker.getName() + " created and started");
				
			} catch (Exception e) {
				LOG.log(Level.SEVERE,
						"Exception accepting client connection: " + e);
				e.printStackTrace();
			}
		}
	}
}
