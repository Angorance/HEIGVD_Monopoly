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
import org.apache.commons.lang3.tuple.MutablePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Héléna Line Reymond
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class GameHandler extends Thread {
	
	private static Logger LOG = Logger.getLogger("GameHandler");
	
	HashMap<Integer, MutablePair<String, Integer>> players = new HashMap<>();
	LightBoard board = null;
	
	// Map a player ID to his exam state.
	private Map<Integer, Boolean> examState;
	
	private BufferedReader in = null;
	private PrintWriter out = null;
	private String response;
	
	private Controller_board sub;
	
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
	
	public void setSub(Controller_board board) {
		
		sub = board;
	}
	
	/**
	 * Handle the command received in the game.
	 *
	 * @param line Command received.
	 */
	private void handleGame(String line) {
		
		String[] split = line.split(" ", 2);
		
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
				manageRoll(split[1].split(" "));
				break;
			
			case GameProtocol.GAM_GAIN:
				manageGain(split[1].split(" "));
				break;
				
			case GameProtocol.GAM_PAY:
				managePay(split[1].split(" "));
				break;
				
			case GameProtocol.GAM_MOV:
				manageMove(split[1].split(" "));
				break;
				
			case GameProtocol.GAM_EXAM:
				examState.put(Integer.parseInt(split[1]), true);
				break;
			
			case GameProtocol.GAM_FRDM:
				examState.put(Integer.parseInt(split[1]), false);
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
		sub.notifyTurn();
		sendData(GameProtocol.GAM_ENDT);
	}
	
	public HashMap<Integer, MutablePair<String, Integer>> getPlayers() {
		
		return players;
	}
	
	/**
	 * Check if the player is stuck in the exam or not.
	 *
	 * @param playerID  Player to check.
	 *
	 * @return  true if he's in exam, false otherwise.
	 */
	public boolean getExamState(int playerID) {
		return examState.get(playerID);
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
		
		if(!getExamState(Player.getInstance().getID())) {
			sub.movePawn(Integer.parseInt(str[0]), tmp);
		}
	}
	
	private void managePlayers(String json) {
		
		JsonArray jsonPlayers = GsonSerializer.getInstance()
				.fromJson(json, JsonArray.class);
		
		for (JsonElement je : jsonPlayers) {
			JsonObject jo = je.getAsJsonObject();
			
			int id = jo.get("id").getAsInt();
			String username = jo.get("username").getAsString();
			int capital = jo.get("capital").getAsInt();
			
			players.put(id, new MutablePair<>(username, capital));
			examState.put(id, false);
			
			if (Player.getInstance().getUsername().equals(username)) {
				Player.getInstance().setID(id);
			}
		}
	}
	
	private void manageBoard(String json) {
		
		board = LightBoard.instancify(json);
		
		synchronized (this) {
			this.notify();
		}
	}
	
	private void manageCurrentPlayer(String playerID) {
		
		String username = Player.getInstance().getUsername();
		String usernameTurn = players.get(Integer.parseInt(playerID)).getKey();
		
		if (username.equals(usernameTurn)) {
			Player.getInstance().setMyTurn(true);
			
			synchronized (this) {
				if (sub == null) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				sub.notifyTurn();
			}
		}
		
		synchronized (this) {
			this.notify();
		}
	}
	
	
	private void manageGain(String[] split) {
		
		updateCapital(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
	}
	
	private void managePay(String[] split) {
		
		updateCapital(Integer.parseInt(split[0]), -1 * Integer.parseInt(split[1]));
	}
	
	private void updateCapital(int id, int value) {
		
		int newCap = players.get(id).getRight() + value;
		
		players.get(id).setRight(newCap);
		
		sub.updateBoard();
	}
	
	private void manageMove(String[] split) {
	
		sub.move(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
	}
}