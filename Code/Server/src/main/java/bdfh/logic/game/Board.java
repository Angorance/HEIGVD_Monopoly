package bdfh.logic.game;

import bdfh.database.DatabaseConnect;
import bdfh.database.SquareDB;
import bdfh.net.server.ClientHandler;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class Board {
	
	private static final int NB_SQUARE = 40;
	private Map<Integer, Integer> playerPosition;
	private Square[] board = new Square[NB_SQUARE];
	
	/**
	 * Prepare the board and init the player's positions
	 * @param players list of player participating to the game
	 */
	public Board(ArrayDeque<ClientHandler> players){
		playerPosition = new HashMap<>();
		
		for(ClientHandler c : players){
			playerPosition.put(c.getClientID(), 0);
		}
		
		// init the game board
		for(Square s : DatabaseConnect.getInstance().getSquareDB().getSquares()){
			board[s.getPosition()] = s;
		}
		
	}
	
	/**
	 * move the player in the board of a certain number of square.
	 * @param clientID  player to move
	 * @param movement how many case to move
	 * @return the case where the player stops
	 */
	public Square movePlayer(int clientID, int movement){
		int newPos = (playerPosition.get(clientID) + movement) % NB_SQUARE;
		playerPosition.put(clientID, newPos);
		
		return board[newPos];
	}
	
}
