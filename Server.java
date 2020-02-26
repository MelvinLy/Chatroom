import java.net.*; 
import java.io.*; 

public class Server {
	
	private ServerSocket welcomeSocket;
	
	public Server(int port) throws Exception {
		welcomeSocket = new ServerSocket(port);
	}
	
	public void run() throws Exception {
		String clientSentence;
		Socket connectionSocket = welcomeSocket.accept();
		BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
		clientSentence = inFromClient.readLine();
		outToClient.writeBytes(clientSentence +  "\n");
		System.out.println(clientSentence);
	}

}