package bdfh.net.client;

import bdfh.gui.controller.Controller_board;
import bdfh.gui.controller.Controller_lobbyList;
import bdfh.net.protocol.GameProtocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Héléna Line Reymond
 * @version 1.0
 */
public class GameHandler extends Thread {
	
	private static Logger LOG = Logger.getLogger("GameHandler");
	
	private BufferedReader in = null;
	private PrintWriter out = null;
	private String response;
	
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
	 * @param in Reader stream.
	 * @param out Writer stream.
	 */
	public void initialise(BufferedReader in, PrintWriter out) {
		
		this.in = in;
		this.out = out;
	}
	
	/**
	 * Handle the command received in the game.
	 *
	 * @param line Command received.
	 */
	private void handleGame(String line) {
		
		String[] split = line.split(" ");
		
		switch (split[0]) {
			case GameProtocol.GAM_ROLL:
				manageRoll(split);
				break;
			
		}
	}
	
	@Override
	public void run() {
		
		try {
			
			// Start the game (interface)
			Controller_lobbyList.startGame();
			
			while (true) {
				response = in.readLine();
				LOG.log(Level.INFO, "RECEIVED: " + response);
				
				handleGame(response);
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "GameHandler::run: " + e);
		}
	}
	
	public void rollDice() {
		
		sendData(GameProtocol.GAM_ROLL);
	}
	
	/**
	 * Send data (commands) to the server.
	 *
	 * @param data Data to send.
	 */
	private void sendData(String data) {
		
		LOG.log(Level.INFO, "SENT: " + data);
		
		// Print the data and flush the stream.
		out.println(data);
		out.flush();
	}
	
	private void manageRoll(String[] str) {
		
		int[] tmp;
		
		tmp = new int[str.length - 2];
		
		for (int i = 2; i < str.length; ++i) {
			tmp[i - 2] = Integer.parseInt(str[i]);
		}
		
		Controller_board_movePawn(str[1], tmp);
	}
}