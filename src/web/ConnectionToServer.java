package web;

import controller.ChatClient;
import model.Message;
import model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionToServer implements Runnable {

    private final ChatClient client;
    private Socket socket;

    private ObjectInputStream socketInput;
    private ObjectOutputStream socketOutput;
    private Thread thread;

    public ConnectionToServer(Socket socket, ChatClient client) throws IOException {
        this.socket = socket;
        this.client = client;

        socketInput = new ObjectInputStream(this.socket.getInputStream());
        socketOutput = new ObjectOutputStream(this.socket.getOutputStream());

        this.thread = new Thread(this);
        thread.start();


    }

    @Override
    public void run() {


        while (true) {
            try {
                Object object = socketInput.readObject();

                if(object instanceof Message){
                    Message message = (Message) object;

                    client.receiveMessage(message);
                } else if(object instanceof User){
                    User user = (User) object;

                    client.receiveNewUser(user);
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void write(Object obj) throws IOException {
        socketOutput.writeObject(obj);
        socketOutput.flush();
        System.out.println("Escrito... " + obj.toString());
    }
}
