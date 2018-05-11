package bdfh.serializable;

import bdfh.data.Lobby;

public class LightLobby {
	
	private int ID;
	private String[] players = new String[Lobby.MAX_PLAYER];
	private boolean[] readys = new boolean[Lobby.MAX_PLAYER];
	
	public LightLobby(Lobby l) {
		
		ID = l.getID();
		for (int i = 0; i < Lobby.MAX_PLAYER; ++i) {
			players[i] = l.getPlayers().get(i).getClientUsername();
			readys[i] = l.getAreReady().get(i);
		}
	}
}
