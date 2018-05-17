package bdfh.net.notification;

import bdfh.game.*;
import bdfh.net.Handler;
import bdfh.protocol.ObsProtocol;
import bdfh.serializable.*;

import java.io.*;
import java.util.logging.*;

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
		
		sendData(json);
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
	 * Send game (commands / info) through the output stream.
	 *
	 * @param cmd Data to send (either a command or info).
	 */
	public void sendData(String cmd) {
		
		sendData(cmd, "");
	}
	
	public void update(int cmd, LightLobby l) {
	
		switch (cmd) {
			case ObsProtocol.NEW:
			
		}
	}
}
