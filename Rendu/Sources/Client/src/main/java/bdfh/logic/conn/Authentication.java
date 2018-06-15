package bdfh.logic.conn;

import bdfh.exceptions.CredentialsException;
import bdfh.logic.usr.Player;
import bdfh.net.client.Client;
import bdfh.net.client.Notification;

import java.io.IOException;
import java.security.*;

/**
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class Authentication {
	
	/**
	 * Empty constructor
	 */
	public Authentication() {}
	
	/**
	 * Create a new account for the player.
	 *
	 * @param username username of the new account.
	 * @param password password of the new account.
	 *
	 * @return true if register successful and false if username is already used.
	 */
	public static boolean register(String username, String password) {
		
		try {
			// Registration successful
			if (Client.getInstance().register(username, hashPassword(password))) {
				
				// Set the username
				Player.getInstance().setUsername(username);
				
				// Connect to the notification channel
				Thread notif = new Thread(Notification.getInstance());
				
				notif.start();
				
				return true;
			}
		} catch (CredentialsException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Connect the player to the game.
	 *
	 * @param username username to use for the login.
	 * @param password password to use for the login.
	 *
	 * @return 1 if login successful, 0 if username unknown, -1 if password does
	 * 		not match with username.
	 */
	public static int login(String username, String password) {
		
		int code = -1;
		
		try {
			code = Client.getInstance().login(username, hashPassword(password));
			
			// Login successful
			if (code == 1) {
				
				//Set he username
				Player.getInstance().setUsername(username);
				
				// Connect to the notification channel
				Thread notif = new Thread(Notification.getInstance());
				
				notif.start();
			}
		} catch (CredentialsException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return code;
	}
	
	/**
	 * check if passwords are equal.
	 *
	 * @param pass1 password.
	 * @param pass2 confirm password.
	 *
	 * @return true if passwords are equal, false otherwise.
	 */
	public static boolean checkPassword(String pass1, String pass2) {
		
		if (pass1.isEmpty() || pass2.isEmpty()) {
			return false;
		}
		
		return pass1.equals(pass2);
	}
	
	/**
	 * Method to hash the password
	 *
	 * @param passwordToHash the password to hash
	 *
	 * @return hashed password in hex format
	 */
	private static String hashPassword(String passwordToHash) {
		
		String hash = null;
		
		// Create MessageDigest instance for MD5
		MessageDigest md = null;
		
		try {
			md = MessageDigest.getInstance("MD5");
			
			// Add password bytes to digest
			md.update(passwordToHash.getBytes());
			
			// Get the hash's bytes
			byte[] bytes = md.digest();
			
			// This byte[] has bytes in decimal format
			// Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			
			for (int i = 0; i < bytes.length; ++i) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			
			//Get complete hashed password in hex format
			hash = sb.toString();
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return hash;
	}
}
