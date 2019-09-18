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

public class ChatView extends JFrame {
    private JPanel rootPanel;
    private JTextArea outputTextArea;
    private JTextArea inputTextArea;
    private JButton sendButton;
    private JScrollPane scrollPane;
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

        client = new ChatClient("127.0.0.1", 4321);
        client.setChat(this);

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
