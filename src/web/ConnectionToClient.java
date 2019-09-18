package web;

import model.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

public class ConnectionToClient implements Runnable {
    private Socket socket;
    private Server server;

    private Thread thread;

    private ObjectOutputStream socketOutput;
    private ObjectInputStream socketInput;

    public ConnectionToClient(Socket socket, Server server) throws IOException {
        this.socket = socket;
        this.server = server;

        socketOutput = new ObjectOutputStream(this.socket.getOutputStream());
        socketInput = new ObjectInputStream(this.socket.getInputStream());

        thread = new Thread(this);
        thread.start();

    }


    public void run() {


        while (true) {
            try {
                if(socketInput == null){
                    System.out.println("socket nulo");
                }
                Object object = socketInput.readObject();

                if(object != null){
                    System.out.println("Server recebeu..." + object.toString());
                }
                if(object instanceof Message){
                    ((Message) object).setDate(new Date());
                }

                ArrayList<ConnectionToClient> clientList = server.getClientList();
                clientList.stream().parallel().forEach(connection -> {
                    try {
                        System.out.println("Enviando a cliente... ");
                        connection.write(object);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });


            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void write(Object obj) throws IOException {
        socketOutput.writeObject(obj);
        socketOutput.flush();
    }
}
