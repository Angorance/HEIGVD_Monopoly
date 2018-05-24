package bdfh.net.client;

import bdfh.exceptions.ConnectionException;
import bdfh.logic.usr.*;
import bdfh.net.protocol.Protocoly;
import bdfh.net.protocol.NotifProtocol;
import bdfh.serializable.GsonSerializer;
import bdfh.serializable.LightLobbies;
import bdfh.serializable.LightLobby;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Daniel Gonzalez Lopez
 * @author Bryan Curchod
 * @version 1.1
 */
public class Notification extends Thread {
	
	private Socket notifSocket = null;
	private BufferedReader in = null;
	
	private String line;
	private LightLobbies lobbies;
	
	public LightLobbies getLobbies() {
		
		return lobbies;
	}
	
	boolean isNotified = true;
	
	Logger LOG = Logger.getLogger("Notification");
	
	// TODO - Gérer list observer
	
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
		
		// TODO - Notify observer
		
		switch (s[0]) {
			case NotifProtocol.NOTIF_LIST:
				
				LightLobbies.getInstance().instancify(json);
				break;
				
			case NotifProtocol.NOTIF_NEW:
				/*newLobby(LightLobby)*/
			case NotifProtocol.NOTIF_UPDATE:
				/*updateLobby(LightLobby)*/
				LightLobbies.getInstance().addLobby(json);
				break;
				
			case NotifProtocol.NOTIF_DELETE:
				/*deleteLobby(LightLobby)*/
				LightLobbies.getInstance().removeLobby(json);
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
			
			while (isNotified) {
				line = in.readLine();

				handleNotification(line);
				
			}
		} catch (ConnectionException e) {
			e.printStackTrace();
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
}
