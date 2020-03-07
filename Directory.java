import java.net.*; 
import java.io.*; 
import java.util.*;

public class Directory extends Thread {
	private DatagramSocket socket;
	private boolean running;
	private byte[] buf = new byte[256];
	private ArrayList<HashMap<String, String>> db;

	public Directory(int port) throws Exception {
		socket = new DatagramSocket(port);
		this.db = new ArrayList<HashMap<String, String>>();
	}

	public ArrayList<HashMap<String, String>> getDb() {
		return (ArrayList<HashMap<String, String>>) this.db.clone();
	}

	public void listen() throws Exception {
		DatagramPacket packet = new DatagramPacket(buf, buf.length);
		socket.receive(packet);
		InetAddress address = packet.getAddress();
		int port = packet.getPort();
		DatagramPacket sendOut = new DatagramPacket(buf, buf.length, address, port);
		/*
		BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		String[] read = inFromClient.readLine().split(" ");
		//Removes user.
		if(read[0].equals("leaving")) {
			String username = "";
			for(int a = 1; a < read.length - 1; a++) {
				username = username + read[a] + " ";
			}
			username = username + read[read.length - 1];
			for(int a = 0; a < db.size(); a++) {
				HashMap<String, String> current = db.get(a);
				if(current.get("Username").equals(username)) {
					System.out.printf("Removed: %s from the system.\n", username);
					db.remove(a);
					return;
				}
			}
		}
		//Registers user.
		else if(read[0].equals("joining")) {
			String name = "";
			for(int a = 1; a < read.length - 2; a++) {
				name = name + read[a] + " ";
			}
			name = name + read[read.length - 2];
			String hostname = this.connectionSocket.getInetAddress().getHostName();
			String ip = this.connectionSocket.getInetAddress().getHostAddress();
			String port = read[read.length - 1];
			addUser(name, hostname, ip, port);
		}
		//Send list of users.
		else if(read[0].equals("fetch")) {
			ObjectOutputStream outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());
			outToClient.writeObject(db);
		}
		*/
	}

	public boolean addUser(String name, String hostname, String ip, String port) {
		Map<String, String> temp = new HashMap<String, String>();
		for(int a = 0; a < this.db.size(); a++) {
			HashMap<String, String> current = this.db.get(a);
			String cName = current.get("Username");
			if(cName.equals(name)) {
				return false;
			}
		}
		temp.put("Username", name);
		temp.put("Hostname", hostname);
		temp.put("IP", ip);
		temp.put("Port", port);
		db.add((HashMap<String, String>) temp);
		System.out.printf("Received: \"%s\" %s %s %s \n", name, hostname, ip, port);
		return true;
	}
	
	/*
	public void send(String message) throws Exception {
		DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
		outToClient.writeBytes(message + "\n");
	}
	*/

	public static void main(String[] args) throws Exception { 
		int port = Integer.parseInt(args[0]);
		Directory d = new Directory(port);
		while(true) {
			try {
				d.listen();
			}
			catch(Exception e) {

			}
		}
	}
}
