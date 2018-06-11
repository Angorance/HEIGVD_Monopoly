package bdfh.net.protocol;

/**
 * @author Daniel Gonzalez Lopez
 * @version 1.1
 */
public abstract class Protocoly {

	public static final String SERVER = "10.192.91.119";
	
	public static final int CPORT = 4242;
	public static final int NPORT = 9071;
	
	public static final String CMD_RGSTR = "RGSTR";
	public static final String CMD_LOGIN = "LOGIN";
	public static final String CMD_JOIN = "JOIN";
	public static final String CMD_RDY = "READY";
	public static final String CMD_NEWLOBBY = "NLOBBY";
	public static final String CMD_QUITLOBBY = "QLOBBY";
	public static final String CMD_BYE = "BYE";
	public static final String CMD_SETP= "SETP";
	
	public static final String ANS_ERR = "ERR";
	public static final String ANS_CONN = "WLCM";
	public static final String ANS_SUCCESS = "OK";
	public static final String ANS_UKNW = "UKNW";
	public static final String ANS_DENIED = "DENIED";
	public static final String ANS_BYE = "SEEYA";
}
