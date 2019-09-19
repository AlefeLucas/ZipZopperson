package main;

import web.Server;

public class ServerApp {
    public static void main(String[] args) throws Exception {
        //inicializa o servidor usando como porta, o valor "4321".
        Server server = new Server(4321);

        server.start();
    }
}
