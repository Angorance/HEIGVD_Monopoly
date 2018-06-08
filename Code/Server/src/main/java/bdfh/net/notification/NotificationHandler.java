package bdfh.net.notification;

import bdfh.logic.saloon.Lobbies;
import bdfh.logic.saloon.Lobby;
import bdfh.protocol.NotifProtocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class NotificationHandler {
	
	private Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;
	
	private final static Logger LOG = Logger.getLogger("NotificationHandler");
	
	public NotificationHandler(Socket s) {
		
		socket = s;
		
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(s.getOutputStream());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Initialize the writer (and the reader) for the notification writing
	 *
	 * @throws IOException
	 */
	public void init() throws IOException {
		
		boolean connected = true;
		
		Lobbies.getInstance().addSubscriber(this);
		sendLobbyList();
	}
	
	public void byebye() {
		
		Lobbies.getInstance().removeSub(this);
	}
	
	/**
	 * Send the whole list
	 */
	private void sendLobbyList() {
		
		String json = Lobbies.getInstance().jsonify();
		
		LOG.log(Level.INFO, "Lobbies: " + json);
		
		sendData(NotifProtocol.NOTIF_LIST + " " + json);
	}
	
	/**
	 * send a notification to the client
	 *
	 * @param cmd command of the notification
	 * @param param parameter of the command
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
	 * Send logic (commands / info) through the output stream.
	 *
	 * @param cmd Data to send (either a command or info).
	 */
	public void sendData(String cmd) {
		
		sendData(cmd, "");
	}
	
	public void update(String cmd, Lobby l) {
		
		String json = l.jsonify();
		
		LOG.log(Level.INFO, cmd + " Lobby: " + json);
		
		switch (cmd) {
			case NotifProtocol.NOTIF_NEW:
				sendData(NotifProtocol.NOTIF_NEW, json);
				break;
			
			case NotifProtocol.NOTIF_UPDATE:
				sendData(NotifProtocol.NOTIF_UPDATE, json);
				break;
			
			case NotifProtocol.NOTIF_DELETE:
				sendData(NotifProtocol.NOTIF_DELETE, json);
				break;
			
			case NotifProtocol.NOTIF_START:
				sendData(NotifProtocol.NOTIF_START, Integer.toString(l.getID()));
				
				try {
					String response = reader.readLine();
					
					if (response.equals("KILL")) {
						socket.close();
						NotificationServer.removeNotifier(this);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
				break;
		}
	}
}
