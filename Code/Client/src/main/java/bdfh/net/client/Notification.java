package bdfh.net.client;

import bdfh.exceptions.ConnectionException;
import bdfh.gui.controller.Controller_lobbyList;
import bdfh.net.protocol.Protocoly;
import bdfh.net.protocol.NotifProtocol;
import bdfh.serializable.LightLobbies;

import java.io.*;
import java.net.Socket;
import java.util.logging.*;

/**
 * @author Daniel Gonzalez Lopez
 * @author Bryan Curchod
 * @version 1.1
 */
public class Notification extends Thread {
	
	private static Logger LOG = Logger.getLogger("Notification");
	
	private Socket notifSocket = null;
	private BufferedReader in = null;
	private String line;
	
	private Controller_lobbyList sub;
	
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
	public void connect() throws IOException {
		
		try {
			notifSocket = new Socket(Protocoly.SERVER, Protocoly.NPORT);
			in = new BufferedReader(
					new InputStreamReader(notifSocket.getInputStream()));
			
			line = in.readLine();
			
			LOG.log(Level.INFO, line);
			
			handleNotification(line);
			
		} catch (IOException e) {
			System.out.println("Notification::connect: " + e);
			throw e;
		}
	}
	
	private void handleNotification(String line) {
		
		String[] s = line.split(" ");
		
		String json = s[1];
		
		// TODO - faire propre
		switch (s[0]) {
			case NotifProtocol.NOTIF_LIST:
				
				LightLobbies.getInstance().instancify(json);
				break;
				
			case NotifProtocol.NOTIF_NEW:
				
				LightLobbies.getInstance().createLobby(sub, json);
				break;
				
			case NotifProtocol.NOTIF_UPDATE:
				
				LightLobbies.getInstance().addLobby(sub, json);
				break;
				
			case NotifProtocol.NOTIF_DELETE:
				
				LightLobbies.getInstance().removeLobby(sub, json);
				break;
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
				line = in.readLine();

				handleNotification(line);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void pause() {
		
		try {
			disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		getInstance().interrupt();
	}
	
	public void unpause() {
		getInstance().start();
	}
	
	public void addSubscriber(Controller_lobbyList sub) {
		this.sub = sub;
	}
}
