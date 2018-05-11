package bdfh.logic.usr;

public class Lobby {
    private int ID;
    private String[] players;
    private boolean[] areReady;

    public boolean[] getAreReady() {
        return areReady;
    }

    public String[] getPlayers() {
        return players;
    }

    public int getID() {
        return ID;
    }
}
