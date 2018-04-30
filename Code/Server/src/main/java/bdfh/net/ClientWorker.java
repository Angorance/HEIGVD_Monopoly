package bdfh.net;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

/**
 * S'occupe de gérer la connection avec un client // TODO - IN ENGLISH BRYAN D:
 *
 * @version 1.0
 * @authors Bryan Curchod
 */
public class ClientWorker implements Runnable {
	
	InputStream in;
	OutputStream out;
	Socket client;
	ClientHandler handler;
	
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
	
	@Override
	public void run() {
		
		try {
			handler.handle(in, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}