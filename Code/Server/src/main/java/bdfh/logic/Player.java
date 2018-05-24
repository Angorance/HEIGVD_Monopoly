package bdfh.logic;

import bdfh.net.server.ClientHandler;

/**
 * @author Bryan Curchod
 * @version 1.0
 */
public class Player {
	ClientHandler player;
	int capital;
	
	public Player(ClientHandler player, int startingCapital){
		this.player = player;
		capital = startingCapital;
	}
	
}
