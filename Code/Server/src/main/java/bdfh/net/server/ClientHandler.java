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

import static bdfh.protocol.GameProtocol.*;
import static bdfh.protocol.Protocoly.ANS_DENIED;
import static bdfh.protocol.Protocoly.ANS_ERR;
import static bdfh.protocol.Protocoly.ANS_SUCCESS;

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
		
		String bounds = GsonSerializer.getInstance().toJson(BoundParameters.getInstance());
		
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
				
				LOG.log(Level.INFO, "CMD RECEIVED BY PLAYER" + clientID + ": " + line);
				
				// instruction processing
				switch (cmd) {
					case Protocoly.CMD_BYE:
						sendData(Protocoly.ANS_BYE);
						
						byebye();
						
						LOG.log(Level.INFO, "Closing connection...");
						
						break;
					
					case Protocoly.CMD_LOGIN: // USER LOGIN
						
						// we try to log the user in
						int result = database.getPlayerDB().playerExists(param[0], param[1]);
						
						if (result == 0) {
							sendData(Protocoly.ANS_UNKNOWN);
							
							LOG.log(Level.INFO, "Username not found");
							
						} else if (result == -1) {
							sendData(Protocoly.ANS_DENIED);
							
							LOG.log(Level.INFO, "Wrong credentials");
							
						} else {
							sendData(ANS_SUCCESS);
							clientID = result;
							clientUsername = param[0];
							
							LOG.log(Level.INFO, "User " + clientID + " connected");
						}
						
						
						break;
					
					case Protocoly.CMD_RGSTR: // USER REGISTER
						
						if (database.getPlayerDB().createPlayer(param[0], param[1])) {
							// username free, we retrieve the user ID
							clientID = database.getPlayerDB().playerExists(param[0], param[1]);
							clientUsername = param[0];
							
							sendData(ANS_SUCCESS);
							
							LOG.log(Level.INFO, "User created in database");
							
						} else {
							// username already taken
							sendData(Protocoly.ANS_DENIED);
							
							LOG.log(Level.INFO,
									"Registration failed: " + "Username already in database.");
							
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
					case GameProtocol.GAM_ROLL:
						if (game != null) {
							game.rollDice(this);
						}
						break;
					
					case GameProtocol.GAM_ENDT:
						if (game != null) {
							game.endTurn(this);
						}
						break;
					
					case GameProtocol.GAM_BUYS:
						if (game != null) {
							handleResponse(game.buySquare(this, Integer.valueOf(param[0]))); // TODO -  pas sûr...
						}
						break;
					
					case GameProtocol.GAM_SELL:
						handleResponse(game.sellSquare(this, Integer.valueOf(param[0])));
						break;
						
					case GameProtocol.GAM_HYPOT:
						handleResponse(game.setMortgaged(this, Integer.valueOf(param[0]))); // TODO -  pas sûr...
						break;
					
					case GameProtocol.GAM_NHYPOT:
						handleResponse(game.disencumbrance(this, Integer.valueOf(param[0]))); // TODO -  pas sûr...
						break;
					
					case GameProtocol.GAM_BCOUCH:
						handleResponse(game.buyCouch(this, Integer.parseInt(param[0])));
						break;
					
					case GameProtocol.GAM_SCOUCH:
						if (game.sellCouch(this, Integer.parseInt(param[0]), false)) {
							sendData(ANS_SUCCESS);
						} else {
							sendData(ANS_DENIED);
						}
						
						break;
					
					case GameProtocol.GAM_BHCINE:
						handleResponse(game.buyHomeCinema(this, Integer.parseInt(param[0])));
						break;
					
					case GameProtocol.GAM_SHCINE:
						if (game.sellHomeCinema(this, Integer.parseInt(param[0]))) {
							sendData(ANS_SUCCESS);
						} else {
							sendData(ANS_DENIED);
						}
						
						break;
					
					case GameProtocol.GAM_FRDM_U:
						game.useFreedomCard();
						break;
					
					case GameProtocol.GAM_FRDM_T:
						game.payExamTax();
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
			} catch (SocketException e) {
				
				byebye();
				LOG.log(Level.SEVERE,
						"Client " + clientID + " disconnected suddenly. (SocketException)");
			}
		}
	}
	
	/**
	 * Handle the responses of the server to the client based on ERROR codes and messages.
	 *
	 * @param err Error code.
	 */
	private void handleResponse(int err) {
		
		switch (err) {
			case SUCCESS: // 0
				sendData(ANS_SUCCESS);
				break;
			
			case NOT_ENOUGH_MONEY: // 1
				sendData(ANS_ERR, ERR1);
				break;
			
			case NOT_ENOUGH_COUCHES: // 2
				sendData(ANS_ERR, ERR2);
				break;
			
			case BAD_DISTRIBUTION: // 3
				sendData(ANS_ERR, ERR3);
				break;
			
			case FULL: // 4
				sendData(ANS_ERR, ERR4);
				break;
			
			case NOT_FULL_FAMILY: // 5
				sendData(ANS_ERR, ERR5);
				break;
			
			case NOT_YOUR_TURN: // 6
				sendData(ANS_ERR, ERR6);
				break;
				
			case NOT_OWNER: // 7
				sendData(ANS_ERR, ERR7);
				break;
				
			case ALREADY_OWNED : // 8
				sendData(ANS_ERR, ERR8);
				break;
			case NOT_BUYABLE : // 9
				sendData(ANS_ERR, ERR9);
				break;
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
		
		//if (writer != null) {
			String toSend = cmd;
			
			if (param != "") {
				toSend += " " + param;
			}
			
			writer.println(toSend);
			writer.flush();
			
			LOG.log(Level.INFO, "SENT: " + toSend);
		//}
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
			
			Parameter p = GsonSerializer.getInstance().fromJson(param, Parameter.class);
			
			lobby = Lobbies.getInstance().createLobby(this, p);
			
			LOG.log(Level.INFO, "Lobby " + lobby.getID() + " created.");
			
			sendData(ANS_SUCCESS, Integer.toString(lobby.getID()));
		} catch (JsonSyntaxException e) {
			
			sendData(Protocoly.ANS_DENIED);
			
			LOG.log(Level.SEVERE, "Problem with Json syntax.");
		}
	}
	
	private void joinLobby(String lobbyID) {
		
		lobby = Lobbies.getInstance().joinLobby(this, Integer.parseInt(lobbyID));
		
		if (lobby != null) {
			
			LOG.log(Level.INFO, "Player " + clientID + " joined lobby " + lobby.getID());
			
			sendData(ANS_SUCCESS);
		} else {
			
			LOG.log(Level.SEVERE, "Impossible to join lobby.");
			
			sendData(Protocoly.ANS_DENIED);
		}
	}
	
	private void setReady() {
		
		if (lobby != null) {
			Lobbies.getInstance().setReady(lobby, this);
			
			LOG.log(Level.INFO, "Player " + clientID + " is ready!");
			
			sendData(ANS_SUCCESS);
			
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
			
			LOG.log(Level.INFO, "Player " + clientID + " left lobby" + lobbyLeft);
			
			sendData(ANS_SUCCESS);
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
	
	public String toString() {
		
		return getClientID() + "-" + getClientUsername();
	}
	
	/**
	 * Used only for JUnit tests !!!
	 * @param username
	 */
	public void setPlayer(int id, String username) {
		this.clientID = id;
		this.clientUsername = username;
	}
}