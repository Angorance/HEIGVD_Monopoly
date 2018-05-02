package bdfh.net;

import bdfh.database.DatabaseConnect;

import java.io.*;
import static bdfh.protocol.Protocoly.*;

/**
 * Handle de dialog with a client
 * @version 1.0
 * @authors Bryan Curchod
 */
public class ClientHandler {
	BufferedReader reader;
	PrintWriter writer;
	int clientID;
	
	public void handle(InputStream in, OutputStream out) throws IOException {
		reader = new BufferedReader(new InputStreamReader(in));
		writer = new PrintWriter(new OutputStreamWriter(out));
		DatabaseConnect database = DatabaseConnect.getInstance();
		
		writer.write(ANS_CONN);
		writer.flush();
		
		// Dialog management
		while(true){
			// divide the string in two parts : the command (in the 7 first char)
			// and the eventual arguments
			String[] line = reader.readLine().split(" ", 7);
			
			String cmd = line[0];
			String[] param;
			
			// instruction processing
			switch(cmd){
				case CMD_BYE :
					writer.write(ANS_BYE);
					break;
				case CMD_LOGIN : // USER LOGIN
					param = line[0].split(" ");
					// we try to log the user in
					int result = database.getPlayerDB().playerExists(param[0], param[1]);
					if(result == 0){
						writer.write(ANS_UNKNOWN);
					} else if(result == -1){
						writer.write(ANS_FAIL);
					} else {
						writer.write(ANS_SUCCESS);
						clientID = result;
					}
					writer.flush();
					break;
				case CMD_RGSTR : // USER REGISTER
					param = line[0].split(" ");
					if(database.getPlayerDB().createPlayer(param[0], param[1])){
						// username free, we retrieve the user ID
						clientID = database.getPlayerDB().playerExists(param[0], param[1]);
						writer.write(ANS_SUCCESS);
					} else {
						// username already taken
						writer.write(ANS_FAIL);
					}
					writer.flush();
					break;
				case CMD_SHOWLOBBY :
					break;
				case CMD_JOINLOBBY:
					break;
				case CMD_QUITLOBBY:
					break;
				case CMD_RDY :
					break;
				case CMD_NEWLOBBY :
					break;
				default : // WTF ???
					writer.write("U wot m8 ?");
					writer.flush();
					break;
			}
			
			
		}
		
	}
	
}