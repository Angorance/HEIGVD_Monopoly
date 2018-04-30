package bdfh.logic.conn;

import bdfh.exceptions.CredentialsException;
import bdfh.logic.usr.User;
import bdfh.net.client.Client;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @version 1.0
 * @authors Daniel Gonzalez Lopez
 */
public class Authentication {
	
	/**
	 * TODO
	 */
	public Authentication(){}
	
	/**
	 * TODO
	 * @param username TODO
	 * @param password TODO
	 * @return TODO
	 */
	public static boolean register(String username, String password){
		
		try {
			if(Client.getInstance().register(username,hashPassword(password))){
				User.getInstance().setUsername(username);
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
	 * TODO
	 * @param username TODO
	 * @param password TODO
	 * @return TODO
	 */
	public static int login(String username,String password){
		
		try {
			int code = Client.getInstance().login(username,hashPassword(password));
			if(code == 1){
				User.getInstance().setUsername(username);
				return code;
			}else{
				return code;
			}
		} catch (CredentialsException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	/**
	 * TODO
	 * @param pass1 TODO
	 * @param pass2 TODO
	 * @return
	 */
	public static boolean checkPassword(String pass1, String pass2){
		if(pass1.isEmpty() || pass2.isEmpty()){
			return false;
		}
		return pass1.equals(pass2);
	}
	
	/**
	 * TODO
	 * @param passwordToHash TODO
	 * @return TODO
	 */
	private static String hashPassword(String passwordToHash){
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
			for(int i = 0; i < bytes.length; ++i){
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
