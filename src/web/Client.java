package web;

import controller.ChatClient;
import model.User;

import java.io.IOException;
import java.net.Socket;

/**
 * Classe "Cliente", age como um controlador para a conexão.
 */
public class Client {

    private ConnectionToServer server;
    private Socket socket;
    private User user;

    /**
     * Construtor que recebe IP e porta para fazer a conexão. Deve-se apontar para
     * a máquina servidor na porta que ele está usando.
     */
    public Client(String ip, int port, User user) throws IOException {
        socket = new Socket(ip, port);
        this.user = user;
    }

    public void connect(ChatClient client) throws IOException {
        server = new ConnectionToServer(socket, client);
    }

    public ConnectionToServer getConnection(){
        return server;
    }

}
