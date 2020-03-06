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
	
	public ArrayList<HashMap<String, String>> getDb() {
		return (ArrayList<HashMap<String, String>>) this.db.clone();
	}
	
	public void listenForSignUp() throws Exception {
		this.connectionSocket = welcomeSocket.accept();
		String[] clientSentence;
		BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		String read = inFromClient.readLine();
		if(read.equals("leaving")) {
			for(int a = 0; a < db.size(); a++) {
				HashMap<String, String> current = db.get(a);
			}
			return;
		}
		clientSentence = read.split(" ");
		String name = "";
		for(int a = 0; a < clientSentence.length - 2; a++) {
			name = name + clientSentence[a] + " ";
		}
		name = name + clientSentence[clientSentence.length - 2];
		String hostname = this.connectionSocket.getInetAddress().getHostName();
		String ip = this.connectionSocket.getInetAddress().getHostAddress();
		String port = clientSentence[clientSentence.length - 1];
		addUser(name, hostname, ip, port);
	}
	
	public boolean addUser(String name, String hostname, String ip, String port) {
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
		temp.put("Port", port);
		System.out.printf("Received: \"%s\" %s %s %s \n", name, hostname, ip, port);
		return true;
	}
	
	public void send(String message) throws Exception {
		DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
		outToClient.writeBytes(message + "\n");
	}
	
	public static void main(String[] args) throws Exception { 
		int port = Integer.parseInt(args[0]);
		Directory d = new Directory(port);
		while(true) {
			try {
				d.listenForSignUp();
			}
			catch(Exception e) {
				
			}
		}
	}
}
