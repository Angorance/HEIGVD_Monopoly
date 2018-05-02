package bdfh.net.client;

import org.junit.jupiter.api.*;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class ClientTest {
	
	private static Client cl = Client.getInstance();
	
	@BeforeAll
	public static void todo() {
		try {
			cl.connect();
			cl.register("alreadyExist", "doesitwork?");
			cl.disconnect();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@BeforeEach
	public void connect() {
		try {
			cl.connect();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@AfterEach
	public void disconnect() {
		try {
			cl.disconnect();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void shouldRegisterNewUser() {
		
		Random r = new Random();
		
		try {
			
			assertTrue(cl.register("testClient" + r.nextInt(1000), "doesitwork?"));
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void shouldNotRegister() {
		try {
			assertFalse(cl.register("alreadyExist", "doesitwork?"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void shouldLoginExistentUser() {
		try {
			assertEquals(1, cl.login("alreadyExist", "doesitwork?"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void shouldNotLoginInexistentUsername() {
		try {
			assertEquals(0, cl.login("alreadyKABOOMExist", "doesitwork?"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void shouldNotLoginWrongCredentials() {
		try {
			assertEquals(-1, cl.login("alreadyExist", "wrong"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
