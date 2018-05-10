package bdfh.logic.usr;

import bdfh.exceptions.ConnectionException;
import bdfh.logic.conn.Authentication;
import bdfh.net.client.Client;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class UserTest {
	
	@BeforeAll
	public static void startingTests() {
		
		try {
			Client.getInstance().connect();
		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Authentication.register("JUnit", "tests");
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
		
		assertTrue(User.getInstance().createLobby(2, 2000, false));
		
	}
}
