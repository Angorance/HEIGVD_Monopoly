package bdfh.net.server;

import bdfh.database.DatabaseConnect;
import bdfh.logic.game.GameLogic;
import bdfh.logic.saloon.Lobbies;
import bdfh.logic.saloon.Lobby;
import bdfh.net.Handler;
import bdfh.protocol.GameProtocol;
import bdfh.protocol.Protocoly;
import bdfh.serializable.BoundParameters;
import bdfh.serializable.GsonSerializer;
import bdfh.serializable.Parameter;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	private boolean connected = true;
	private GameLogic game;
	
	private Lobby lobby;
	
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
		
		String bounds = GsonSerializer.getInstance()
				.toJson(BoundParameters.getInstance());
		
		sendData(Protocoly.ANS_CONN, bounds);
		
		// Dialog management
		while (connected) {
			
			String line;
			String cmd;
			String[] param;
			String[] args;
			
			
			try {
				
				line = reader.readLine();
				
				args = line.split(" ");
				
				cmd = args[0];
				
				
				param = new String[args.length - 1];
				System.arraycopy(args, 1, param, 0, param.length);
				
				LOG.log(Level.INFO,
						"CMD RECEIVED BY PLAYER" + clientID + ": " + line);
				
				// instruction processing
				switch (cmd) {
					case Protocoly.CMD_BYE:
						sendData(Protocoly.ANS_BYE);
						
						byebye();
						
						LOG.log(Level.INFO, "Closing connection...");
						
						break;
					
					case Protocoly.CMD_LOGIN: // USER LOGIN
						
						// we try to log the user in
						int result = database.getPlayerDB()
								.playerExists(param[0], param[1]);
						
						if (result == 0) {
							sendData(Protocoly.ANS_UNKNOWN);
							
							LOG.log(Level.INFO, "Username not found");
							
						} else if (result == -1) {
							sendData(Protocoly.ANS_DENIED);
							
							LOG.log(Level.INFO, "Wrong credentials");
							
						} else {
							sendData(Protocoly.ANS_SUCCESS);
							clientID = result;
							clientUsername = param[0];
							
							LOG.log(Level.INFO,
									"User " + clientID + " connected");
						}
						
						
						break;
					
					case Protocoly.CMD_RGSTR: // USER REGISTER
						
						if (database.getPlayerDB()
								.createPlayer(param[0], param[1])) {
							// username free, we retrieve the user ID
							clientID = database.getPlayerDB()
									.playerExists(param[0], param[1]);
							clientUsername = param[0];
							
							sendData(Protocoly.ANS_SUCCESS);
							
							LOG.log(Level.INFO, "User created in database");
							
						} else {
							// username already taken
							sendData(Protocoly.ANS_DENIED);
							
							LOG.log(Level.INFO, "Registration failed: "
									+ "Username already in database.");
							
						}
						
						break;
					
					case Protocoly.CMD_JOINLOBBY:
						
						joinLobby(param[0]);
						break;
					
					case Protocoly.CMD_QUITLOBBY:
						
						quitLobby();
						break;
					
					case Protocoly.CMD_RDY:
						
						setReady();
						break;
					
					case Protocoly.CMD_NEWLOBBY:
						
						createLobby(param[0]);
						break;
						
						// ============================================================================================
					// commande de phase de jeu
					case GameProtocol.GAM_ROLL :
						if(game!= null){
							game.rollDice(this);
						}
						break;
						
					case GameProtocol.GAM_ENDT :
						if(game != null){
							game.endTurn(this);
						}
						break;
						
					case GameProtocol.GAM_BUYS :
						if(game != null){
							game.buySquare(this, Integer.valueOf(param[1]));
						}
						break;
						
					case GameProtocol.GAM_HYPOT :
							game.setMortgaged(this, Integer.valueOf(param[1]));
						break;
						
					case GameProtocol.GAM_NHYPOT :
						game.disencumbrance(this, Integer.valueOf(param[1]));
						break;
					
					case GameProtocol.GAM_FRDM_U :
						game.useFreedomCard();
						break;
					
					default: // WTF ???
						sendData("U wot m8 ?");
						
						LOG.log(Level.SEVERE, "User CMD not found!");
						
						break;
				}
				
			} catch (NullPointerException e) {
				
				byebye();
				
				LOG.log(Level.SEVERE,
						"Client " + clientID + " disconnected suddenly. (NullPointerException)");
			} catch (SocketException e){
				
				byebye();
				LOG.log(Level.SEVERE,
						"Client " + clientID + " disconnected suddenly. (SocketException)");
			}
		}
	}
	
	@Override
	public void byebye() {
		
		connected = false;
		
		if (lobby != null) {
			lobby.quitLobby(this);
		}
		
	}
	
	/**
	 * TODO
	 *
	 * @param cmd
	 * @param param
	 */
	public void sendData(String cmd, String param) {
		
		String toSend = cmd;
		
		if (param != "") {
			toSend += " " + param;
		}
		
		writer.println(toSend);
		writer.flush();
	}
	
	/**
	 * Send logic (commands / info) through the output stream.
	 *
	 * @param cmd Data to send (either a command or info).
	 */
	public void sendData(String cmd) {
		
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
			
			sendData(Protocoly.ANS_SUCCESS, Integer.toString(lobby.getID()));
		} catch (JsonSyntaxException e) {
			
			sendData(Protocoly.ANS_DENIED);
			
			LOG.log(Level.SEVERE, "Problem with Json syntax.");
		}
	}
	
	private void joinLobby(String lobbyID) {
		
		lobby = Lobbies.getInstance()
				.joinLobby(this, Integer.parseInt(lobbyID));
		
		if (lobby != null) {
			
			LOG.log(Level.INFO,
					"Player " + clientID + " joined lobby " + lobby.getID());
			
			sendData(Protocoly.ANS_SUCCESS);
		} else {
			
			LOG.log(Level.SEVERE, "Impossible to join lobby.");
			
			sendData(Protocoly.ANS_DENIED);
		}
	}
	
	private void setReady() {
		
		if (lobby != null) {
			Lobbies.getInstance().setReady(lobby, this);
			
			LOG.log(Level.INFO, "Player " + clientID + " is ready!");
			
			sendData(Protocoly.ANS_SUCCESS);
			
			lobby.checkStartingCondition();
		} else {
			
			LOG.log(Level.SEVERE, "Not in a lobby...");
			
			sendData(Protocoly.ANS_DENIED);
		}
		
	}
	
	private void quitLobby() {
		
		if (lobby != null) {
			int lobbyLeft = lobby.getID();
			
			Lobbies.getInstance().quitLobby(lobby, this);
			lobby = null;
			
			LOG.log(Level.INFO,
					"Player " + clientID + " left lobby" + lobbyLeft);
			
			sendData(Protocoly.ANS_SUCCESS);
		} else {
			
			sendData(Protocoly.ANS_DENIED);
			
			LOG.log(Level.SEVERE, "Not in a lobby...");
		}
	}
	
	public String getClientUsername() {
		
		return clientUsername;
	}
	
	public int getClientID() {
		
		return clientID;
	}
	
	public void setGame(GameLogic game) {
		this.game = game;
	}
	
	public String toString(){
		return getClientID() + "-" +getClientUsername();
	}
}