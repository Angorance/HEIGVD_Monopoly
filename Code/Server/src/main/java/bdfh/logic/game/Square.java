package bdfh.logic.game;

import bdfh.net.server.ClientHandler;

/**
 * @author Bryan Curchod
 * @version 1.0
 */
public class Square {
	private int id;
	private int position;
	private int type;
	private String name;
	private int prix;
	
	ClientHandler owner;
	
	public Square(){
	
	}
	
	public int getId() {
		
		return id;
	}
	
	public int getPosition() {
		
		return position;
	}
	
	public int getPrix() {
		
		return prix;
	}
	
	public int getType() {
		
		return type;
	}
	
	public String getName() {
		
		return name;
	}
	
	
}
