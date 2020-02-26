import java.net.*;
import java.util.Scanner;
import java.io.*; 

public class Client {
	
	static final String NAME = "localhost";
	static final int PORT = 56789;
	
	public static void main(String[] args) throws Exception {
		
		Scanner s = new Scanner(System.in);
		BufferedReader inUser = new BufferedReader(new InputStreamReader(System.in));

		Socket clientSocket = new Socket(NAME, PORT);
		
		DataOutputStream outServer = new DataOutputStream(clientSocket.getOutputStream());
		
		BufferedReader inServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		String sentenceOut = s.nextLine();
		
		outServer.writeBytes(sentenceOut + "\n");
		String sentenceIn = inServer.readLine();
		System.out.println(sentenceIn);
		clientSocket.close();
	}
}