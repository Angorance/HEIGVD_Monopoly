package bdfh.logic.usr;

import bdfh.AuthenticationForTest;
import bdfh.ClientForTest;
import bdfh.exceptions.ConnectionException;
import bdfh.logic.conn.Authentication;
import bdfh.net.client.Client;
import bdfh.net.client.Notification;
import bdfh.net.protocol.NotifProtocol;
import bdfh.net.protocol.Protocoly;
import bdfh.serializable.LightLobbies;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class PlayerTest {
	
	@BeforeAll
	public static void startingTests() {
		
		try {
			Client.getInstance().connect();
		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*for (int i = 0; i < 100; ++i) {
			Authentication.register("JUnit" + i, "tests");
		}*/
	}
	
	@AfterAll
	public static void endingTests() {
		
		try {
			Client.getInstance().disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void shouldCreateLobby() {
		
		Authentication.login("JUnit", "tests");
		
		assertTrue(Player.getInstance().createLobby(2, 2000, 1, false));
		
	}
	
	@Test
	public void shouldCreateReadyQuitLobby() {
		
		Authentication.login("JUnit", "tests");
		
		assertTrue(Player.getInstance().createLobby(2, 2000, 1, false));
		assertTrue(Player.getInstance().setReady());
		assertTrue(Player.getInstance().quitLobby());
	}
	
	@Test
	public void shouldJoinReadyQuitExistingLobby() {
		
		Authentication.login("JUnit", "tests");
		assertTrue(Player.getInstance().createLobby(2, 2000, 1, false));
		Player.getInstance().setReady();
		
		ClientForTest c1 = null;
		ClientForTest c2 = null;
		ClientForTest c3 = null;
		
		try {
			
			c1 = new ClientForTest();
			c2 = new ClientForTest();
			c3 = new ClientForTest();
			
			c1.connect();
			c2.connect();
			c3.connect();
			
			AuthenticationForTest a1 = new AuthenticationForTest(c1);
			AuthenticationForTest a2 = new AuthenticationForTest(c2);
			AuthenticationForTest a3 = new AuthenticationForTest(c3);
			
			
			a1.login("JUnit1", "tests");
			a2.login("JUnit2", "tests");
			a3.login("JUnit3", "tests");
			
			assertTrue(c1.joinLobby(0));
			assertTrue(c3.joinLobby(0));
			assertTrue(c2.joinLobby(0));
			
			assertTrue(c1.quitLobby());
			
			assertTrue(c2.setReady());
			assertTrue(c3.setReady());
			
			assertTrue(c2.quitLobby());
			assertTrue(c3.quitLobby());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				c1.disconnect();
				c2.disconnect();
				c3.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@Test
	public void shouldNotJoinFullLobby() {
		
		Authentication.login("JUnit", "tests");
		assertTrue(Player.getInstance().createLobby(2, 2000, 1, false));
		Player.getInstance().setReady();
		
		ClientForTest c1 = null;
		ClientForTest c2 = null;
		ClientForTest c3 = null;
		ClientForTest c4 = null;
		
		try {
			
			c1 = new ClientForTest();
			c2 = new ClientForTest();
			c3 = new ClientForTest();
			c4 = new ClientForTest();
			
			c1.connect();
			c2.connect();
			c3.connect();
			c4.connect();
			
			AuthenticationForTest a1 = new AuthenticationForTest(c1);
			AuthenticationForTest a2 = new AuthenticationForTest(c2);
			AuthenticationForTest a3 = new AuthenticationForTest(c3);
			AuthenticationForTest a4 = new AuthenticationForTest(c4);
			
			
			a1.login("JUnit1", "tests");
			a2.login("JUnit2", "tests");
			a3.login("JUnit3", "tests");
			a4.login("JUnit4", "tests");
			
			assertTrue(c1.joinLobby(0));
			assertTrue(c3.joinLobby(0));
			assertTrue(c2.joinLobby(0));
			assertFalse(c4.joinLobby(0));
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				c1.disconnect();
				c2.disconnect();
				c3.disconnect();
				c4.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void shouldReceiveGoodFirstNotificationWhenStartingNotificationClient() {
		
		ArrayList<ClientForTest> clients = new ArrayList<>();
		
		for (int i = 0; i < 8; ++i) {
			ClientForTest c = new ClientForTest();
			
			try {
				c.connect();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			clients.add(c);
			new AuthenticationForTest(c).login("JUnit" + i, "tests");
		}
		
		for (int i = 0; i < 8; ++i) {
			
			clients.get(i).createLobby(new Parameter(2, 2000, 1, false));
		}
		
		try {
			Socket tmp = new Socket(Protocoly.SERVER, Protocoly.NPORT);
			BufferedReader in = new BufferedReader(
					new InputStreamReader(tmp.getInputStream()));
			
			assertEquals(NotifProtocol.NOTIF_LIST + " "
							+ "[{\"key\":0,\"value\":\"{\\\"ID\\\":0,\\\"Users\\\":[\\\"JUnit0\\\"],\\\"Ready\\\":[false]}\"},{\"key\":1,\"value\":\"{\\\"ID\\\":1,\\\"Users\\\":[\\\"JUnit1\\\"],\\\"Ready\\\":[false]}\"},{\"key\":2,\"value\":\"{\\\"ID\\\":2,\\\"Users\\\":[\\\"JUnit2\\\"],\\\"Ready\\\":[false]}\"},{\"key\":3,\"value\":\"{\\\"ID\\\":3,\\\"Users\\\":[\\\"JUnit3\\\"],\\\"Ready\\\":[false]}\"},{\"key\":4,\"value\":\"{\\\"ID\\\":4,\\\"Users\\\":[\\\"JUnit4\\\"],\\\"Ready\\\":[false]}\"},{\"key\":5,\"value\":\"{\\\"ID\\\":5,\\\"Users\\\":[\\\"JUnit5\\\"],\\\"Ready\\\":[false]}\"},{\"key\":6,\"value\":\"{\\\"ID\\\":6,\\\"Users\\\":[\\\"JUnit6\\\"],\\\"Ready\\\":[false]}\"},{\"key\":7,\"value\":\"{\\\"ID\\\":7,\\\"Users\\\":[\\\"JUnit7\\\"],\\\"Ready\\\":[false]}\"}]",
					in.readLine());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			for (int i = 0; i < 8; ++i) {
				
				try {
					clients.get(i).disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Test
	public void shouldCreateLobbyInstances() {
		
		ArrayList<ClientForTest> clients = new ArrayList<>();
		
		for (int i = 0; i < 3; ++i) {
			ClientForTest c = new ClientForTest();
			
			try {
				c.connect();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			clients.add(c);
			new AuthenticationForTest(c).login("JUnit" + i, "tests");
		}
		
		for (int i = 0; i < 3; ++i) {
			
			clients.get(i).createLobby(new Parameter(2, 2000, 1, false));
		}
		
		try {
			Notification.getInstance().run();
			
			LightLobbies ls = Notification.getInstance().getLobbies();
			
			assertEquals(0, ls.getLobbies().get(0).getID());
			assertEquals(1, ls.getLobbies().get(1).getID());
			assertEquals(2, ls.getLobbies().get(2).getID());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
