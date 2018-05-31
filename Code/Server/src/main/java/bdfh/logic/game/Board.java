package bdfh.logic.game;

import bdfh.database.DatabaseConnect;
import bdfh.database.SquareDB;
import bdfh.net.server.ClientHandler;
import bdfh.protocol.GameProtocol;
import bdfh.serializable.GsonSerializer;
import com.google.gson.JsonArray;
import com.mysql.fabric.xmlrpc.Client;

import java.util.*;

/**
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class Board {
	
	public static final int NB_SQUARE = 40;
	private Map<String, ArrayList<Integer>> listPossession;
	private Map<Integer, Integer> playerPosition;
	private Square[] board = new Square[NB_SQUARE];
	
	/**
	 * Prepare the board and init the player's positions
	 * @param players list of player participating to the game
	 */
	public Board(ArrayDeque<ClientHandler> players){
		playerPosition = new HashMap<>();
		listPossession = new TreeMap<>();
		
		for(ClientHandler c : players){
			playerPosition.put(c.getClientID(), 0);
		}
		
		// init the game board
		for(Square s : DatabaseConnect.getInstance().getSquareDB().getSquares()){
			board[s.getPosition()] = s;
			
			if(s.isBuyable() && !listPossession.containsKey(s.getFamily())){
				
				switch(s.getFamily()){
					case GameProtocol.SQUA_INSTITUTE :
						listPossession.put(s.getFamily(), new ArrayList<>(Arrays.asList(-1,-1,-1,-1)));
					case GameProtocol.SQUA_COMPANY :
						listPossession.put(s.getFamily(), new ArrayList<>(Arrays.asList(-1,-1)));
					default :
						listPossession.put(s.getFamily(), new ArrayList<>(Arrays.asList(-1,-1,-1)));
						
				}
			}
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
	
	/**
	 *
	 * @param game
	 * @param s
	 */
	public void manageEffect(GameLogic game, Square s) {
		
		switch (s.getFamily()) {
			
			case GameProtocol.SQUA_TAX:
				game.manageMoney(game.getCurrentPlayer(), s.getPrices().getRent() * -1);
				break;
			
			case GameProtocol.SQUA_INSTITUTE:
				if(s.getOwner() != null && s.getOwner().getClientID() != game.getCurrentPlayerID()){
					int baseRent = s.getPrices().getRent();
					int factor = howManyPossession(s.getOwner().getClientID(), s.getFamily());
					int totalToPay = baseRent * (int)Math.pow(2, factor-1);
					game.manageMoney(game.getCurrentPlayer(), totalToPay * -1);
					game.manageMoney(s.getOwner(), totalToPay);
				}
				break;
			
			case GameProtocol.SQUA_COMPANY: // IL Y EN A DEUX
				if(s.getOwner() != null && s.getOwner().getClientID() != game.getCurrentPlayerID()){
					// TODO we have to check how many company the owner possess
					int factor = s.getPrices().getRent();
					
					if(howManyPossession(s.getOwner().getClientID(),s.getFamily()) == 2){
						factor *= 2.5;
					}
					
					int roll = game.getTotalLastRoll();
					int totalToPay = roll * factor;
					game.manageMoney(game.getCurrentPlayer(), totalToPay * -1);
					game.manageMoney(s.getOwner(), totalToPay);
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
	
	private int howManyPossession(int clientID, String family) {
		int count = 0;
		for(Integer i : listPossession.get(family)){
			if(i == clientID) count ++;
		}
		
		return count;
	}
	
	public void setOwner(ClientHandler buyer, int squarePos){
		board[squarePos].setOwner(buyer);
		
		listPossession.get(board[squarePos].getFamily()).remove(0);
		listPossession.get(board[squarePos].getFamily()).add(buyer.getClientID());
	}
	
	public void removeOwner(ClientHandler oldOwner, int squarePos){
		board[squarePos].setOwner(null);
		
		listPossession.get(board[squarePos].getFamily()).remove(Integer.valueOf(oldOwner.getClientID()));
		listPossession.get(board[squarePos].getFamily()).add(0, -1);
	}
	
	public Square getSquare(int posSquare) {
		
		Square returned = null;
		if(posSquare > 0 && posSquare < NB_SQUARE){
			returned = board[posSquare];
		}
		
		return  returned;
	}
	
	public String jsonify() {
		
		JsonArray jsonBoard = new JsonArray();
		
		for (Square s : board) {
			jsonBoard.add(s.jsonify());
		}
		
		return GsonSerializer.getInstance().toJson(jsonBoard);
	}
}
