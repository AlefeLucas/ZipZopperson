package web;

import controller.ChatClient;
import model.Message;
import model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Controla a conexão com o servidor, a comunicação de entrada e saída. Os
 * dados recebidos são tratados em um thread exclusiva.
 */
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

        //Inicia uma thread para esta conexão
        this.thread = new Thread(this);
        thread.start();


    }

    /**
     * Faz as leituras dos objetos enviados pelo servidor. Se for uma Mensagem,
     * é enviada para interface para ser exibida na tela.
     */
    @Override
    public void run() {
        while (true) {
            try {
                Object object = socketInput.readObject();

                if(object instanceof Message){
                    Message message = (Message) object;

                    client.receiveMessage(message);
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Envia o objeto passado para o servidor conectado no socket.
     */
    public void write(Object obj) throws IOException {
        socketOutput.writeObject(obj);
        socketOutput.flush();
        System.out.println("Escrito... " + obj.toString());
    }
}
