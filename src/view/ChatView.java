package view;

import controller.ChatClient;
import model.Message;
import model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Date;

/**
 * Classe para a interface visual, janela de chat com campo de texto e botão de enviar.
 */
public class ChatView extends JFrame {
    private JPanel rootPanel;
    private JTextArea outputTextArea;
    private JTextArea inputTextArea;
    private JButton sendButton;
    private JScrollPane scrollPane;
    private JLabel nameLabel;
    private User user;
    private ChatClient client;

    public static final int WIDTH = 300;
    public static final int HEIGHT = 500;


    public ChatView(User user) throws IOException {
        this.setSize(WIDTH, HEIGHT);
        this.setContentPane(rootPanel);
        this.setLocationRelativeTo(null);
        this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.user = user;

        //Inicia a conexão do cliente com o servidor no IP e porta especificado
        client = new ChatClient("127.0.0.1", 4321, user);
        client.setChat(this);

        nameLabel.setText(user.getName());

        sendButton.addActionListener((ActionEvent e) -> {

            sendMessage();
        });

        inputTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    sendMessage();
                }
            }
        });

        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void sendMessage() {
        String text = inputTextArea.getText();
        Message message = new Message(user, text, new Date());
        System.out.println("Enviando... " + message.toString());
        inputTextArea.setText("");
        try {
            client.sendMessage(message);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro ao enviar mensagem", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void receiveMessage(Message message){
        outputTextArea.setText(outputTextArea.getText() + "\n\n" + message.toString());
    }
}
