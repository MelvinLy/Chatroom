import java.net.*; 
import java.io.*; 
import java.util.*;

public class Directory {
	private DatagramSocket socket;
	private boolean running;
	private byte[] buf = new byte[576];
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
		byte[] received = packet.getData();
		String string = new String(received, 0, received.length);
		String[] read = string.split(" ");
		//Removes user.
		if(read[0].equals("leaving")) {
			String username = "";
			for(int a = 1; a < read.length - 2; a++) {
				username = username + read[a] + " ";
			}
			username = username + read[read.length - 2];
			for(int a = 0; a < db.size(); a++) {
				HashMap<String, String> current = db.get(a);
				if(current.get("Username").equals(username)) {
					System.out.printf("Removed: \"%s\" from the system.\n", username);
					db.remove(a);
					return;
				}
			}
		}
		//Registers user.
		else if(read[0].equals("joining")) {
			String name = "";
			for(int a = 1; a < read.length - 3; a++) {
				name = name + read[a] + " ";
			}
			name = name + read[read.length - 3];
			String hostname = address.getHostName();
			String ip = address.getHostAddress();
			addUser(name, hostname, ip, read[read.length - 2] + "");
		}
		//Send list of users.
		else if(read[0].equals("fetch")) {
			for(int a = 0; a < db.size(); a++) {
				HashMap<String, String> currentMap = db.get(a);
				ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			    ObjectOutputStream out = new ObjectOutputStream(byteOut);
			    out.writeObject(currentMap);
			    DatagramPacket toSend = new DatagramPacket(byteOut.toByteArray(), byteOut.size(), address, port);
			    socket.send(toSend);
			}
			DatagramPacket toSend = new DatagramPacket("end ".getBytes(), 4, address, port);
			socket.send(toSend);
		}
		buf = new byte[576];
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
