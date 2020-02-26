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
	
	public void listenForSignUp() throws Exception {
		this.connectionSocket = welcomeSocket.accept();
		String clientSentence;
		BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		clientSentence = inFromClient.readLine();
		System.out.println(clientSentence);
	}
	
	public boolean addUser(String name, String hostname, String ip, int port) {
		Map<String, String> temp = new HashMap<String, String>();
		for(int a = 0; a < this.db.size(); a++) {
			HashMap<String, String> current = this.db.get(a);
			String cName = current.get(name);
			if(cName.equals(name)) {
				return false;
			}
		}
		temp.put("Username", name);
		temp.put("Hostname", hostname);
		temp.put("String", ip);
		temp.put("Port", Integer.toString(port));
		return true;
	}
	
	public void send(String message) throws Exception {
		DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
		outToClient.writeBytes(message + "\n");
	}
	
	public void run() {
		try {
			while(true) {
				listenForSignUp();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		
	}
}
