import java.net.*; 
import java.io.*; 

public class Server {
	private ServerSocket welcomeSocket;
	private Socket connectionSocket;
	
	public Server(int port) throws Exception {
		this.welcomeSocket = new ServerSocket(port);
	}
	
	public String listen() throws Exception {
		this.connectionSocket = welcomeSocket.accept();
		String clientSentence;
		BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		clientSentence = inFromClient.readLine();
		return clientSentence;
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