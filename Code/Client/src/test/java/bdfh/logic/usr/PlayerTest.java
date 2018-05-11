package bdfh.logic.usr;

import bdfh.AuthenticationForTest;
import bdfh.ClientForTest;
import bdfh.exceptions.ConnectionException;
import bdfh.logic.conn.Authentication;
import bdfh.net.client.Client;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
		
		Authentication.register("JUnit4", "tests");
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
		
		assertTrue(Player.getInstance().createLobby(2, 2000, false));
		
	}
	
	@Test
	public void shouldCreateReadyQuitLoby() {
		
		Authentication.login("JUnit", "tests");
		
		assertTrue(Player.getInstance().createLobby(2, 2000, false));
		assertTrue(Player.getInstance().setReady());
		assertTrue(Player.getInstance().quitLobby());
	}
	
	@Test
	public void shouldJoinReadyQuitExistingLobby() {
		
		Authentication.login("JUnit", "tests");
		assertTrue(Player.getInstance().createLobby(2, 2000, false));
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
		assertTrue(Player.getInstance().createLobby(2, 2000, false));
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
}
