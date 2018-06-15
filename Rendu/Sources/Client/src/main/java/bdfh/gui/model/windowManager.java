package bdfh.gui.model;


import bdfh.gui.controller.IWindow;

/**
 * @version 1.0
 * @authors Bryan Curchod
 */
public class windowManager {
	
	private IWindow lobbyFrame = null;
	private IWindow gameFrame = null;
	
	private windowManager() {}
	
	private static class Instance {
		
		static final windowManager instance = new windowManager();
	}
	
	public static windowManager getInstance() {
		
		return Instance.instance;
	}
	
	public void setBoard(IWindow lobbyFrame) {
		
		this.gameFrame = lobbyFrame;
	}
	
	public void setLobbyList(IWindow gameFrame) {
		
		this.lobbyFrame = gameFrame;
	}
	
	public boolean hasGameBoard() {
		
		return gameFrame != null;
	}
	
	public void displayLobbyList() {
		
		if (gameFrame != null && lobbyFrame != null) {
			lobbyFrame.show();
			gameFrame.hide();
			
		}
	}
	
	public void displayBoard() {
		
		if (gameFrame != null && lobbyFrame != null) {
			gameFrame.show();
			lobbyFrame.hide();
		}
	}
}
