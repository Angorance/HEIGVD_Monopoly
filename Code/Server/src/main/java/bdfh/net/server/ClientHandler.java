package bdfh.net.server;

import bdfh.data.Lobbies;
import bdfh.data.Lobby;
import bdfh.database.DatabaseConnect;
import bdfh.net.Handler;
import bdfh.serializable.BoundParameters;
import bdfh.serializable.GsonSerializer;
import bdfh.serializable.Parameter;
import com.google.gson.JsonArray;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static bdfh.protocol.Protocoly.*;

/**
 * Handle de dialog with a client
 *
 * @author Bryan Curchod
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class ClientHandler implements Handler {
	
	private BufferedReader reader;
	private PrintWriter writer;
	private int clientID;
	private String clientUsername;
	
	Lobby lobby;
	
	private final static Logger LOG = Logger.getLogger("ClientHandler");
	
	/**
	 * handle de discussion with a client
	 *
	 * @param in Input stream to receive commands.
	 * @param out Output stream to send commands / info.
	 *
	 * @throws IOException
	 */
	public void handle(InputStream in, OutputStream out) throws IOException {
		
		reader = new BufferedReader(new InputStreamReader(in));
		writer = new PrintWriter(new OutputStreamWriter(out));
		DatabaseConnect database = DatabaseConnect.getInstance();
		boolean connected = true;
		
		String bounds = GsonSerializer.getInstance()
				.toJson(BoundParameters.getInstance());
		
		sendData(ANS_CONN, bounds);
		
		// Dialog management
		while (connected) {
			
			String line;
			String cmd;
			String[] param;
			String[] args;
			
			// TODO - COMMENTS
			line = reader.readLine();
			
			args = line.split(" ");
			
			cmd = args[0];
			
			param = new String[args.length - 1];
			System.arraycopy(args, 1, param, 0, param.length);
			
			LOG.log(Level.INFO, "CMD RECEIVED BY PLAYER" + clientID + ": " + line);
			
			// instruction processing
			switch (cmd) {
				case CMD_BYE:
					sendData(ANS_BYE);
					connected = false;
					
					LOG.log(Level.INFO, "Closing connection...");
					
					break;
				
				case CMD_LOGIN: // USER LOGIN
					
					// we try to log the user in
					int result = database.getPlayerDB()
							.playerExists(param[0], param[1]);
					if (result == 0) {
						sendData(ANS_UNKNOWN);
						
						LOG.log(Level.INFO, "Username not found");
						
					} else if (result == -1) {
						sendData(ANS_DENIED);
						
						LOG.log(Level.INFO, "Wrong credentials");
						
					} else {
						sendData(ANS_SUCCESS);
						clientID = result;
						clientUsername = param[0];
						
						LOG.log(Level.INFO, "User " + clientID + " connected");

						sendLobbyList();
					}

					
					break;
				
				case CMD_RGSTR: // USER REGISTER
					
					if (database.getPlayerDB()
							.createPlayer(param[0], param[1])) {
						// username free, we retrieve the user ID
						clientID = database.getPlayerDB()
								.playerExists(param[0], param[1]);
						clientUsername = param[0];
						
						sendData(ANS_SUCCESS);
						
						LOG.log(Level.INFO, "User created in database");

						sendLobbyList();
						
					} else {
						// username already taken
						sendData(ANS_DENIED);
						
						LOG.log(Level.INFO, "Registration failed: "
								+ "Username already in database.");
						
					}
					
					break;
				
				case CMD_JOINLOBBY:
					
					joinLobby(param[0]);
					break;
				
				case CMD_QUITLOBBY:
					
					quitLobby();
					break;
				
				case CMD_RDY:
					
					setReady();
					break;
				
				case CMD_NEWLOBBY:
					
					createLobby(param[0]);
					break;
				
				default: // WTF ???
					sendData("U wot m8 ?");
					
					LOG.log(Level.SEVERE, "User CMD not found!");
					
					break;
			}
		}
	}

	private void sendLobbyList() {
		JsonArray lobbyList = new JsonArray();
		Lobbies.getInstance().getLobbies().forEach((key, lobby) -> {
			lobbyList.add(GsonSerializer.getInstance().toJson(lobby));
		});

		sendData(lobbyList.getAsString());
	}

	/**
	 * TODO
	 *
	 * @param cmd
	 * @param param
	 */
	private void sendData(String cmd, String param) {
		
		String toSend = cmd;
		
		if (param != "") {
			toSend += " " + param;
		}
		
		writer.println(toSend);
		writer.flush();
	}
	
	/**
	 * Send data (commands / info) through the output stream.
	 *
	 * @param cmd Data to send (either a command or info).
	 */
	private void sendData(String cmd) {
		
		sendData(cmd, "");
	}
	
	/**
	 * TODO
	 *
	 * @param param
	 */
	private void createLobby(String param) {
		
		try {
			
			Parameter p = GsonSerializer.getInstance()
					.fromJson(param, Parameter.class);
			
			lobby = Lobbies.getInstance().createLobby(this, p);
			
			LOG.log(Level.INFO, "Lobby " + lobby.getID() + " created.");
			
			sendData(ANS_SUCCESS);
		} catch (JsonSyntaxException e) {
			
			sendData(ANS_DENIED);
			
			LOG.log(Level.SEVERE, "Problem with Json syntax.");
		}
	}
	
	private void quitLobby() {
		
		if (lobby != null) {
			int lobbyLeft = lobby.getID();
			
			lobby.quitLobby(this);
			lobby = null;
			
			LOG.log(Level.INFO, "Player " + clientID + " left lobby" + lobbyLeft);
			
			sendData(ANS_SUCCESS);
		} else {
			
			sendData(ANS_DENIED);
			
			LOG.log(Level.SEVERE, "Not in a lobby...");
		}
	}
	
	private void joinLobby(String lobbyID) {
		
		lobby = Lobbies.getInstance()
				.joinLobby(this, Integer.parseInt(lobbyID));
		
		if (lobby != null) {
			
			LOG.log(Level.INFO,
					"Player " + clientID + " joined lobby " + lobby.getID());
			
			sendData(ANS_SUCCESS);
		} else {
			
			LOG.log(Level.SEVERE, "Impossible to join lobby.");
			
			sendData(ANS_DENIED);
		}
	}
	
	private void setReady() {
		
		if (lobby != null) {
			lobby.setReady(this);
			
			LOG.log(Level.INFO, "Player " + clientID + " is ready!");
			
			sendData(ANS_SUCCESS);
		} else {
			
			LOG.log(Level.SEVERE, "Not in a lobby...");
			
			sendData(ANS_DENIED);
		}
		
	}

	public String getClientUsername() {
		return clientUsername;
	}
}