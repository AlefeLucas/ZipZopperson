package main;

import controller.ChatClient;
import model.User;
import view.ChatView;

import javax.swing.*;

public class ClientApp {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            try {
                String name = JOptionPane.showInputDialog(null, "Informe seu nome.", "Bem-vindo", JOptionPane.QUESTION_MESSAGE);
                User user = new User(name);

                ChatView chatView = new ChatView(user);


            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
