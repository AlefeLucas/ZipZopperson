package main;

import web.Server;

public class ServerApp {
    public static void main(String[] args) throws Exception {
        Server server = new Server(4321);

        server.start();
    }
}
