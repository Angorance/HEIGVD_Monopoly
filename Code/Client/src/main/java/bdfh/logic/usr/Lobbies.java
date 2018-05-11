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
     * initilize the lobby list from a JSON string
     * @param JSListLobby JSON string representing a list of lobby
     */
    public void setLobbies(String JSListLobby){
        Lobby[] list = new Gson().fromJson(JSListLobby, Lobby[].class);

        for(Lobby l : list){
           lobbies.put(l.getID(), l);
        }
    }

    private Lobbies() {}

    /**
     * update a lobby if exists, add it to the lost otherwise
     * @param l updated/new lobby
     */
    public void updateLobby(Lobby l){
        lobbies.put(l.getID(), l);
    }

    /**
     * remove a lobby from the hasmap
      * @param ID lobby's ID to remove
     */
    public void removeLobby(int ID){
        lobbies.remove(ID);
    }
}
