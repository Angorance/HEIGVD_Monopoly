package bdfh.net.notification;

import java.net.*;
import java.util.ArrayList;
import java.util.logging.*;

import static bdfh.protocol.NotifProtocol.NPORT;


/**
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class NotificationServer implements Runnable {
	
	private ServerSocket srv;
	
	private static ArrayList<NotificationHandler> notifiers = new ArrayList<>();
	
	private final static Logger LOG = Logger.getLogger("NotificationServer");
	
	private NotificationServer() {
		
		try {
			srv = new ServerSocket(NPORT);
			
			LOG.log(Level.INFO,
					"Server connected\nAddress::" + srv.getLocalSocketAddress()
							+ "\nPort::" + NPORT);
			
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Exception creating server socket: " + e);
			e.printStackTrace();
		}
	}
	
	private static class Instance {
		
		static final NotificationServer instance = new NotificationServer();
	}
	
	public static NotificationServer getInstance() {
		
		return Instance.instance;
	}
	
	/**
	 * Wait for clients connection and create client workers to handle the
	 * clients.
	 */
	@Override
	public void run() {
		
		while (true) {
			
			try {
				
				LOG.log(Level.INFO, "Notif: Waiting for new client to connect");
				
				Socket newClient = srv.accept();
				
				NotificationHandler tmp = new NotificationHandler(newClient);
				
				notifiers.add(tmp);
				
				tmp.init();
				
				LOG.log(Level.INFO, "Notif: Client accepted. Worker created and started");
				
			} catch (Exception e) {
				LOG.log(Level.SEVERE, "Notif: Exception accepting client connection: " + e);
				e.printStackTrace();
			}
		}
	}
	
	public synchronized static void removeNotifier(NotificationHandler n) {
		
		notifiers.remove(n);
	}
}