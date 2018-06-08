package bdfh.net.client;

import bdfh.exceptions.*;
import bdfh.logic.usr.*;
import bdfh.net.protocol.GameProtocol;
import bdfh.net.protocol.Protocoly;
import bdfh.serializable.*;

import java.io.*;
import java.net.Socket;
import java.util.logging.*;

import static bdfh.net.protocol.GameProtocol.*;

// TODO - Maybe handle better the exceptions...

/**
 * Client class.
 * Implements the methods needed to communicate with the server.
 *
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class Client {
	
	private static int COUCH = 0;
	private static int CINE = 1;
	
	
	private static Logger LOG = Logger.getLogger("Client");
	
	private Socket clientSocket = null;
	private BufferedReader in = null;
	private PrintWriter out = null;
	private String response;
	
	private Thread gamehandler = new Thread(GameHandler.getInstance());
	private int lobbyID = -1;
	
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
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintWriter(clientSocket.getOutputStream());
			
			// Prepare the GameHandler with the current streams
			GameHandler.getInstance().initialise(in, out);
			
			// Check if the connection is successful
			response = in.readLine();
			
			LOG.log(Level.INFO, "RECEIVED: " + response);
			
			if (!handleConnectionAnswer(response)){
				throw new ConnectionException(
						"A problem happened during Connection");
			}
			
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Client::connect: " + e);
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
			
			LOG.log(Level.INFO, "RECEIVED: " + response);
			
			if (!response.equals(Protocoly.ANS_BYE)) {
				LOG.log(Level.SEVERE, "Disconnection failed from server.");
			}
			
			Notification.getInstance().pause();
			
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Client::disconnect: " + e);
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
		
		sendData(Protocoly.CMD_RGSTR + " " + usr + " " + password);
		
		try {
			response = in.readLine();
			
			LOG.log(Level.INFO, "RECEIVED: " + response);
			
			if (response.equals(Protocoly.ANS_SUCCESS)) {
				success = true;
			} else if (response.equals(Protocoly.ANS_DENIED)) {
				success = false;
			} else {
				throw new CredentialsException("Problem with Registration");
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Client::register: " + e);
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
		
		sendData(Protocoly.CMD_LOGIN + " " + usr + " " + password);
		
		try {
			response = in.readLine();
			
			LOG.log(Level.INFO, "RECEIVED: " + response);
			
			if (response.equals(Protocoly.ANS_SUCCESS)) {
				result = 1;
			} else if (response.equals(Protocoly.ANS_UKNW)) {
				result = 0;
			} else if (response.equals(Protocoly.ANS_DENIED)) {
				result = -1;
			} else {
				throw new CredentialsException("Problem with Login");
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Client::login: " + e);
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
		
		LOG.log(Level.INFO, "SENT: " + data);
		
		// Print the data and flush the stream.
		out.println(data);
		out.flush();
	}
	
	/**
	 * Handle answers after connection and initialize the bounds of parameters.
	 *
	 * @param response  Response to handle.
	 *
	 * @return  true if the connection is successful, false otherwise.
	 */
	private boolean handleConnectionAnswer(String response) {
		
		String[] splitted = response.split(" ", 2);
		
		if (!splitted[0].equals(Protocoly.ANS_CONN)) {
			return false;
		}
		
		Player.setBounds(GsonSerializer.getInstance()
				.fromJson(splitted[1], BoundParameters.class));
		
		return true;
	}
	
	/**
	 * Create a new lobby on the game server.
	 *
	 * @param parameters    Parameters used in the new lobby.
	 *
	 * @return  true if the lobby is created, false otherwise.
	 */
	public boolean createLobby(Parameter parameters) {
		
		boolean result = false;
		
		// Send the new lobby to the server with its parameters
		String jsonParam = GsonSerializer.getInstance().toJson(parameters);
		sendData(Protocoly.CMD_NEWLOBBY + " " + jsonParam);
		
		try {
			String line = in.readLine();
			LOG.log(Level.INFO, "RECEIVED: " + line);
			
			String[] s = line.split(" ");
			response = s[0];
			
			if (response.equals(Protocoly.ANS_SUCCESS)) {
				result = true;
				int lobbyID = Integer.parseInt(s[1]);
				Player.getInstance().setLobbyID(lobbyID);
				
			} else if (response.equals(Protocoly.ANS_DENIED)) {
				result = false;
			}
			
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Client::createLobby: " + e);
			result = false;
		}
		
		return result;
	}
	
	/**
	 * Join a lobby on the game server.
	 *
	 * @param lobbyID   ID of the lobby to join.
	 *
	 * @return true if the lobby is joined, false otherwise.
	 */
	public boolean joinLobby(int lobbyID) {
		
		boolean result = false;
		
		sendData(Protocoly.CMD_JOIN + " " + lobbyID);
		
		try {
			response = in.readLine();
			LOG.log(Level.INFO, "RECEIVED: " + response);
			
			if (response.equals(Protocoly.ANS_SUCCESS)) {
				Player.getInstance().setLobbyID(lobbyID);
				result = true;
				
			} else if (response.equals(Protocoly.ANS_DENIED)) {
				result = false;
			}
			
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Client::joinLobby: " + e);
			result = false;
		}
		
		return result;
	}
	
	/**
	 * The player notifies the game server that he's ready.
	 *
	 * @return  true if the lobby is left, false otherwise.
	 */
	public boolean setReady() {
		
		boolean result = false;
		
		// Send the command to the server
		sendData(Protocoly.CMD_RDY);
		
		try {
			response = in.readLine();
			LOG.log(Level.INFO, "RECEIVED: " + response);
			
			if (response.equals(Protocoly.ANS_SUCCESS)) {
				
				result = true;
				
			} else if (response.equals(Protocoly.ANS_DENIED)) {
				result = false;
			}
			
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Client::setReady: " + e);
			result = false;
		}
		
		return result;
	}
	
	/**
	 * Quit the current lobby on the game server.
	 *
	 * @return  true if the lobby is left, false otherwise.
	 */
	public boolean quitLobby() {
		
		boolean result = false;
		
		// Send the command to the server
		sendData(Protocoly.CMD_QUITLOBBY);
		
		try {
			response = in.readLine();
			LOG.log(Level.INFO, "RECEIVED: " + response);
			
			if (response.equals(Protocoly.ANS_SUCCESS)) {
				Player.getInstance().setLobbyID(-1);
				result = true;
				
			} else if (response.equals(Protocoly.ANS_DENIED)) {
				result = false;
			}
			
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Client::quitLobby: " + e);
			result = false;
		}
		
		return result;
	}
	
	/**
	 * Allows the player to buy or sell either a couch or a home cinema.
	 * Send the command to the server and manage the response.
	 *
	 * @param buy
	 * @param type
	 * @param pos
	 */
	public void buySellImprovements(boolean buy, int type, int pos) {
		
		if (type == COUCH) {
			if (buy) {
				// Send the command to the server
				sendData(GAM_BCOUCH + " " + pos);
				
			} else {
				// Send the command to the server
				sendData(GameProtocol.GAM_SCOUCH + " " + pos);
			}
		} else if (type == CINE) {
			if (buy) {
				// Send the command to the server
				sendData(GameProtocol.GAM_BHCINE + " " + pos);
			} else {
				// Send the command to the server
				sendData(GameProtocol.GAM_SHCINE + " " + pos);
			}
		}
		
		manageResponse();
	}
	
	/**
	 * Allows the player to set or cancel a mortgage on a property
	 * @param set define if the method is called to set (true) or to cancel (false) the mortgage
	 * @param position position of the concerned property
	 */
	public void setCancelMortgage(boolean set, int position) {
		// TODO validate ~~Bry
		
		if(set){
			sendData(GameProtocol.GAM_HYPOT + " " + position);
		} else {
			sendData(GameProtocol.GAM_NHYPOT + " " + position);
		}
		
		manageResponse();
	}
	
	/**
	 * allows the player to buy or sell a property
	 * @param buy define if the method is called to buy (true) or to sell (false) the property
	 * @param position position of the concerned property
	 */
	public void buySellSquare(boolean buy, int position) {
		// TODO validate ~~Bry
		
		if(buy){
			sendData((GameProtocol.GAM_BUYS + " " + position));
		} else {
			sendData((GameProtocol.GAM_SELL + " " + position));
		}
		
		manageResponse();
	}
	
	private void manageResponse(){
		
		// TODO - Add log messages (board logs)
		
		try {
			response = in.readLine();
			
			String[] split = response.split(" ", 2);
			
			String command = split[0];
			String[] param = split[0].split(" ");
			
			switch (command) {
				case GAM_BCOUCH:
					GameHandler.getInstance().getBoard().getSquares().get(Integer.parseInt(param[1])).toggleCouch(1);
					break;
					
				case GAM_SCOUCH:
					GameHandler.getInstance().getBoard().getSquares().get(Integer.parseInt(param[1])).toggleCouch(-1);
					break;
					
				case GAM_BHCINE:
					GameHandler.getInstance().getBoard().getSquares().get(Integer.parseInt(param[1])).toggleHCine(true);
					break;
					
				case GAM_SHCINE:
					GameHandler.getInstance().getBoard().getSquares().get(Integer.parseInt(param[1])).toggleHCine(false);
					break;
					
					
			}
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
