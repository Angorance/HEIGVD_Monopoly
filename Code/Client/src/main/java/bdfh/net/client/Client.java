package bdfh.net.client;

import bdfh.net.protocol.Protocoly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @version 1.0
 * @authors Daniel Gonzalez Lopez
 */
public class Client {
	
	private Socket clientSocket = null;
	private BufferedReader in = null;
	private PrintWriter out = null;
	
	private String response;
	
	
	private Client() {}
	
	private static class Instance {
		
		static final Client instance = new Client();
	}
	
	public static Client getInstance() {
		
		return Instance.instance;
	}
	
	public void connect() throws IOException {
		
		clientSocket = new Socket(Protocoly.SERVER, Protocoly.PORT);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		out = new PrintWriter(clientSocket.getOutputStream());
	}
	
	public void disconnect() throws IOException {
		
		sendData(Protocoly.CMD_BYE);
		
		response = in.readLine();
		
		if (response.equals(Protocoly.ANS_BYE)) {
			// Disconnected
		}
		
		if (in != null) {
			in.close();
		}
		
		if (out != null) {
			out.close();
		}
		
		if (clientSocket != null) {
			clientSocket.close();
		}
	}
	
	private void sendData(String data) {
		
		// Print the data and flush the stream.
		out.println(data);
		out.flush();
	}
}
