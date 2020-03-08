import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.*; 

public class Client extends Thread {
	private Socket clientSocket;

	public Client(String name, int port) throws Exception {
		this.clientSocket = new Socket(name, port);
	}

	public void send(String message) throws Exception {
		DataOutputStream outServer = new DataOutputStream(clientSocket.getOutputStream());
		outServer.writeBytes(message + "\n");
	}

	public String receive() throws Exception {
		BufferedReader inServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		String sentenceIn = inServer.readLine();
		return sentenceIn;
	}

	public void close() throws Exception{
		clientSocket.close();
	}


	/*
	public static void main(String[] args) throws Exception {
		Client client = new Client("localhost", 56789);
		Scanner s = new Scanner(System.in);
		String message = s.nextLine();
		client.send(message);
		System.out.println(client.receive());
		client.close();
	}
	 */
}