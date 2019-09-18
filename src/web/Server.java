package web;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private ServerSocket serverSocket;
    private ArrayList<ConnectionToClient> clientList;


    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        clientList = new ArrayList<>();
    }

    public void start() throws IOException {
        Socket socket = null;

        while(clientList.size() < 5){
            System.out.println("Waiting for a client");
            socket = serverSocket.accept();

            System.out.println("Got a client");
            clientList.add(new ConnectionToClient(socket, this));
        }

        System.out.println(clientList.size());
    }

    public ArrayList<ConnectionToClient> getClientList() {
        return clientList;
    }




}
