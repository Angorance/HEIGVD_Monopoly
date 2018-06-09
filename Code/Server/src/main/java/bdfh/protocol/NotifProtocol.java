package bdfh.protocol;

/**
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public abstract class NotifProtocol {
	
	public static final int NPORT = 9071;
	
	public static final String NOTIF_DELETE = "DELETED";
	public static final String NOTIF_UPDATE = "UPDATED";
	public static final String NOTIF_NEW = "NEW";
	public static final String NOTIF_LIST = "LIST";
	public static final String NOTIF_START = "START";
}
