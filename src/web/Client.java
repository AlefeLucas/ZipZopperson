package web;

import controller.ChatClient;

import java.io.IOException;
import java.net.Socket;

public class Client {

    private ConnectionToServer server;
    private Socket socket;

    public Client(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
    }

    public void connect(ChatClient client) throws IOException {
        server = new ConnectionToServer(socket, client);
    }

    public ConnectionToServer getConnection(){
        return server;
    }

}
