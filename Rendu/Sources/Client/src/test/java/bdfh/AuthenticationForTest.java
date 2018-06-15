package bdfh;

import bdfh.logic.usr.Player;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class AuthenticationForTest {
	
	ClientForTest cl;
	
	/**
	 * TODO
	 */
	public AuthenticationForTest(ClientForTest c) {
		cl = c;
	}
	
	/**
	 * Login
	 *
	 * @param username username to use for the login
	 * @param password password to use for the login
	 *
	 * @return 1 if login successful, 0 if username unknown, -1 if password does
	 * 		not match with username.
	 */
	public int login(String username, String password) {
		
		int code = -1;
		
		try {
			code = cl.login(username, hashPassword(password));
			
			//login successful
			if (code == 1) {
				//Set he username
				Player.getInstance().setUsername(username);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return code;
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
		
		/* Create MessageDigest instance for MD5*/
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
			
			/*Add password bytes to digest*/
			md.update(passwordToHash.getBytes());
			
			/*Get the hash's bytes*/
			byte[] bytes = md.digest();
			
			//This byte[] has bytes in decimal format
			//Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; ++i) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			
			//Get complete hashed password in hex format
			hash = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return hash;
	}
}

