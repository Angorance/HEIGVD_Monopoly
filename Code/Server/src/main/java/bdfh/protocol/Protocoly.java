package bdfh.protocol;

/**
 * Définit le protocole utilisé par les clients et le serveur
 *
 * @author Bryan Curchod
 * @version 1.0
 */
public class Protocoly {
	
	public static final String SERVER = "localhost";
	public static final int PORT = 4242;
	
	
	// commandes utilisée par le client
	public static final String CMD_RGSTR = "RGSTR";
	public static final String CMD_LOGIN = "LOGIN";
	public static final String CMD_SHOWLOBBY = "SLOBBY";
	public static final String CMD_JOINLOBBY = "JOIN";
	public static final String CMD_RDY = "READY";
	public static final String CMD_NEWLOBBY = "NLOBBY";
	public static final String CMD_QUITLOBBY = "QLOBBY";
	public static final String CMD_BYE = "BYE";
	
	// réponse du serveur
	public static final String ANS_CONN = "WLCM";
	public static final String ANS_SUCCESS = "OK";
	public static final String ANS_FAIL = "FAIL";
	public static final String ANS_DENIED = "DENIED";
	public static final String ANS_UNKNOWN = "UKNW";
	public static final String ANS_BYE = "SEEYA";
	
}
