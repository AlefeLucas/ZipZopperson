package main;

import controller.ChatClient;
import model.User;
import view.ChatView;

import javax.swing.*;

public class ClientApp {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            try {
                //Pede o nome do usuário que deseja conectar para identificação
                String name = JOptionPane.showInputDialog(null, "Informe seu nome.", "Bem-vindo", JOptionPane.QUESTION_MESSAGE);
                User user = new User(name);
                //Cria a janela do chat com este usuário, o nome é enviado ao servidor junto com cada mensagem.
                ChatView chatView = new ChatView(user);


            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
