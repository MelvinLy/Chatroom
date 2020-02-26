import java.net.*; 
import java.io.*; 

public class Server {
	
	static final String NAME = "localhost";
	static final int PORT = 51954;
	
	public static void main(String[] args) throws Exception {
		String clientSentence;
		ServerSocket welcomeSocket = new ServerSocket(PORT);

		while(true) {
			Socket connectionSocket = welcomeSocket.accept();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			
			clientSentence = inFromClient.readLine();
			
			outToClient.writeBytes(clientSentence + "LOL \n");
			System.out.println(clientSentence);
		}
	}
}