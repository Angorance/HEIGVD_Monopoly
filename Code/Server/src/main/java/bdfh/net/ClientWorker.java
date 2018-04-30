package bdfh.net;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

/**
 * Manage the connection with a client
 * @version 1.0
 * @authors Bryan Curchod
 */
public class ClientWorker implements Runnable {
	
	InputStream in;
	OutputStream out;
	Socket client;
	ClientHandler handler;
	
	/**
	 * Construct a client manager with a socket
	 * @param client
	 */
	ClientWorker(Socket client) {
		
		try {
			this.client = client;
			in = client.getInputStream();
			out = client.getOutputStream();
			handler = new ClientHandler();
		} catch (IOException e) {
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