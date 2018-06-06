package bdfh.logic.game;

import bdfh.database.DatabaseConnect;
import bdfh.net.server.ClientHandler;
import bdfh.protocol.GameProtocol;
import bdfh.serializable.GsonSerializer;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Daniel Gonzalez Lopez, Bryan Curchod
 * @version 1.6
 */
public class Board {
	
	private final static Logger LOG = Logger.getLogger("Board");
	
	public static final int NB_SQUARE = 40;
	private Map<String, ArrayList<Integer>> listPossession;
	private Map<Integer, Integer> playerPosition;
	private Square[] board = new Square[NB_SQUARE];
	
	/**
	 * Prepare the board and init the player's positions
	 *
	 * @param players list of player participating to the game
	 */
	public Board(ArrayDeque<ClientHandler> players) {
		
		playerPosition = new HashMap<>();
		listPossession = new TreeMap<>();
		
		for (ClientHandler c : players) {
			playerPosition.put(c.getClientID(), 0);
		}
		
		// init the game board
		for (Square s : DatabaseConnect.getInstance().getSquareDB().getSquares()) {
			board[s.getPosition()] = s;
			
			if (s.isBuyable() && !listPossession.containsKey(s.getFamily())) {
				
				switch (s.getFamily()) {
					case GameProtocol.SQUA_INSTITUTE:
						listPossession.put(s.getFamily(), new ArrayList<>(Arrays.asList(-1, -1, -1, -1)));
					case GameProtocol.SQUA_COMPANY:
						listPossession.put(s.getFamily(), new ArrayList<>(Arrays.asList(-1, -1)));
					default:
						listPossession.put(s.getFamily(), new ArrayList<>(Arrays.asList(-1, -1, -1)));
					
				}
			}
		}
		
	}
	
	/**
	 * move the player in the board of a certain number of square.
	 *
	 * @param clientID player to move
	 * @param movement how many case to move
	 *
	 * @return true if the player passed the start case
	 */
	public boolean movePlayer(int clientID, int movement) {
		
		int oldPos =  playerPosition.get(clientID);
		int newPos;
		
		if(movement > 0) {
			newPos = (oldPos + movement) % NB_SQUARE;
		} else {
			newPos = (oldPos + movement) + NB_SQUARE;
		}
		
		playerPosition.put(clientID, newPos);
		
		// Notify
		LOG.log(Level.INFO, clientID + " old pos: " + oldPos + " | new pos : " + newPos );
		
		boolean passedGo = newPos < oldPos && movement > 0;
		return passedGo;
	}
	
	/**
	 * Set the new position of the player in the board.
	 *
	 * @param clientID player to move
	 * @param position new position of the player
	 *
	 * @return true if the player passed the start case
	 */
	public void setPlayerPosition(int clientID, int position) {
		
		int oldPos =  playerPosition.get(clientID);
		playerPosition.put(clientID, position);
		
		// Notify
		LOG.log(Level.INFO, clientID + " old pos: " + oldPos + " | new pos : " + position);
	}
	
	/**
	 * retrieve the square on which a player is.
	 *
	 * @param playerId player to retrieve the square he is on. (i understand myself...)
	 *
	 * @return Square on which the player is.
	 */
	public Square getCurrentSquare(int playerId) {
		
		return board[playerPosition.get(playerId)];
	}
	
	/**
	 * @param game
	 * @param s
	 */
	public void manageEffect(GameLogic game, Square s) {
		
		switch (s.getFamily()) {
			
			case GameProtocol.SQUA_TAX:
				game.manageMoney(game.getCurrentPlayer(), s.getPrices().getRent() * -1);
				LOG.log(Level.INFO,
						game.getCurrentPlayer().getClientUsername() + " paid " + s.getPrices().getRent() + " (got a Tax)");
				break;
			
			case GameProtocol.SQUA_INSTITUTE:
				if (s.getOwner() != null && s.getOwner().getClientID() != game.getCurrentPlayerID() && !s.isMortgaged()) {
					int baseRent = s.getPrices().getRent();
					int factor = howManyPossession(s.getOwner().getClientID(), s.getFamily());
					int totalToPay = baseRent * (int) Math.pow(2, factor - 1);
					game.manageMoney(game.getCurrentPlayer(), totalToPay * -1);
					game.manageMoney(s.getOwner(), totalToPay);
					LOG.log(Level.INFO,
							game.getCurrentPlayer().getClientUsername() + " paid " + totalToPay + " to " + s.getOwner()
									.getClientUsername() + " (" + factor + " Institute possessed)");
				}
				break;
			
			case GameProtocol.SQUA_COMPANY: // IL Y EN A DEUX
				if (s.getOwner() != null && s.getOwner().getClientID() != game.getCurrentPlayerID() && !s.isMortgaged()) {
					// TODO we have to check how many company the owner possess
					int factor = s.getPrices().getRent();
					int howManyPossession = howManyPossession(s.getOwner().getClientID(), s.getFamily());
					if ( howManyPossession == 2) {
						factor *= 2.5;
					}
					
					int roll = game.getTotalLastRoll();
					int totalToPay = roll * factor;
					game.manageMoney(game.getCurrentPlayer(), totalToPay * -1);
					game.manageMoney(s.getOwner(), totalToPay);
					LOG.log(Level.INFO,
							game.getCurrentPlayer().getClientUsername() + " paid " + totalToPay + " to " + s.getOwner()
									.getClientUsername() + " (rolled a " + roll + ", "+ howManyPossession +" Company possessed)");
				}
				break;
			
			case GameProtocol.SQUA_CARD:
				game.drawCard();
				break;
				
			case GameProtocol.SQUA_GO_EXAM:
				game.sendToExam();
				break;
			
			case GameProtocol.SQUA_RED:
			case GameProtocol.SQUA_BLUE:
			case GameProtocol.SQUA_CYAN:
			case GameProtocol.SQUA_PINK:
			case GameProtocol.SQUA_GREEN:
			case GameProtocol.SQUA_ORANGE:
			case GameProtocol.SQUA_BROWN:
			case GameProtocol.SQUA_YELLOW:
				handleProperty(game, s);
				
				break;
		}
	}
	
	private int howManyPossession(int clientID, String family) {
		
		int count = 0;
		for (Integer i : listPossession.get(family)) {
			if (i == clientID) {
				count++;
			}
		}
		
		return count;
	}
	
	/**
	 * Tell if a player possesses all squares of a given family.
	 *
	 * @param clientID Player id.
	 * @param family Family of the square.
	 *
	 * @return True if the player possesses all family, false otherwise.
	 */
	public boolean hasFullFamily(int clientID, String family) {
		
		switch (family) {
			
			case GameProtocol.SQUA_RED:
			case GameProtocol.SQUA_CYAN:
			case GameProtocol.SQUA_PINK:
			case GameProtocol.SQUA_GREEN:
			case GameProtocol.SQUA_ORANGE:
			case GameProtocol.SQUA_YELLOW:
				if (howManyPossession(clientID, family) == 3) {
					return true;
				}
				
				break;
				
			case GameProtocol.SQUA_BROWN:
			case GameProtocol.SQUA_BLUE:
				if (howManyPossession(clientID, family) == 2) {
					return true;
				}
		}
		
		return false;
	}
	
	public void setOwner(ClientHandler buyer, int squarePos) {
		
		board[squarePos].setOwner(buyer);
		
		listPossession.get(board[squarePos].getFamily()).remove(0);
		listPossession.get(board[squarePos].getFamily()).add(buyer.getClientID());
	}
	
	public void removeOwner(ClientHandler oldOwner, int squarePos) {
		
		board[squarePos].setOwner(null);
		
		listPossession.get(board[squarePos].getFamily()).remove(Integer.valueOf(oldOwner.getClientID()));
		listPossession.get(board[squarePos].getFamily()).add(0, -1);
	}
	
	public Square getSquare(int posSquare) {
		
		Square returned = null;
		if (posSquare > 0 && posSquare < NB_SQUARE) {
			returned = board[posSquare];
		}
		
		return returned;
	}
	
	public String jsonify() {
		
		JsonArray jsonBoard = new JsonArray();
		
		for (Square s : board) {
			JsonObject jo = GsonSerializer.getInstance().fromJson(s.jsonify(), JsonObject.class);
			jsonBoard.add(jo);
		}
		
		return GsonSerializer.getInstance().toJson(jsonBoard);
	}
	
	public void setMortgaged(ClientHandler caller, int posProperty){
		Square requested = board[posProperty];
		
		if(requested.getOwner().getClientID() == caller.getClientID()){
			requested.setMortgaged(true);
		}
	}
	
	public void cancelMortgaged(ClientHandler caller, int posProperty){
		Square requested = board[posProperty];
		
		if(requested.getOwner().getClientID() == caller.getClientID()){
			requested.setMortgaged(false);
		}
	}
	
	/**
	 * Allows to handle the payment of a property tax.
	 * The amount depends on the number of Couches / HomeCinema or if it's empty.
	 *
	 * @param game
	 * @param s
	 */
	private void handleProperty(GameLogic game, Square s) {
		ClientHandler owner = s.getOwner();
		ClientHandler player = game.getCurrentPlayer();
		
		if(owner != null && owner.getClientID() != player.getClientID() && !s.isMortgaged()){
			// TODO payer le loyer selon canap et homeciné
		}
	}
	
	/**
	 * Allows to check if couches are well dispatched over the square's family.
	 *
	 * @param s
	 * @return
	 */
	public boolean checkCouchRepartition(Square s) {
		
		// TODO - Optimize
		for (Square tmp : board) {
			if (tmp.getFamily().equals(s.getFamily()) && !tmp.equals(s)) {
				if (tmp.getNbCouch() < s.getNbCouch()) {
					return false;
				}
			}
		}
		
		return true;
	}
}
