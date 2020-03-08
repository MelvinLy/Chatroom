import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

public class GUI {

	String hostname;
	String directoryport;
	String serverport;
	String username;
	ArrayList<HashMap<String, String>> db;
	JTextArea online;
	JTextArea history;
	JTextArea messageInput;
	Client c;
	Client c2;
	Server s;
	UDPClient udp;
	int semaphore = 1;

	public GUI(){
		login();
	}

	public void startChatRoom() throws NumberFormatException, Exception {
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
					udp.send("leaving " + username + " ");
				}
				catch(Exception e1) {
					e1.printStackTrace();
					System.exit(1);
				}
			}
		});
		//Fetch thread.
		/*
		new Thread() {
			public void run() {
				while(true) {
					try {
						if(semaphore == 1) {
							//CRITICAL SECTION
							semaphore = 0;
							c2 = new Client(hostname, Integer.parseInt(directoryport));
							db = c2.fetchOnline();
							String replace = "List of Users Online:";
							for(int a = 0; a < db.size(); a++) {
								HashMap<String, String> current = db.get(a);
								replace = replace + "\n" + current.get("Username");
							}
							online.setText(replace);
							semaphore = 1;
						}
						Thread.sleep(5000);
					} catch (Exception ex) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}.start();
		*/
		new Thread() {
			public void run() {
				try {
					s = new Server(Integer.parseInt(serverport));
				} 
				catch (Exception e1) {
					e1.printStackTrace();
					System.exit(1);
				}
				while(true) {
					try {
						String incoming = s.listen();
						history.append("\n" + incoming);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	private JPanel createButtons() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		//        panel.setBorder(BorderFactory.createLineBorder(Color.black));
		class SendListener implements MouseListener {
			public void mouseClicked(MouseEvent e) {
				while(true) {
					String text = messageInput.getText();
					if(text.equals("") || text == null) {
						return;
					}
					if(semaphore == 1) {
						//CRITICAL SECTION
						semaphore = 0;
						messageInput.setText(null);
						text = username + ": " + text;
						for(int a = 0; a < db.size(); a++) {
							HashMap<String, String> current = db.get(a);
							try {
								Client out = new Client(current.get("IP"), Integer.parseInt(current.get("Port")));
								out.send(text);
							}
							catch (Exception e1) {

							}
						}
						semaphore = 1;
						return;
					}
				}
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
		}
		JButton sendButton = new JButton("Send");
		JButton clientButton = new JButton("Client");
		JButton serverButton = new JButton("Server");

		sendButton.addMouseListener(new SendListener());

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
		this.messageInput = jTextArea;
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
		online = jTextArea;
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
		this.history = jTextArea;
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
					udp = new UDPClient(Integer.parseInt(directoryport), hostname);
					db = udp.fetchDb();
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				for(int a = 0; a < db.size(); a++) {
					HashMap<String, String> current = db.get(a);
					if(current.get("Username").equals(username)) {
						System.exit(0);
					}
				}
				
				try {
					if(username.length() > 15) {
						System.exit(1);
					}

					udp.send("joining" + " " + username + " " + serverport + " ");
				}
				catch(Exception e1) {
					e1.printStackTrace();
					System.exit(1);
				}

				panel.remove(send);
				try {
					startChatRoom();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
