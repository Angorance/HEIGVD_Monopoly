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
	 * TODO
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
	
	/**
	 * Send the whole list
	 */
	private void sendLobbyList() {
		
		String json = Lobbies.getInstance().jsonify();
		
		LOG.log(Level.INFO, "Lobbies: " + json);
		
		sendData(NotifProtocol.NOTIF_LIST + " " + json);
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
	 * Send logic (commands / info) through the output stream.
	 *
	 * @param cmd Data to send (either a command or info).
	 */
	public void sendData(String cmd) {
		
		sendData(cmd, "");
	}
	
	public void update(int cmd, Lobby l) {
		
		String json = l.jsonify();
		
		switch (cmd) {
			case NotifProtocol.NEW:
				
				sendData(NotifProtocol.NOTIF_NEW + " " + json);
				break;
				
			case NotifProtocol.UPDATE:
				
				sendData(NotifProtocol.NOTIF_UPDATE + " " + json);
				break;
				
			case NotifProtocol.DELETE:
				
				sendData(NotifProtocol.NOTIF_DELETE + " " + json);
				break;
		}
	}
}
