package bdfh.client;

public class Client {


    private Client() {
    }

    private static class Instance {
        static final Client instance = new Client();
    }

    public static Client getInstance() {
        return Instance.instance;
    }
}
