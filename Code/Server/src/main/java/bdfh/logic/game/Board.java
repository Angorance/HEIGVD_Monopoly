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
	private Map<String, LinkedList<Square>> SquareByFamily;
	private Map<Integer, Integer> playerPosition;
	private Square[] board = new Square[NB_SQUARE];
	
	/**
	 * Prepare the board and init the player's positions
	 *
	 * @param players list of player participating to the game
	 */
	public Board(ArrayDeque<ClientHandler> players) {
		
		playerPosition = new HashMap<>();
		SquareByFamily = new TreeMap<>();
		
		for (ClientHandler c : players) {
			playerPosition.put(c.getClientID(), 0);
		}
		
		// init the game board
		for (Square s : DatabaseConnect.getInstance().getSquareDB().getSquares()) {
			board[s.getPosition()] = s;
			SquareByFamily.merge(s.getFamily(), new LinkedList<>(), (old, newV) -> old);
			SquareByFamily.get(s.getFamily()).add(s);
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
	public void movePlayer(int clientID, int movement, GameLogic game) {
		
		int oldPos =  playerPosition.get(clientID);
		int newPos = ((oldPos + movement) + NB_SQUARE) % NB_SQUARE;
		
		// Move the player
		playerPosition.put(clientID, newPos);
		LOG.log(Level.INFO, clientID + " old pos: " + oldPos + " | new pos : " + newPos );
		
		// Handle the case if the player passed the start square
		if(newPos < oldPos && movement > 0) {
			game.handleStartPassed();
		}
		
		// Handle the effect of the square
		game.manageSquareEffect();
	}
	
	/**
	 * Set the new position of the player in the board.
	 *
	 * @param clientID player to move
	 * @param position new position of the player
	 *
	 * @return true if the player passed the start case
	 */
	public void setPlayerPosition(int clientID, int position, GameLogic game) {
		
		int oldPos =  playerPosition.get(clientID);
		
		// Move the player
		playerPosition.put(clientID, position);
		LOG.log(Level.INFO, clientID + " old pos: " + oldPos + " | new pos : " + position);
		
		// Handle the effect of the square
		game.manageSquareEffect();
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
	 * retrieve the exam square.
	 *
	 * @return Square of the exam.
	 */
	public Square getExamSquare() {
		
		Square exam = null;
		
		for(int s = 0; s < NB_SQUARE; s++) {
			
			exam = board[s];
			
			if(exam.getFamily().equals(GameProtocol.SQUA_EXAM)) {
				break;
			}
		}
		
		return exam;
	}
	
	/**
	 * @param game
	 * @param s
	 */
	public void manageEffect(GameLogic game, Square s) {
		
		switch (s.getFamily()) {
			
			case GameProtocol.SQUA_TAX:
				game.manageMoney(game.getCurrentPlayer(), s.getPrices().getRent() * -1);
				
				game.notifyPlayers(GameProtocol.GAM_PAY, Integer.toString(s.getPrices().getRent()));
				LOG.log(Level.INFO,
						game.getCurrentPlayer().getClientUsername() + " paid " + s.getPrices().getRent() + " (got a Tax)");
				break;
			
			case GameProtocol.SQUA_INSTITUTE:
				if (s.getOwner() != null && s.getOwner().getClientID() != game.getCurrentPlayerID() && !s.isMortgaged()) {
					int baseRent = s.getPrices().getRent();
					int factor = howManyPossession(s.getOwner().getClientID(), s.getFamily());
					int totalToPay = baseRent * (int) Math.pow(2, factor - 1);
					game.manageMoney(game.getCurrentPlayer(), totalToPay * -1);
					game.notifyPlayers(GameProtocol.GAM_PAY, Integer.toString(totalToPay));
					game.manageMoney(s.getOwner(), totalToPay);
					game.notifyPlayers(s.getOwner(), GameProtocol.GAM_PAY, Integer.toString(totalToPay));
					LOG.log(Level.INFO,
							game.getCurrentPlayer().getClientUsername() + " paid " + totalToPay + " to " + s.getOwner()
									.getClientUsername() + " (" + factor + " Institute possessed)");
				}
				break;
			
			case GameProtocol.SQUA_COMPANY: // IL Y EN A DEUX
				if (s.getOwner() != null && s.getOwner().getClientID() != game.getCurrentPlayerID() && !s.isMortgaged()) {
					int factor = s.getPrices().getRent();
					int howManyPossession = howManyPossession(s.getOwner().getClientID(), s.getFamily());
					if ( howManyPossession == 2) {
						factor *= 2.5;
					}
					
					int roll = game.getTotalLastRoll();
					int totalToPay = roll * factor;
					game.manageMoney(game.getCurrentPlayer(), totalToPay * -1);
					game.notifyPlayers(GameProtocol.GAM_PAY, Integer.toString(totalToPay));
					game.manageMoney(s.getOwner(), totalToPay);
					game.notifyPlayers(s.getOwner(), GameProtocol.GAM_PAY, Integer.toString(totalToPay));
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
		for (Square i : SquareByFamily.get(family)) {
			if (i.getOwner() != null && i.getOwner().getClientID() == clientID) {
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
	
	public void setOwner(ClientHandler possessor, int squarePos) {
		
		board[squarePos].setOwner(possessor);
	}
	
	public void removeOwner(ClientHandler oldOwner, int squarePos) {
		
		board[squarePos].setOwner(null);
	}
	
	public Square getSquare(int posSquare) {
		
		Square returned = null;
		if (posSquare >= 0 && posSquare < NB_SQUARE) {
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
			
			// Get the rent to pay
			int rent = s.getPrices().getRents()[s.getLevelRent()];
			
			// The current player pay the rent
			game.manageMoney(player, rent * -1);
			
			game.notifyPlayers(GameProtocol.GAM_PAY, String.valueOf(rent));
			LOG.log(Level.INFO, player.getClientUsername() + " a payé " + rent + ".- de loyer à " + owner.getClientUsername() + ".");
			
			// The owner of the square receives the rent
			game.manageMoney(owner, rent);
			
			game.notifyPlayers(owner, GameProtocol.GAM_GAIN, String.valueOf(rent));
			LOG.log(Level.INFO, owner.getClientUsername() + " a reçu " + rent + ".- de loyer de " + player.getClientUsername() + ".");
		}
	}
	
	/**
	 * Allows to check if couches are well dispatched over the square's family.
	 *
	 * @param s
	 * @return
	 */
	public boolean checkCouchRepartition(Square s) {
		for (Square tmp : SquareByFamily.get(s.getFamily())) {
			if (!tmp.equals(s) && tmp.getNbCouch() < s.getNbCouch()) {
				return false;
			}
		}
		
		return true;
	}
}
