package bdfh.net.client;

import bdfh.exceptions.ConnectionException;
import bdfh.net.protocol.Protocoly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static bdfh.net.protocol.Protocoly.*;

/**
 * Client class.
 * Implements the methods needed to communicate with the server.
 *
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
	
	/**
	 * Connect to the server and check if it is successful.
	 *
	 * @throws ConnectionException if the server sends a wrong answer.
	 */
	public void connect() throws ConnectionException, IOException {
		
		try {
			clientSocket = new Socket(Protocoly.SERVER, Protocoly.PORT);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintWriter(clientSocket.getOutputStream());
			
			response = in.readLine();
			
			if (!response.equals(ANS_CONN)) {
				throw new ConnectionException("A problem happened during connection");
			}
			
		} catch (IOException e) {
			System.out.println("Client::connect: " + e);
			throw e;
		}
	}
	
	public void disconnect() throws IOException {
		
		sendData(Protocoly.CMD_BYE);
		
		try {
			response = in.readLine();
			
			if (!response.equals(Protocoly.ANS_BYE)) {
				// TODO - Disconnection failed - What to do?
			}
		} catch (IOException e) {
			System.out.println(e);
			throw e;
		} finally {
			
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
	}
	
	public void register(String usr, String password) {
		
		sendData(CMD_RGSTR + " " + usr + " " + password);
		
		try {
			response = in.readLine();
			
			if (response.equals(ANS_SUCCESS)) {
				// Registration done.
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void login(String usr, String password) {
		
		sendData(CMD_LOGIN + " " + usr + " " + password);
		
		try {
			response = in.readLine();
			
			if (response.equals(ANS_SUCCESS)) {
				// Login success
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private void sendData(String data) {
		
		// Print the data and flush the stream.
		out.println(data);
		out.flush();
	}
}
