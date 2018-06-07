package bdfh.net.client;

import bdfh.gui.controller.Controller_board;
import bdfh.gui.controller.Controller_lobbyList;
import bdfh.logic.usr.Player;
import bdfh.net.protocol.GameProtocol;
import bdfh.serializable.GsonSerializer;
import bdfh.serializable.LightBoard;
import bdfh.serializable.LightPlayer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Héléna Line Reymond
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class GameHandler extends Thread {
	
	private static Logger LOG = Logger.getLogger("GameHandler");
	
	private Map<Integer, LightPlayer> players = new HashMap<>();
	
	private LightBoard board = null;
	
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
				String[] infos = split[1].split(" ");
				int id = Integer.parseInt(infos[0]);
				
				players.get(id).setInPrison(true);
				manageMove(infos);
				
				// Refresh
				sub.updateBoard();
				
				break;
			
			case GameProtocol.GAM_FRDM:
				id = Integer.parseInt(split[1]);
				
				players.get(id).setInPrison(false);
				
				// Refresh
				sub.updateBoard();
				
				break;

			case GameProtocol.GAM_FRDM_C:

				// The player received a freedom card
				
				id = Integer.parseInt(split[1]);
				
				players.get(id).setFreeCards(1);
				
				// Update the player
				Player.getInstance().setHasFreedomCard(true);
				
				// Refresh
				sub.updateBoard();
				
				break;

			case GameProtocol.GAM_FRDM_U:

				// The player used a freedom card
				id = Integer.parseInt(split[1]);
				
				players.get(id).setFreeCards(-1);
				players.get(id).setInPrison(false);
				
				// Update the player
				if(players.get(id).getFreeCards() == 0) {
					
					Player.getInstance().setHasFreedomCard(false);
				}
				
				// Refresh
				sub.updateBoard();
				
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
	
	public Map<Integer, LightPlayer> getPlayers() {
		
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
		
		// Don't move the player if he's in exam and load the popup choice
		if(!players.get(Player.getInstance().getID()).isInExam()) {
			
			sub.movePawn(Integer.parseInt(str[0]), tmp);
		} else {
			sub.loadPopup();
		}
	}
	
	private void managePlayers(String json) {
		
		JsonArray jsonPlayers = GsonSerializer.getInstance()
				.fromJson(json, JsonArray.class);
		
		for (JsonElement je : jsonPlayers) {
			JsonObject jo = je.getAsJsonObject();
			
			LightPlayer tmp = LightPlayer.instancify(jo);
			
			players.put(tmp.getId(), tmp);
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
		String usernameTurn = players.get(Integer.parseInt(playerID)).getUsername();
		
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
		
		players.get(id).addCapital(value);
		
		sub.updateBoard();
	}
	
	private void manageMove(String[] split) {
	
		sub.move(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
	}
	
	/**
	 * Use a card the leave the exam.
	 */
	public void useFreedomCard() {
		sendData(GameProtocol.GAM_FRDM_U);
	}
	
	/**
	 * Pay the tax to leave the exam.
	 */
	public void payExamTax() {
		
		sendData(GameProtocol.GAM_FRDM_T);
	}
}