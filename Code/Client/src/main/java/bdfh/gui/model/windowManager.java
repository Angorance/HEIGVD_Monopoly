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
	
	public void setConnectionFrame(IWindow lobbyFrame) {
		
		this.lobbyFrame = lobbyFrame;
	}
	
	public void setMainFrame(IWindow gameFrame) {
		
		this.gameFrame = gameFrame;
	}
	
	public boolean hasMainframe() {
		
		return gameFrame != null;
	}
	
	public void displayMainFrame() {
		
		if (gameFrame != null && lobbyFrame != null) {
			lobbyFrame.hide();
			gameFrame.show();
		}
	}
	
	public void displayConnectionFrame() {
		
		if (gameFrame != null && lobbyFrame != null) {
			lobbyFrame.show();
			gameFrame.hide();
		}
	}
}
