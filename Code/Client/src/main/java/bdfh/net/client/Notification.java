package bdfh.net.client;

import bdfh.exceptions.ConnectionException;
import bdfh.net.protocol.Protocoly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
	private void connect() throws ConnectionException, IOException {
		
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
	
	public static void pause() {
		getInstance().interrupt();
	}
	
	public static void unpause() {
		getInstance().start();
	}
}
