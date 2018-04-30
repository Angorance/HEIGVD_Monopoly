package bdfh.net;

import java.io.*;
import static bdfh.net.Protocoly.*;

/**
 * @version 1.0
 * @authors Bryan Curchod
 */
public class ClientHandler {
	BufferedReader reader;
	PrintWriter writer;
	
	public void handle(InputStream in, OutputStream out) throws IOException {
		reader = new BufferedReader(new InputStreamReader(in));
		writer = new PrintWriter(new OutputStreamWriter(out));
		
		// Gestion des communications avec le client
		while(true){
			String line = reader.readLine();
			
			// extraire la commande de la chaine, laissant l'éventuel argument
			String cmd = "";
			
			// traitement de la commande
			switch(cmd){
				case CMD_BYE :
					break;
				case CMD_LOGIN :
					break;
				case CMD_RGSTR :
					break;
				case CMD_SHOWLOBBY :
					break;
				case CMD_JOINLOBBY:
					break;
				case CMD_QUITLOBBY:
					break;
				case CMD_RDY :
					break;
				case CMD_NEWLOBBY : // Création
					break;
				default : // WTF ???
					break;
			}
			
			
		}
		
	}
	
}