package bdfh.logic.game;

import bdfh.logic.saloon.Lobby;
import bdfh.net.server.ClientHandler;
import bdfh.serializable.Parameter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class CardTest {
	
	static ClientHandler p1;
	static ClientHandler p2;
	
	@BeforeAll
	public static void toto() {
		p1 = new ClientHandler();
		p2 = new ClientHandler();
		
		p1.setPlayer(1, "toto");
		p2.setPlayer(2, "tata");
	}
	
	@Test
	public void testAllCards() {
		Lobby tmp = new Lobby(new Parameter(2, 2000, 0, 0));
		
		tmp.joinLobby(p1);
		tmp.joinLobby(p2);
		
		tmp.setReady(p1);
		tmp.setReady(p2);
		
		GameLogic game = new GameLogic(tmp);
		
		game.start();
		
		System.out.println("\n\n=============================================================\n");
		
		for (int i = 0; i < 29; ++i) {
			
			game.drawCard();
		}
		
		System.out.println("\n\n=============================================================\n");
	}
}
