import java.net.*; 
import java.io.*; 
import java.util.*;

public class Directory extends Thread {
	private ServerSocket welcomeSocket;
	private Socket connectionSocket;
	private ArrayList<HashMap<String, String>> db;

	public Directory(int port) throws Exception {
		this.welcomeSocket = new ServerSocket(port);
		this.db = new ArrayList<HashMap<String, String>>();
	}
	
	public void listen() throws Exception {
		this.connectionSocket = welcomeSocket.accept();
		String clientSentence;
		BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		//connectionSocket.getHostname???????????
		clientSentence = inFromClient.readLine();
		System.out.println(clientSentence);
	}
	
	public void send(String message) throws Exception {
		DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
		outToClient.writeBytes(message + "\n");
	}
	
	public void run() {
		try {
			while(true) {
				listen();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	public static void main(String[] args) throws Exception {
		Server server = new Server(56789);
		while(true) {
			server.listen();
		}
	}
	*/
}
