package bdfh.net;

import bdfh.database.DatabaseConnect;

import java.io.*;

import static bdfh.protocol.Protocoly.*;

/**
 * Handle de dialog with a client
 *
 * @author Bryan Curchod
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class ClientHandler {
	
	private BufferedReader reader;
	private PrintWriter writer;
	private int clientID;
	
	public void handle(InputStream in, OutputStream out) throws IOException {
		
		reader = new BufferedReader(new InputStreamReader(in));
		writer = new PrintWriter(new OutputStreamWriter(out));
		DatabaseConnect database = DatabaseConnect.getInstance();
		boolean connected = true;
		
		sendData(ANS_CONN);
		
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
			
			// instruction processing
			switch (cmd) {
				case CMD_BYE:
					sendData(ANS_BYE);
					connected = false;
					break;
				case CMD_LOGIN: // USER LOGIN
					
					// we try to log the user in
					int result = database.getPlayerDB()
							.playerExists(param[0], param[1]);
					if (result == 0) {
						sendData(ANS_UNKNOWN);
					} else if (result == -1) {
						sendData(ANS_FAIL);
					} else {
						sendData(ANS_SUCCESS);
						clientID = result;
					}
					break;
				case CMD_RGSTR: // USER REGISTER
					
					if (database.getPlayerDB()
							.createPlayer(param[0], param[1])) {
						// username free, we retrieve the user ID
						clientID = database.getPlayerDB()
								.playerExists(param[0], param[1]);
						sendData(ANS_SUCCESS);
					} else {
						// username already taken
						sendData(ANS_FAIL);
					}
					break;
				case CMD_SHOWLOBBY:
					break;
				case CMD_JOINLOBBY:
					break;
				case CMD_QUITLOBBY:
					break;
				case CMD_RDY:
					break;
				case CMD_NEWLOBBY:
					break;
				default: // WTF ???
					sendData("U wot m8 ?");
					break;
			}
			
		}
		
	}
	
	private void sendData(String cmd, String param) {
		
		String toSend = cmd;
		
		if (param != "") {
			toSend += " " + param;
		}
		writer.println(toSend);
		writer.flush();
	}
	
	private void sendData(String cmd) {
		
		sendData(cmd, "");
	}
	
}