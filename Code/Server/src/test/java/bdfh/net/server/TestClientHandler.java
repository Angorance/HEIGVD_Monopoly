package bdfh.net.server;


import bdfh.protocol.Protocoly;
import bdfh.serializable.BoundParameters;
import bdfh.serializable.GsonSerializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class TestClientHandler {
	
	@BeforeAll
	public static void startingTests() {
		Thread t = new Thread(ConnectionServer.getInstance());
		t.start();
	}
	
	@Test
	public void ShouldSendSerialisedBoundParameters() {
		
		try {
			Socket s = new Socket(Protocoly.SERVER, Protocoly.CPORT);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintWriter out = new PrintWriter(s.getOutputStream());
			
			String response = in.readLine();
			
			String expected = Protocoly.ANS_CONN + " " + GsonSerializer.getInstance().toJson(BoundParameters.getInstance());
			
			System.out.println(response);
			assertEquals(expected, response);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
