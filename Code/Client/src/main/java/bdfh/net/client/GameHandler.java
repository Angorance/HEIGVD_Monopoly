package bdfh.net.client;

import bdfh.gui.controller.Controller_board;
import bdfh.gui.controller.Controller_lobbyList;
import bdfh.logic.usr.Player;
import bdfh.net.protocol.GameProtocol;
import bdfh.serializable.GsonSerializer;
import bdfh.serializable.LightBoard;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Héléna Line Reymond
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class GameHandler extends Thread {
	
	private static Logger LOG = Logger.getLogger("GameHandler");
	
	HashMap<Integer, Pair<String, Integer>> players = new HashMap<>();
	LightBoard board = null;
	
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
			case GameProtocol.GAM_BOARD:
				manageBoard(split[1]);
				break;
				
			case GameProtocol.GAM_PLYR:
				managePlayers(split[1]);
				break;
				
			case GameProtocol.GAM_PLAY:
				manageCurrentPlayer(split[1]);
				break;
				
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
	
	public void endTurn() {
		
		Player.getInstance().setMyTurn(false);
		sendData(GameProtocol.GAM_ENDT);
	}
	
	public HashMap<Integer, Pair<String,Integer>> getPlayers() {
		
		return players;
	}
	
	public LightBoard getBoard() {
		
		return board;
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
		
		ArrayList tmp = new ArrayList();
		
		for (int i = 2; i < str.length; ++i) {
			tmp.add(Integer.parseInt(str[i]));
		}
		
		Controller_board.movePawn(Integer.parseInt(str[1]), tmp);
	}
	
	private void managePlayers(String json) {
		
		JsonArray jsonPlayers = GsonSerializer.getInstance().fromJson(json, JsonArray.class);
		
		for (JsonElement je : jsonPlayers) {
			JsonObject jo = je.getAsJsonObject();
			
			int id = jo.get("id").getAsInt();
			String username = jo.get("username").getAsString();
			int capital = jo.get("capital").getAsInt();
			
			players.put(id, new Pair<> (username, capital));
		}
	}
	
	private void manageBoard(String json) {
	
		board = LightBoard.instancify(json);
	}
	
	private void manageCurrentPlayer(String playerID) {
	
		if (Player.getInstance().getUsername().equals(players.get(playerID).getKey())) {
			Player.getInstance().setMyTurn(true);
		}
	}
}