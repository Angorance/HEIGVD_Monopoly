package bdfh.protocol;

/**
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class NotifProtocol {
	
	public static final int NEW = 0;
	public static final int UPDATE = 1;
	public static final int DELETE = 2;
	
	public static final String NOTIF_DELETE = "DELETED";
	public static final String NOTIF_UPDATE = "UPDATED";
	public static final String NOTIF_NEW = "NEW";
	public static final String NOTIF_LIST = "LIST";
}
