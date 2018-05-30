package bdfh.net.client;

import bdfh.exceptions.ConnectionException;
import bdfh.gui.controller.Controller_lobbyList;
import bdfh.logic.usr.Player;
import bdfh.net.protocol.*;
import bdfh.serializable.LightLobbies;

import java.io.*;
import java.net.Socket;
import java.util.logging.*;

/**
 * @author Daniel Gonzalez Lopez
 * @author Bryan Curchod
 * @author Héléna Line Reymond
 *
 * @version 1.1
 */
public class Notification extends Thread {
	
	private static Logger LOG = Logger.getLogger("Notification");
	
	private Socket notifSocket = null;
	private BufferedReader in = null;
	private String response;
	
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
			in = new BufferedReader(new InputStreamReader(notifSocket.getInputStream()));
			
			response = in.readLine();
			LOG.log(Level.INFO, "RECEIVED: " + response);
			
			handleNotification(response);
			
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Notification::connect: " + e);
			throw e;
		}
	}
	
	@Override
	public void run() {
		
		try {
			connect();
			
			while (true) {
				response = in.readLine();
				LOG.log(Level.INFO, "RECEIVED: " + response);
				
				handleNotification(response);
				
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Notification::run: " + e);
		}
	}
	
	/**
	 * Handle the notifications of the channel.
	 *
	 * @param line  Notification received.
	 */
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
			
			case NotifProtocol.NOTIF_START:
				
				// Check if my lobby is started
				if(Player.getInstance().getLobbyID() == Integer.parseInt(json)) {
					
					// Quit the notification channel
					getInstance().pause();
					
					// Clean the received lobbies
					LightLobbies.getInstance().clearLobbies();
					
					// Start the game
					GameHandler.getInstance().setLobbyList(sub);
					GameHandler.getInstance().start();
				}
				break;
		}
	}
	
	/**
	 * Disconnect from the server and check if it is successful.
	 *
	 * @throws IOException
	 */
	private void disconnect() throws IOException {
		
		try{
			
			if (in != null) {
				in.close();
			}
			
			if (notifSocket != null) {
				notifSocket.close();
			}
			
		} catch(IOException e) {
			LOG.log(Level.SEVERE, "Notification::disconnect: " + e);
			throw e;
		}
	}
	
	/**
	 * Put the notification channel into pause mode.
	 */
	public void pause() {
		
		try {
			disconnect();
			
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Notification::pause: " + e);
		}
		
		getInstance().interrupt();
	}
	
	/**
	 * Put the notification channel into start mode.
	 */
	public void unpause() {
		getInstance().start();
	}
	
	/**
	 * Add a listener to the notification channel.
	 *
	 * @param sub   Subscriber to add.
	 */
	public void addSubscriber(Controller_lobbyList sub) {
		this.sub = sub;
	}
}
