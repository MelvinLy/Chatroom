import java.net.*; 
import java.io.*; 

public class Server {
	private ServerSocket welcomeSocket;
	private Socket connectionSocket;
	
	public Server(int port) throws Exception {
		this.welcomeSocket = new ServerSocket(port);
		
	}
	
	public void listen() throws Exception {
		this.connectionSocket = welcomeSocket.accept();
		String clientSentence;
		BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		clientSentence = inFromClient.readLine();
		System.out.println(clientSentence);
	}
	
	public void send(String message) throws Exception {
		DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
		outToClient.writeBytes(message + "\n");
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