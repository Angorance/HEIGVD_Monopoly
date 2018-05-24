package bdfh.logic.game;

import bdfh.database.CardDB;
import bdfh.logic.Player;
import bdfh.net.server.ClientHandler;
import bdfh.logic.saloon.Lobby;
import bdfh.protocol.Protocoly;
import com.mysql.fabric.xmlrpc.Client;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;

import static bdfh.protocol.Protocoly.*;
/**
 * @author Daniel Gonzalez Lopez
 * @author Bryan Curchod
 * @version 1.0
 */
public class GameLogic extends Thread {
	
	private static final int NB_DECKCARD = 20;
	private ArrayDeque<ClientHandler> players;
	private ArrayDeque<Card> Deck;
	private Board board;
	private int nbDice;
	
	private ClientHandler currentPlayer;
	
	/**
	 * constructor of a logic session. Define the turns, generate the board, and apply the parameters
	 * @param lobby lobby that launched a logic
	 */
	public GameLogic(Lobby lobby){
		preparePlayers(lobby);
		prepareDeck();
		board = new Board(players);
		nbDice = lobby.getParam().getNbrDice();
	}
	
	/**
	 * Generate a random deck
	 */
	private void prepareDeck() {
		Deck = new ArrayDeque<>(NB_DECKCARD);
		ArrayList<Card> cardList = CardDB.getInstance().getCards();
		Random rdm = new Random();
		
		int pos;
		while(!cardList.isEmpty()){
			// we get a randomly chosen card
			pos = rdm.nextInt(cardList.size());
			Card card = cardList.get(pos);
			
			// we add it to the deck
			Deck.addFirst(card);
			
			// we reduce the available quantity. if it get to 0, we remove the card from the list
			card.setQuantity(card.getQuantity()-1);
			if(card.getQuantity() <= 0){
				cardList.remove(card);
			}
		}
		
	}
	
	/**
	 * create and store the players with their own possessions.
	 * @param lobby
	 */
	private void preparePlayers(Lobby lobby) {
		players = new ArrayDeque<>(lobby.getPlayers().size());
		ArrayList<ClientHandler> tab = new ArrayList<>(lobby.getPlayers());
		Random rdm = new Random();
		
		while(!tab.isEmpty()){
			int pos = rdm.nextInt(tab.size());
			players.addFirst((tab.remove(pos)));
		}
	}
	
	/**
	 * Roll the dices and move the player
	 */
	private void rollDice(){
		Random dice = new Random();
		ArrayList<Integer> rolls = new ArrayList<Integer>(nbDice);
		int total = 0;
		String rollsStr = "";
		boolean didADouble = false;
		
		for(int i = 0; i < nbDice; ++i){
			int roll = dice.nextInt(5) +1;
			if(rolls.contains(roll)){
				didADouble = true;
			}
			
			rolls.add(roll);
			total += roll;
			rollsStr += " " + roll;
		}
		// notify the players
		notifyPlayers(GAM_ROLL, rollsStr);
		
		// move the player
		board.movePlayer(currentPlayer.getClientID(), total);
		
	}
	
	private void drawCard(){
		Card drawed = Deck.pop();
		
		// notify the players
		String cardJson = ""; // TODO SPRINT 4
		notifyPlayers(GAM_DRAW, cardJson);
		
		// check if can keep the card, if not, we put it in the end of the deck
		if(drawed.getEffect() != Card.EFFECTS.FREE){
			// TODO SPRINT X handling the effect
			Deck.addLast(drawed);
		} else {
			// TODO PRINT X
		}
	}
	
	@Override public void run() {
		boolean endGame = false;
		while(!endGame){
			
			currentPlayer = players.pop();
			
			currentPlayer.sendData(GAM_PLAY);
			
			// next player
			// roll dice
			//
			// TODO SPRINT 4 tirer les dés
			// TODO SPRINT 4 tirer une cartte
		}
		
	}
	
	private void notifyPlayers(String cmd, String data){
		for(ClientHandler c : players){
			c.sendData(cmd, currentPlayer.getClientUsername() + " " + data);
		}
	}
}
