package controller;

import model.Message;
import model.User;
import view.ChatView;
import web.Client;

import java.io.IOException;

public class ChatClient extends Client {

    private ChatView chat;

    public ChatClient(String ip, int port) throws IOException {
        super(ip, port);
        connect(this);
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

    public void receiveNewUser(User user){

    }
}