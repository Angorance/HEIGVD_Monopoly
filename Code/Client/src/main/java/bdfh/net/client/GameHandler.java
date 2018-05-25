package bdfh.net.client;

import java.io.*;
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
	 * Handle the command received in the game.
	 *
	 * @param line  Command received.
	 */
	private void handleGame(String line) {
		
		String[] s = line.split(" ");
		
		// TODO - update with information received for the game
		//String json = s[1];
		
		switch (s[0]) {
		
		}
	}
	
	@Override
	public void run() {
		
		try {
			
			// Start the game (interface)
			// TODO
			
			while (true) {
				response = in.readLine();
				handleGame(response);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initialise(BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;
	}
}