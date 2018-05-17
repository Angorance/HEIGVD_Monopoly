package bdfh.net.client;

import bdfh.exceptions.ConnectionException;
import bdfh.logic.usr.*;
import bdfh.net.protocol.Protocoly;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;

/**
 * @author Daniel Gonzalez Lopez
 * @author Bryan Curchod
 * @version 1.1
 */
public class Notification extends Thread {
	
	private Socket notifSocket = null;
	private BufferedReader in = null;
	
	private String line;
	private Lobbies lobbies = Lobbies.getInstance();
	
	boolean isNotified = true;
	
	
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
			
			if (!line.equals(Protocoly.ANS_CONN)) {
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
			
			while (isNotified) {
				line = in.readLine();

				String[] command = line.split(" ",1);
				int idUpdated = 0;
				
				switch (command[0]) {
					case Protocoly.NOT_LIST : // lobbies have been sent
						//TODO - implement when sprint 3 is started
						break;
					case Protocoly.NOT_UPDATE : // a lobby has been updated
						Lobby l = new Gson().fromJson(command[1],Lobby.class);
						lobbies.updateLobby(l);
						idUpdated = l.getID();
						break;
					case Protocoly.NOT_DELETE : // a lobby has been deleted (game launched, nobody in lobby)
						idUpdated = Integer.parseInt(command[1]);
						lobbies.removeLobby(idUpdated);
						break;
				}

				// signal the update with the ID
				// TODO signal an observer/the GUI


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
