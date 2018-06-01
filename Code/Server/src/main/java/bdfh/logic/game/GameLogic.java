package bdfh.logic.game;

import bdfh.database.DatabaseConnect;
import bdfh.net.server.ClientHandler;
import bdfh.logic.saloon.Lobby;
import bdfh.protocol.GameProtocol;
import bdfh.serializable.GsonSerializer;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.*;
import java.util.logging.*;

import static bdfh.protocol.Protocoly.*;

/**
 * @author Daniel Gonzalez Lopez
 * @author Bryan Curchod
 * @version 1.0
 */
public class GameLogic extends Thread {
	
	private static final int NB_DECKCARD = 20;
	private static final int STANDARD_GO_AMOUNT = 200;
	private static final int STANDARD_TAXE_AMOUNT = 200;
	private ArrayDeque<ClientHandler> players;
	
	private ArrayDeque<Card> Deck;
	private Board board;
	private int nbDice;
	private int totalLastRoll;
	
	// Map a player to his fortune. The first cell of the tab is the capital,
	// and the second is the total of his possession (capital + nbHouse + Hypotheques + ... )
	private Map<Integer, Integer[]> playersFortune;
	private final int CAPITAL = 0;
	private final int VPOSSESSION = 1;
	
	private ClientHandler currentPlayer;
	
	private final static Logger LOG = Logger.getLogger("GameLogic");
	
	/**
	 * constructor of a logic session. Define the turns, generate the board, and apply the parameters
	 *
	 * @param lobby lobby that launched a logic
	 */
	public GameLogic(Lobby lobby) {
		LOG.log(Level.INFO, "construction du gameLogic");
		preparePlayers(lobby);
		prepareDeck();
		LOG.log(Level.INFO, "Génération du plateau");
		board = new Board(players);
		
		// send the id, the username, and their capital
		sendPlayers(lobby.getParam().getMoneyAtTheStart());
		String boardJSON = board.jsonify();
		notifyPlayers(GameProtocol.GAM_BOARD, boardJSON);
		nbDice = lobby.getParam().getNbrDice();
	}
	
	private void sendPlayers(int capitalDepart) {
		
		JsonArray playerList = new JsonArray();
		
		for(ClientHandler c : players){
			JsonObject player = new JsonObject();
			JsonPrimitive id = new JsonPrimitive(c.getClientID());
			JsonPrimitive username = new JsonPrimitive(c.getClientUsername());
			JsonPrimitive capital = new JsonPrimitive(capitalDepart);
			
			player.add("id", id);
			player.add("username", username);
			player.add("capital", capital);
			
			playerList.add(player);
		}
		
		notifyPlayers(GameProtocol.GAM_PLYR, GsonSerializer.getInstance().toJson(playerList));
		
	}
	
	/**
	 * Get the current player of the turn.
	 *
	 * @return  the current player.
	 */
	public ClientHandler getCurrentPlayer() {
		return currentPlayer;
	}
	
	/**
	 * Generate a random deck
	 */
	private void prepareDeck() {
		
		LOG.log(Level.INFO, "Préparation du deck...");
		Deck = new ArrayDeque<>(NB_DECKCARD);
		ArrayList<Card> cardList = DatabaseConnect.getInstance().getCardDB().getCards();
		Random rdm = new Random();
		
		int pos;
		while (!cardList.isEmpty()) {
			// we get a randomly chosen card
			pos = rdm.nextInt(cardList.size());
			Card card = cardList.get(pos);
			
			// we add it to the deck
			Deck.addFirst(card);
			
			// we reduce the available quantity. if it get to 0, we remove the card from the list
			card.setQuantity(card.getQuantity() - 1);
			if (card.getQuantity() <= 0) {
				cardList.remove(card);
			}
		}
		LOG.log(Level.INFO, "deck generated : " + Deck);
	}
	
	/**
	 * create and store the players with their own possessions.
	 *
	 * @param lobby
	 */
	private void preparePlayers(Lobby lobby) {
		
		LOG.log(Level.INFO, "Préparation des joueurs");
		players = new ArrayDeque<>(lobby.getPlayers().size());
		playersFortune = new HashMap<>();
		ArrayList<ClientHandler> tab = new ArrayList<>(lobby.getPlayers());
		Random rdm = new Random();
		int startCapital = lobby.getParam().getMoneyAtTheStart();
		
		while (!tab.isEmpty()) {
			int pos = rdm.nextInt(tab.size());
			ClientHandler c = tab.remove(pos);
			players.addFirst(c);
			playersFortune.put(c.getClientID(), new Integer[] { lobby.getParam().getMoneyAtTheStart(),0 }); // TODO à vérifier...
		}
		
		
	}
	
	public void buySquare(ClientHandler caller, int posSquare){
		if(caller.getClientID() == currentPlayer.getClientID()){
			Price price = board.getSquare(posSquare).getPrices();
			notifyPlayers(GameProtocol.GAM_PAY, Integer.toString(price.getPrice()));
			notifyPlayers(GameProtocol.GAM_BUYS, Integer.toString(posSquare));
			playersFortune.get(currentPlayer.getClientID())[VPOSSESSION] += price.getHypothec();
			manageMoney(currentPlayer, -1*price.getPrice());
			board.setOwner(currentPlayer, posSquare);
			LOG.log(Level.INFO, currentPlayer.getClientUsername() + " bought the square " + posSquare);
		}
	}
	
	/**
	 * Roll the dices and move the player
	 */
	public void rollDice(ClientHandler player) {
		
		if (currentPlayer.getClientID() == player.getClientID()) {
			Random dice = new Random();
			ArrayList<Integer> rolls = new ArrayList<Integer>(nbDice);
			int total = 0;
			String rollsStr = "";
			boolean didADouble = false;
			
			for (int i = 0; i < nbDice; ++i) {
				int roll = dice.nextInt(6) + 1;
				if (rolls.contains(roll)) {
					didADouble = true;
				}
				
				rolls.add(roll);
				total += roll;
				rollsStr += " " + roll;
			}
			
			// notify the players
			notifyPlayers(GameProtocol.GAM_ROLL, rollsStr);
			
			if(!didADouble){
				players.addLast(players.pop());
			}
			
			// move the player
			totalLastRoll = total;
			LOG.log(Level.INFO, currentPlayer.getClientUsername() + " rolled " + rolls);
			boolean passedGo = board.movePlayer(currentPlayer.getClientID(), total);
			
			if(passedGo){
				playersFortune.get(currentPlayer.getClientID())[CAPITAL] += STANDARD_GO_AMOUNT;
				notifyPlayers(GameProtocol.GAM_GAIN, Integer.toString(STANDARD_GO_AMOUNT));
				LOG.log(Level.INFO, currentPlayer.getClientUsername() + " passed the start square");
			}
			
			// MANAGING THE CASE EFFECT
			Square current = board.getCurrentSquare(currentPlayer.getClientID());
			if(current.isBuyable() && current.getOwner() == null){
				currentPlayer.sendData(GameProtocol.GAM_FREE, Integer.toString(current.getPosition()));
			} else {
				board.manageEffect(this, current);
			}
		}
	}
	
	public int getTotalLastRoll() {
		
		return totalLastRoll;
	}
	
	/**
	 * Draw a card for the current player and manage its effect.
	 */
	public void drawCard() {
		
		LOG.log(Level.INFO, "Deck avant pioche : " + Deck.toString());
		Card drawed = Deck.pop();
		LOG.log(Level.INFO, "Deck après pioche : " + Deck.toString());
		
		// notify the players
		notifyPlayers(GameProtocol.GAM_DRAW, drawed.jsonify());
		
		// wait the confirmation of the current player before handling the effect
		
		
		// check if can keep the card, if not, we put it in the end of the deck
		if (drawed.getAction() != GameProtocol.CARD_FREE) {
			
			String[] fullAction = drawed.getFullAction().split(" ");
			
			// Handle the effect
			int value;
			
			switch(drawed.getAction()) {
				
				case GameProtocol.CARD_MOVE:
					value = Integer.parseInt(fullAction[1]);
					board.movePlayer(getCurrentPlayerID(), value);
					notifyPlayers(GameProtocol.GAM_MOV, String.valueOf(board.getCurrentSquare(getCurrentPlayerID()).getPosition()));
					
					LOG.log(Level.INFO, currentPlayer.getClientUsername() + " a avancé de " + value + " cases et se "
							+ "trouve ici : " + board.getCurrentSquare(getCurrentPlayerID()).getPosition() + ".");
					break;
					
				case GameProtocol.CARD_BACK:
					value = Integer.parseInt(fullAction[1]);
					board.movePlayer(getCurrentPlayerID(), value * -1);
					notifyPlayers(GameProtocol.GAM_MOV, String.valueOf(board.getCurrentSquare(getCurrentPlayerID()).getPosition()));

					LOG.log(Level.INFO, currentPlayer.getClientUsername() + " a reculé de " + value + " cases et se "
							+ "trouve ici : " + board.getCurrentSquare(getCurrentPlayerID()).getPosition() + ".");
					break;
					
				case GameProtocol.CARD_WIN:
					int amount = Integer.parseInt(fullAction[1]);
					manageMoney(currentPlayer, amount);
					notifyPlayers(GameProtocol.GAM_GAIN, String.valueOf(amount));
					
					LOG.log(Level.INFO, currentPlayer.getClientUsername() + " a recu " + amount + ".-");
					break;
					
				case GameProtocol.CARD_LOSE:
					amount = Integer.parseInt(fullAction[1]);
					manageMoney(currentPlayer, amount * -1);
					notifyPlayers(GameProtocol.GAM_PAY, String.valueOf(amount));
					
					LOG.log(Level.INFO, currentPlayer.getClientUsername() + " a payé " + amount + ".-");
					break;
					
				case GameProtocol.CARD_GOTO:
					value = Integer.parseInt(fullAction[1]);
					int actualPosition = board.getCurrentSquare(getCurrentPlayerID()).getPosition();
					
					// Get the number of squares the player has to move to go to the square given
					if(value > actualPosition) {
						value = value - actualPosition;
					
					} else if (value < actualPosition) {
						value = Board.NB_SQUARE - 1 - actualPosition + value;
					}
					
					board.movePlayer(getCurrentPlayerID(), value);
					notifyPlayers(GameProtocol.GAM_MOV, String.valueOf(board.getCurrentSquare(getCurrentPlayerID()).getPosition()));
					
					LOG.log(Level.INFO, currentPlayer.getClientUsername() + " s'est déplacé sur la case : " +
							board.getCurrentSquare(getCurrentPlayerID()).getPosition() + ".");
					break;
					
				case GameProtocol.CARD_EACH:
					
					amount = Integer.parseInt(fullAction[1]);
					
					// Each player pays the current player
					for(ClientHandler player : players) {
						
						if(player != currentPlayer) {
							manageMoney(player, amount * -1);
						}
					}
					
					// The current player receives the money
					amount = (amount * (players.size() - 1));
					manageMoney(currentPlayer, amount);
					notifyPlayers(GameProtocol.GAM_GAIN, String.valueOf(amount));
					
					LOG.log(Level.INFO, currentPlayer.getClientUsername() + " a recu " + amount + ".- de chaque joueur.");
					break;
					
				case GameProtocol.CARD_REP:
					// TODO
					break;
			}
			
			// Put the card at the end of the deck
			Deck.addLast(drawed);
			
		} else {
			
			// TODO SPRINT X
			// add the card to its owner
		}
	}
	
	@Override public void run() {
		
		boolean endGame = false;
		
		nextTurn();
		
		while (!endGame) {
			
			/*
			// next player
			currentPlayer = players.getFirst();
			
			currentPlayer.sendData(GAM_PLAY);
			// we wait the client signal to roll the dices
			String answer = currentPlayer.getAnswer();
			
			if(answer != null) {
				didADouble = rollDice();
				
				drawCard();
				// get the new player's square
				Square currentSquare = board.getCurrentSquare(currentPlayer.getClientID());
				switch (currentSquare.getFamily()) {
					case CARD:
						drawCard();
						break;
					// TODO SPRINT X ajouter le traitement des autres cases
				}
				
				// end turn
				if (didADouble) {
					didADouble = false;
				} else {
					currentPlayer.sendData(GAM_ENDT);
					players.addLast(players.pop());
				}
			} // TODO SPRINT X player inactivity
			*/
		}
		
	}
	
	private void nextTurn() {
		LOG.log(Level.INFO, "Player queue : " + players.toString());
		currentPlayer = players.getFirst();
		notifyPlayers(GameProtocol.GAM_PLAY, "");
		LOG.log(Level.INFO, "It's the turn of " + currentPlayer.getClientUsername() + " to play.");
	}
	
	public void endTurn( ClientHandler c) {
		if(c.getClientID() == currentPlayer.getClientID()) {
			LOG.log(Level.INFO, currentPlayer.getClientUsername() + " ended his turn" );
			//players.addLast(currentPlayer);
			currentPlayer = null;
			nextTurn();
		}
	}
	
	private void notifyPlayers(String cmd, String data) {
		
		String param = "";
		
		if (cmd != GameProtocol.GAM_BOARD && currentPlayer != null) {
			param += currentPlayer.getClientID();
		}
		
		if(data != ""){
			param += " ";
		}
		
		param += data;
		LOG.log(Level.INFO, "sending to players : " + cmd + " " + param);
		for (ClientHandler c : players) {
			c.sendData(cmd, param);
		}
		
	}
	
	public int getCurrentPlayerID() {
		
		return currentPlayer.getClientID();
	}
	
	/**
	 * Update the money of a player (add or remove money).
	 *
	 * @param player    Target of the change.
	 * @param amount    Amount to add/remove.
	 */
	public void manageMoney(ClientHandler player, int amount) {
		playersFortune.get(player.getClientID())[CAPITAL] += amount;
		
		// TODO - check if the game is over for the player
	}
}
