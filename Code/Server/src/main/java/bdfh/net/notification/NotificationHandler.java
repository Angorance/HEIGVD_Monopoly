package bdfh.net.notification;

import bdfh.net.Handler;

import java.io.*;
import java.util.logging.Logger;

import static bdfh.protocol.Protocoly.CMD_NEWLOBBY;

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
		
		//sendData(); TODO
		
		// Dialog management
		while (connected) {
			// TODO
		}
		
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
}
