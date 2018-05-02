package bdfh.net.protocol;

/**
 * @version 1.0
 * @author Daniel Gonzalez Lopez
 */
public abstract class Protocoly {

	public static final String SERVER = "localhost";
	
	public static final int PORT = 4242;
	
	
	public static final String CMD_RGSTR = "RGSTR";
	
	public static final String CMD_LOGIN = "LOGIN";
	
	public static final String CMD_SHOWLOBBY = "SLOBBY";
	
	public static final String CMD_JOIN = "JOIN";
	
	public static final String CMD_RDY = "READY";
	
	public static final String CMD_NEWLOBBY = "NLOBBY";
	
	public static final String CMD_QUITLOBBY = "QLOBBY";
	
	public static final String CMD_BYE = "BYE";
	
	
	public static final String ANS_CONN = "WLCM";
	
	public static final String ANS_SUCCESS = "OK";
	
	public static final String ANS_UKNW = "UKNW";
	
	public static final String ANS_DENIED = "FAIL";
	
	public static final String ANS_BYE = "SEEYA";
	
}
