package bdfh.logic.game;

import bdfh.database.DatabaseConnect;
import bdfh.database.SquareDB;
import bdfh.net.server.ClientHandler;
import bdfh.protocol.GameProtocol;
import bdfh.serializable.GsonSerializer;
import com.google.gson.JsonArray;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class Board {
	
	public static final int NB_SQUARE = 40;
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
	 * @return true if the player passed the start case
	 */
	public boolean movePlayer(int clientID, int movement){
		boolean passedGo = (playerPosition.get(clientID) + movement) >= NB_SQUARE ;
		int newPos = (playerPosition.get(clientID) + movement) % NB_SQUARE;
		playerPosition.put(clientID, newPos);
		
		return passedGo;
		
	}
	
	public Square getCurrentSquare(int playerId){
		return board[playerPosition.get(playerId)];
	}
	
	public String jsonify() {
		
		JsonArray jsonBoard = new JsonArray();
		
		for (Square s : board) {
			jsonBoard.add(s.jsonify());
		}
		
		return GsonSerializer.getInstance().toJson(jsonBoard);
	}
	
	/**
	 *
	 * @param game
	 * @param s
	 */
	public void manageEffect(GameLogic game, Square s) {
		
		switch (s.getFamily()) {
			
			case GameProtocol.SQUA_TAX:
				
				break;
			
			case GameProtocol.SQUA_INSTITUTE:
				
				break;
			
			case GameProtocol.SQUA_COMPANY:
				if(s.getOwner() != null && s.getOwner().getClientID() != game.getCurrentPlayerID()){
					int amount = 0;
					// TODO we have to check how many company the owner possess
					game.manageMoney(game.getCurrentPlayer(), amount * -1);
					game.manageMoney(s.getOwner(), amount);
					
				} else if (s.getOwner() == null){
					// TODO offerToBuy this case
					
				}
				// we have to check how many company the player
				break;
			
			case GameProtocol.SQUA_CARD:
				game.drawCard();
				break;
			
			case GameProtocol.SQUA_GO_EXAM:
				
				break;
			
			case GameProtocol.SQUA_RED:
			case GameProtocol.SQUA_BLUE:
			case GameProtocol.SQUA_CYAN:
			case GameProtocol.SQUA_PINK:
			case GameProtocol.SQUA_GREEN:
			case GameProtocol.SQUA_ORANGE:
			case GameProtocol.SQUA_BROWN:
			case GameProtocol.SQUA_YELLOW:
				
				break;
		}
	}
}
