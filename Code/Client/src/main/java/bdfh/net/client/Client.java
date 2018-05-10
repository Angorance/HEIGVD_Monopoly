package bdfh.net.client;

import bdfh.exceptions.ConnectionException;
import bdfh.exceptions.CredentialsException;
import bdfh.logic.usr.User;
import bdfh.net.protocol.Protocoly;
import bdfh.serializable.BoundParameters;
import bdfh.serializable.GsonSerializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static bdfh.net.protocol.Protocoly.*;

// TODO - Maybe handle better the exceptions...

/**
 * Client class.
 * Implements the methods needed to communicate with the server.
 *
 * @author Daniel Gonzalez Lopez
 * @version 1.0
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
	 * @throws IOException
	 */
	public void connect() throws ConnectionException, IOException {
		
		try {
			clientSocket = new Socket(Protocoly.SERVER, Protocoly.CPORT);
			in = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintWriter(clientSocket.getOutputStream());
			
			response = in.readLine();
			
			// TODO - Recover bound parameters.
			
			if (!handleConnectionAnswer(response)){
				throw new ConnectionException(
						"A problem happened during Connection");
			}
			
		} catch (IOException e) {
			System.out.println("Client::connect: " + e);
			throw e;
		}
	}
	
	/**
	 * Disconnect from the server and check if it is successful.
	 *
	 * @throws IOException
	 */
	public void disconnect() throws IOException {
		
		sendData(Protocoly.CMD_BYE);
		
		try {
			response = in.readLine();
			
			if (!response.equals(Protocoly.ANS_BYE)) {
				// TODO - Disconnection failed - What to do?
			}
		} catch (IOException e) {
			System.out.println("Client::disconnect: " + e);
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
	
	/**
	 * Register a user given his credentials.
	 * Fails if the username is not available.
	 *
	 * @param usr Username of the user.
	 * @param password Password of the user.
	 *
	 * @return True if registration successful, false otherwise.
	 */
	public boolean register(String usr, String password)
			throws CredentialsException, IOException {
		
		boolean success;
		
		sendData(CMD_RGSTR + " " + usr + " " + password);
		
		try {
			response = in.readLine();
			
			if (response.equals(ANS_SUCCESS)) {
				success = true;
			} else if (response.equals(ANS_DENIED)) {
				success = false;
			} else {
				throw new CredentialsException("Problem with Registration");
			}
		} catch (IOException e) {
			System.out.println("Client::register: " + e);
			throw e;
		}
		
		return success;
	}
	
	/**
	 * Log the user in given his credentials.
	 * Fails if the username does not exist or if the credentials does not
	 * match.
	 *
	 * @param usr Username of the user.
	 * @param password Password of the user.
	 *
	 * @return 1 if login successful, 0 if username unknown, -1 if password does
	 * 		not match with username.
	 *
	 * @throws CredentialsException if the server sends wrong answer.
	 * @throws IOException
	 */
	public int login(String usr, String password)
			throws CredentialsException, IOException {
		
		int result;
		
		sendData(CMD_LOGIN + " " + usr + " " + password);
		
		try {
			response = in.readLine();
			
			if (response.equals(ANS_SUCCESS)) {
				result = 1;
			} else if (response.equals(ANS_UKNW)) {
				result = 0;
			} else if (response.equals(ANS_DENIED)) {
				result = -1;
			} else {
				throw new CredentialsException("Problem with Login");
			}
		} catch (IOException e) {
			System.out.println("Client::login: " + e);
			throw e;
		}
		
		return result;
	}
	
	/**
	 * Send data (commands) to the server.
	 *
	 * @param data Data to send.
	 */
	private void sendData(String data) {
		
		// Print the data and flush the stream.
		out.println(data);
		out.flush();
	}
	
	private boolean handleConnectionAnswer(String response) {
		
		String[] splitted = response.split(" ", 2);
		
		if (!splitted[0].equals(ANS_CONN)) {
			return false;
		}
		
		User.setBounds(GsonSerializer.getInstance()
				.fromJson(splitted[1], BoundParameters.class));
		
		return true;
	}
}
