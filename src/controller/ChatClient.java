package controller;

import model.Message;
import model.User;
import view.ChatView;
import web.Client;

import java.io.IOException;

/**
 * Implementação da classe Client que possui uma instância de ChatView,
 * fazendo a ponte entre a conexão e a interface.
 */
public class ChatClient extends Client {

    private ChatView chat;

    public ChatClient(String ip, int port, User user) throws IOException {
        super(ip, port, user);
        connect(this);
        getConnection().write(user);
    }

    public void setChat(ChatView chat) {
        this.chat = chat;
    }

    public void sendMessage(Message message) throws IOException {
        getConnection().write(message);
    }

    public void receiveMessage(Message message){
        chat.receiveMessage(message);
    }


}
