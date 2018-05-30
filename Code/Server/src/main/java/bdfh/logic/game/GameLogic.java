package bdfh.logic.game;

import bdfh.database.CardDB;
import bdfh.net.server.ClientHandler;
import bdfh.logic.saloon.Lobby;
import bdfh.protocol.GameProtocol;
import com.mysql.fabric.xmlrpc.Client;

import javax.swing.plaf.ColorUIResource;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
		
		String boardJSON = board.jsonify();
		notifyPlayers(GAM_BOARD, boardJSON);
		nbDice = lobby.getParam().getNbrDice();
	}
	
	/**
	 * Generate a random deck
	 */
	private void prepareDeck() {
		
		LOG.log(Level.INFO, "Préparation du deck...");
		Deck = new ArrayDeque<>(NB_DECKCARD);
		ArrayList<Card> cardList = CardDB.getInstance().getCards();
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
			playersFortune.put(c.getClientID(), new Integer[] { lobby.getParam().getMoneyAtTheStart(),
					lobby.getParam().getMoneyAtTheStart() }); // TODO à vérifier...
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
			notifyPlayers(GAM_ROLL, rollsStr);
			
			if(!didADouble){
				players.addLast(players.pop());
			}
			
			// move the player
			boolean passedGo = board.movePlayer(currentPlayer.getClientID(), total);
			
			if(passedGo){
				playersFortune.get(currentPlayer.getClientID())[CAPITAL] += STANDARD_GO_AMOUNT;
				notifyPlayers(GAM_GAIN, Integer.toString(STANDARD_GO_AMOUNT));
			}
			
		}
	}
	
	private void drawCard() {
		
		LOG.log(Level.INFO, "Deck avant pioche : " + Deck.toString());
		Card drawed = Deck.pop();
		LOG.log(Level.INFO, "Deck après pioche : " + Deck.toString());
		// notify the players
		String cardJson = drawed.jsonify();
		notifyPlayers(GAM_DRAW, cardJson);
		
		// check if can keep the card, if not, we put it in the end of the deck
		if (drawed.getAction() != GameProtocol.CARD_FREE) {
			// TODO SPRINT X handling the effect
			Deck.addLast(drawed);
		} else {
			// Deck.addLast(drawed);
			// TODO SPRINT X
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
		LOG.log(Level.INFO, "nouveau tour");
		currentPlayer = players.getFirst();
		notifyPlayers(GAM_PLAY, "");
	}
	
	public void endTurn( ClientHandler c) {
		if(c.getClientID() == currentPlayer.getClientID()) {
			LOG.log(Level.INFO, "Fin du tour tour");
			//players.addLast(currentPlayer);
			currentPlayer = null;
			nextTurn();
		}
	}
	
	private void notifyPlayers(String cmd, String data) {
		
		String param = "";
		
		if (cmd != GAM_BOARD && currentPlayer != null) {
			param += currentPlayer.getClientUsername();
		}
		
		param += " " + data;
		for (ClientHandler c : players) {
			c.sendData(cmd, param);
		}
	}
}
