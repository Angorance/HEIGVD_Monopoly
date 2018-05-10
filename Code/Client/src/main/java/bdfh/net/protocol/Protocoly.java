package bdfh.net.protocol;

/**
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public abstract class Protocoly {
	
	public static final String SERVER = "10.192.93.234";
	
	public static final int PORT = 4242;
	public static final int NPORT = 666;
	
	
	public static final String CMD_RGSTR = "RGSTR";
	public static final String CMD_LOGIN = "LOGIN";
	//public static final String CMD_SHOWLOBBY = "SLOBBY";
	public static final String CMD_JOIN = "JOIN";
	public static final String CMD_RDY = "READY";
	public static final String CMD_NEWGAME = "NGAME";
	public static final String CMD_QUITGAME = "QGAME";
	public static final String CMD_BYE = "BYE";
	
	
	public static final String ANS_CONN = "WLCM";
	public static final String ANS_SUCCESS = "OK";
	public static final String ANS_UKNW = "UKNW";
	public static final String ANS_DENIED = "DENIED";
	public static final String ANS_BYE = "SEEYA";
	
}
