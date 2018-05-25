package bdfh.net.notification;

import bdfh.logic.saloon.Lobbies;
import bdfh.logic.saloon.Lobby;
import bdfh.net.Handler;
import bdfh.protocol.NotifProtocol;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class NotificationHandler implements Handler {
	
	private BufferedReader reader;
	private PrintWriter writer;
	
	private final static Logger LOG = Logger.getLogger("NotificationHandler");
	
	/**
	 * initialize the writer (and the reader) for the notification writing
	 *
	 * @param in Input stream to receive commands.
	 * @param out Output stream to send commands / info.
	 *
	 * @throws IOException
	 */
	public void handle(InputStream in, OutputStream out) throws IOException {
		
		reader = new BufferedReader(new InputStreamReader(in));
		writer = new PrintWriter(new OutputStreamWriter(out));
		boolean connected = true;
		
		Lobbies.getInstance().addSubscriber(this);
		sendLobbyList();
		
		// Dialog management
		while (connected) {
			// TODO ???
		}
		
	}
	
	@Override public void byebye() {
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
				
			case NotifProtocol.NOTIF_START :
				sendData(NotifProtocol.NOTIF_START,  Integer.toString(l.getID()));
				break;
		}
	}
}
