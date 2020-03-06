import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GUI {

    public GUI(){
        JFrame jFrame = new JFrame();

        JPanel south = new JPanel();
        south.add(createTextInput());
        south.add(createButtons());
        south.setBorder(BorderFactory.createLineBorder(Color.black));

        jFrame.add(createChatHistory(), BorderLayout.CENTER);
        jFrame.add(createUsers(), BorderLayout.EAST);
        jFrame.add(south, BorderLayout.SOUTH);

        jFrame.setSize(1000,800);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setTitle("Chatroom");
        jFrame.setVisible(true);
    }

    private JPanel createButtons() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
//        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        JButton sendButton = new JButton("Send");
        JButton clientButton = new JButton("Client");
        JButton serverButton = new JButton("Server");
        panel.add(sendButton);
        panel.add(Box.createRigidArea(new Dimension(0,5)));
        panel.add(clientButton);
        panel.add(Box.createRigidArea(new Dimension(0,5)));
        panel.add(serverButton);
        panel.add(Box.createRigidArea(new Dimension(0,5)));
        return panel;
    }

    private JPanel createTextInput(){
        JPanel panel = new JPanel();
//        panel.setBorder(BorderFactory.createLineBorder(Color.black));
//        panel.setLayout(BorderLayout);
        JTextArea jTextArea = new JTextArea(10,50);
        jTextArea.setLineWrap(true);
        jTextArea.setWrapStyleWord(true);
//        jTextArea.setColumns(panel.getMaximumSize().width);
        JScrollPane jScrollPane = new JScrollPane(jTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(jScrollPane);
        return panel;
    }

    private JPanel createUsers(){
        JPanel panel = new JPanel();
        JTextArea jTextArea = new JTextArea(1000,20);
        jTextArea.setLineWrap(true);
        jTextArea.setWrapStyleWord(true);
        jTextArea.setEditable(false);
//        jTextArea.setColumns(panel.getMaximumSize().width);
        jTextArea.setText("List of Users Online:");
        JScrollPane jScrollPane = new JScrollPane(jTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(jScrollPane);
        return panel;
    }

    private JPanel createChatHistory(){
        JPanel panel = new JPanel();
        JTextArea jTextArea = new JTextArea(100,60);
        jTextArea.setLineWrap(true);
        jTextArea.setWrapStyleWord(true);
        jTextArea.setEditable(false);
//        jTextArea.setMinimumSize(panel.getMaximumSize());
        jTextArea.setText("Chat History:");
        JScrollPane jScrollPane = new JScrollPane(jTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(jScrollPane);
//        jScrollPane.setPreferredSize(panel.getPreferredSize());
        return panel;
    }

    public static void main(String[] args) {
        new GUI();
    }
}
