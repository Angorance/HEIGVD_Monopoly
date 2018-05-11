package bdfh.net.notification;

import bdfh.data.Lobbies;
import bdfh.data.Lobby;
import bdfh.net.Handler;
import bdfh.serializable.GsonSerializer;
import bdfh.serializable.LightLobby;
import com.google.gson.JsonArray;

import java.io.*;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;
import static bdfh.protocol.Protocoly.*;

/**
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class NotificationHandler implements Handler{
	
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
		JsonArray lobbyList = new JsonArray();
		Lobbies.getInstance().getLobbies().forEach((key, lobby) -> {
			lobbyList.add(GsonSerializer.getInstance().toJson(new LightLobby(lobby)));
		});

		sendData(NOT_LIST + " " + lobbyList.getAsString());
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
	public void sendData(String cmd) {
		
		sendData(cmd, "");
	}

}
