package bdfh.net;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manage the connection with a client
 *
 * @version 1.0
 * @authors Bryan Curchod
 */
public class ClientWorker implements Runnable {
	
	private InputStream in;
	private OutputStream out;
	private Socket client;
	private ClientHandler handler;
	
	private final static Logger LOG = Logger.getLogger("ClientWorker");
	
	/**
	 * Construct a client manager with a socket
	 *
	 * @param client
	 */
	ClientWorker(Socket client) {
		
		LOG.log(Level.INFO, "New client connected");
		
		try {
			this.client = client;
			in = client.getInputStream();
			out = client.getOutputStream();
			handler = new ClientHandler();
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "ClientWorker not created: " + e);
			e.printStackTrace();
		}
	}
	
	/**
	 * call the handler
	 */
	@Override
	public void run() {
		
		try {
			handler.handle(in, out);
			disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * close the client connection
	 */
	private void disconnect() {
		
		try {
			in.close();
			out.close();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}