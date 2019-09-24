package web;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Set;

public class Server {

    private ServerSocket serverSocket;
    private ArrayList<ConnectionToClient> clientList;

    /**
     * O serverSocket é inicializado, ele tem como funcao receber uma requisicao e decide o que fazer a partir do tipo
     * da requisicao, e caso necessário, envia uma resposta para o cliente.
     *
     * Client list é inicializada, esta armazena os clientes dos quais estão conectados ao servidor.
     * @param port
     * @throws IOException
     */
    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        clientList = new ArrayList<>();
    }

    public void start() throws IOException {
        Socket socket = null;

        System.out.println("\nThreads iniciais:");
        Thread.getAllStackTraces().keySet().forEach(System.out::println);
        System.out.println("\n\n");


        //Limita o número de clientes conectados a 5, o valor poderia ser maior caso desejado.
        while(clientList.size() < 5){
            System.out.println("Esperando por cliente");

            //Instancia um socket para quando um cliente conectar com o servidor para gerenciar as requisições feitas pelo mesmo.
            socket = serverSocket.accept();

            System.out.println("Obteve cliente");
            //Cria um controlador para a conexão com o cliente e o adiciona na lista de clientes conectados.
            clientList.add(new ConnectionToClient(socket, this));

            System.out.println("\nThreads:");
            Thread.getAllStackTraces().keySet().forEach(System.out::println);
            System.out.println("\n\n");
        }

        System.out.println(clientList.size());
    }

    /**
     * Get de clientList
     * @return retorna a lista de clientes com conexões ativas.
     */
    public ArrayList<ConnectionToClient> getClientList() {
        return clientList;
    }




}
