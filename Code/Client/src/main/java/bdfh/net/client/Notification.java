package bdfh.net.client;

import bdfh.exceptions.ConnectionException;
import bdfh.net.protocol.Protocoly;

import java.io.*;
import java.net.Socket;

import static bdfh.net.protocol.Protocoly.ANS_CONN;

/**
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class Notification extends Thread {
	
	private Socket notifSocket = null;
	private BufferedReader in = null;
	
	private String update;
	
	
	private Notification() {}
	
	private static class Instance {
		
		static final Notification instance = new Notification();
	}
	
	public static Notification getInstance() {
		
		return Instance.instance;
	}
	
	/**
	 * Connect to the server and check if it is successful.
	 *
	 * @throws ConnectionException if the server sends a wrong answer.
	 * @throws IOException
	 */
	public void connect() throws ConnectionException, IOException {
		
		try {
			notifSocket = new Socket(Protocoly.SERVER, Protocoly.NPORT);
			in = new BufferedReader(
					new InputStreamReader(notifSocket.getInputStream()));
			
			update = in.readLine();
			
			if (!update.equals(ANS_CONN)) {
				throw new ConnectionException(
						"A problem happened during Connection to Notification");
			}
			
		} catch (IOException e) {
			System.out.println("Notification::connect: " + e);
			throw e;
		}
	}
	
	/**
	 * Disconnect from the server and check if it is successful.
	 *
	 * @throws IOException
	 */
	public void disconnect() throws IOException {
		
		pause();
		
		if (in != null) {
			in.close();
		}
		
		if (notifSocket != null) {
			notifSocket.close();
		}
	}
	
	@Override
	public void run() {
		
		try {
			connect();
			
			while (true) {
				update = in.readLine();
				
				// TODO - créer classe lobby pour désérialisation
			}
		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void pause() {
		getInstance().interrupt();
	}
	
	public void unpause() {
		getInstance().start();
	}
}
