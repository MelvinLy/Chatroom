import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GUI {

	String hostname;
	String directoryport;
	String serverport;
	String username;
	Client c;
	Server s;

	public GUI(){
		login();
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

	private void login() {
		JFrame jFrame = new JFrame();
		JPanel panel = new JPanel();

		/*
    	JTextArea user = new JTextArea("Enter Username");
    	JTextArea cPort = new JTextArea("Enter Your Server Port");
    	JTextArea dPort = new JTextArea("Enter the Directory Server Port");
    	JTextArea host = new JTextArea("Enter Server Host Name");
		 */
		JTextArea user = new JTextArea("big boi");
		JTextArea cPort = new JTextArea("56788");
		JTextArea dPort = new JTextArea("56789");
		JTextArea host = new JTextArea("10.0.0.199");
		user.setPreferredSize(new Dimension(450, 20));
		cPort.setPreferredSize(new Dimension(450, 20));
		dPort.setPreferredSize(new Dimension(450, 20));
		host.setPreferredSize(new Dimension(450, 20));    	 

		JButton send = new JButton("Send to Directory Server");

		class SendListener implements MouseListener {

			public void mouseClicked(MouseEvent e) {

				hostname = host.getText();
				directoryport = dPort.getText();
				serverport = cPort.getText();
				username = user.getText();
				try {
					s = new Server(Integer.parseInt(serverport));
				} 
				catch (Exception e1) {
					e1.printStackTrace();
					System.exit(1);
				}
				try {
					Client c = new Client(hostname, Integer.parseInt(directoryport));
					c.send("joining" + " " + username + " " + serverport);
				}
				catch(Exception e1) {
					e1.printStackTrace();
					System.exit(1);
				}

				panel.remove(send);
				JFrame jFrame = new JFrame();
				jFrame.setResizable(false);

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
				jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
					public void windowClosing(java.awt.event.WindowEvent windowEvent) {
						try {
							Client c = new Client(hostname, Integer.parseInt(directoryport));
							c.send("leaving " + username);
						}
						catch(Exception e1) {
							e1.printStackTrace();
							System.exit(1);
						}
					}
				});
			}

			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}

		}

		send.addMouseListener(new SendListener());

		panel.add(user);
		panel.add(cPort);
		panel.add(dPort);
		panel.add(host);
		panel.add(send);
		jFrame.add(panel);

		jFrame.setResizable(false);
		jFrame.setSize(500, 250);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setTitle("Login");
		jFrame.setVisible(true);
		jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				try {
					Client c = new Client(hostname, Integer.parseInt(directoryport));
					c.send("leaving " + username);
				}
				catch(Exception e1) {
					
				}
			}
		});
	}

	public static void main(String[] args) {
		new GUI();
	}

}
