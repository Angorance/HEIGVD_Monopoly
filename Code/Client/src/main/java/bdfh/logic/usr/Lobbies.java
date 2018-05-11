package bdfh.logic.usr;

import com.google.gson.Gson;

import java.util.HashMap;

/**
 * @author Bryan Curchod
 * @version 1.0
 */
public class Lobbies {

    private HashMap<Integer, Lobby> lobbies = new HashMap<>();

    private static class Instance {
        static final Lobbies instance = new Lobbies();
    }

    public static Lobbies getInstance() {
        return Instance.instance;
    }

    /**
     * Initialize the lobby list from a JSON string.
     *
     * @param JSListLobby JSON string representing a list of lobby.
     */
    public void setLobbies(String JSListLobby) {
        Lobby[] list = new Gson().fromJson(JSListLobby, Lobby[].class);

        for(Lobby l : list){
           lobbies.put(l.getID(), l);
        }
    }

    private Lobbies() {}
    
    /**
     * Get a particular lobby of the list.
     *
     * @param ID    ID of the lobby wanted.
     *
     * @return  the lobby wanted.
     */
    public Lobby getLobby(int ID) {
        
        return lobbies.get(ID);
    }
    
    /**
     * Update a lobby if exists, add it to the lost otherwise
     *
     * @param l updated/new lobby
     */
    public void updateLobby(Lobby l) {
        
        lobbies.put(l.getID(), l);
    }

    /**
     * Remove a lobby from the hashmap
     *
     * @param ID lobby's ID to remove
     */
    public void removeLobby(int ID) {
        
        lobbies.remove(ID);
    }
}
