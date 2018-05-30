package bdfh.net.client;

import bdfh.gui.controller.Controller_lobbyList;

import java.io.*;
import java.util.logging.*;

/**
 * @author Héléna Line Reymond
 * @version 1.0
 */
public class GameHandler extends Thread {
	
	private static Logger LOG = Logger.getLogger("GameHandler");
	
	private BufferedReader in = null;
	private PrintWriter out = null;
	private String response;
	
	private Controller_lobbyList lobbyList;
	
	private GameHandler() {}
	
	private static class Instance {
		
		static final GameHandler instance = new GameHandler();
	}
	
	public static GameHandler getInstance() {
		
		return Instance.instance;
	}
	
	/**
	 * Initialise the game handler with the streams of the client.
	 *
	 * @param in    Reader stream.
	 * @param out   Writer stream.
	 */
	public void initialise(BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;
	}
	
	public void setLobbyList(Controller_lobbyList lobbyList) {
		
		this.lobbyList = lobbyList;
	}
	
	/**
	 * Handle the command received in the game.
	 *
	 * @param line  Command received.
	 */
	private void handleGame(String line) {
		
		String[] s = line.split(" ");
		
		// TODO - update with information received for the game
		/*String json = s[1];
		
		switch (s[0]) {
		
		}*/
	}
	
	@Override
	public void run() {
		
		try {
			
			// Start the game (interface)
			lobbyList.startGame();
			
			while (true) {
				response = in.readLine();
				LOG.log(Level.INFO, "RECEIVED: " + response);
				
				handleGame(response);
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "GameHandler::run: " + e);
		}
	}
}