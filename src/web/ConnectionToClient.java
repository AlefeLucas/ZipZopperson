package web;

import model.Message;
import model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Date;

public class ConnectionToClient implements Runnable {
    private Socket socket;
    private Server server;
    private User user;

    private Thread thread;

    private ObjectOutputStream socketOutput;
    private ObjectInputStream socketInput;

    /**
     * @param socket conexao existente com um cliente
     * @param server servidor ativo
     * @throws IOException
     */
    public ConnectionToClient(Socket socket, Server server) throws IOException {
        this.socket = socket;
        this.server = server;

        socketOutput = new ObjectOutputStream(this.socket.getOutputStream());
        socketInput = new ObjectInputStream(this.socket.getInputStream());

        //Cria e inicia uma thread para gerenciar a conexão do cliente com o servidor.
        thread = new Thread(this, "Novo cliente");
        thread.start();

    }


    public void run() {
        while (true) {
            try {

                //readObject le um objeto recebido do cliente.
                Object object = socketInput.readObject();

                if (object != null) {
                    System.out.println("Server recebeu..." + object.toString());
                }

                //verifica se o objeto recebido pelo cliente é uma mensagem, se caso for, define a data da mensagem
                //como o momento do qual foi recebida a mensagem pelo servidor.
                if (object instanceof Message) {
                    ((Message) object).setDate(new Date());

                    //Recebe do servidor quais clientes estão conectados, em seguida envia o objeto recebido para todos os
                    //clientes em paralelo.
                    ArrayList<ConnectionToClient> clientList = server.getClientList();
                    clientList.stream().parallel().forEach(connection -> {
                        try {
                            System.out.println("Enviando a cliente... " + (connection.getUser() != null ? connection.getUser().getName() : ""));
                            connection.write(object);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

                } else if (object instanceof User) {
                    this.user = (User) object;
                    this.thread.setName(user.getName());
                }


            } catch (SocketException ex) {
                System.out.println("Cliente fechado");
                break;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Envia um object para um cliente via socket.
     *
     * @param obj objeto do qual sera enviado.
     * @throws IOException
     */
    public void write(Object obj) throws IOException {
        socketOutput.writeObject(obj);
        socketOutput.flush();
    }

    public User getUser() {
        return user;
    }
}
