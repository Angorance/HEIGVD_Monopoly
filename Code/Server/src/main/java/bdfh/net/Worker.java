package bdfh.net;

import bdfh.net.notification.NotificationHandler;
import bdfh.net.server.ClientHandler;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.*;

/**
 * Manage the connection with a client
 *
 * @author Bryan Curchod
 * @author Daniel Gonzalez Lopez
 * @version 1.1
 */
public class Worker implements Runnable {
	
	public static ArrayList<Worker> workers = new ArrayList<>();
	
	private InputStream in;
	private OutputStream out;
	private Socket client;
	private Handler handler;
	
	private final static Logger LOG = Logger.getLogger("Worker");
	
	/**
	 * Construct a client manager with a socket
	 *
	 * @param client
	 */
	public Worker(Socket client, Handler handler) {
		
		LOG.log(Level.INFO, "New client connected");
		
		try {
			this.client = client;
			this.handler = handler;
			in = client.getInputStream();
			out = client.getOutputStream();
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Worker not created: " + e);
			e.printStackTrace();
		}
		
		workers.add(this);
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
		
		workers.remove(this);
		
		try {
			in.close();
			out.close();
			client.close();
			
			LOG.log(Level.INFO, "Client disconnected\n\t"
					+ Thread.currentThread().getName() + " stopped.");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void bindNotif(NotificationHandler not, String username) {
		
		for (Worker w : workers) {
			
			if (((ClientHandler) w.handler).getClientUsername().equals(username)) {
				((ClientHandler) w.handler).addNot(not);
			}
		}
	}
}