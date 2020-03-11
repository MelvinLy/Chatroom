import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

public class UDPClient extends Thread {
	private DatagramSocket udp;
	private InetAddress address;
	private int port;
	private byte[] buf;
	
	public UDPClient(int port, String hostname) throws SocketException, UnknownHostException {
		udp = new DatagramSocket();
		udp.setSoTimeout(5000);
		this.port = port;
		address = InetAddress.getByName(hostname);
	}
	
	public UDPClient(int port, byte[] outAddress) throws SocketException, UnknownHostException {
		udp = new DatagramSocket();
		this.port = port;
		address = InetAddress.getByAddress(outAddress);
	}
	
	public void send(String message) throws IOException {
		byte[] out = message.getBytes();
		DatagramPacket packet = new DatagramPacket(out, out.length, address, port);
		udp.send(packet);
	}
	
	public String receive() throws IOException {
		byte[] receive = new byte[576];
		DatagramPacket in = new DatagramPacket(receive, receive.length);
		udp.receive(in);
		byte[] received = in.getData();
		String toReturn = new String(received, 0, received.length);
		return toReturn;
	}
	
	public ArrayList<HashMap<String, String>> fetchDb() throws IOException, ClassNotFoundException {
		this.send("fetch ");
		ArrayList<HashMap<String, String>> toReturn = new ArrayList<HashMap<String, String>>();
		while(true) {
			byte[] receive = new byte[576];
			DatagramPacket in = new DatagramPacket(receive, receive.length);
			udp.receive(in);
			String string = new String(receive, 0, receive.length);
			String[] read = string.split(" ");
			if(read[0].equals("end")) {
				break;
			}
			ByteArrayInputStream byteIn = new ByteArrayInputStream(receive);
		    ObjectInputStream ois = new ObjectInputStream(byteIn);
		    HashMap<String, String> map = (HashMap<String, String>) ois.readObject();
		    toReturn.add(map);
		}
		return toReturn;
	}
	/*
	public static void main(String args[]) throws IOException, InterruptedException, ClassNotFoundException {
		UDPClient lol = new UDPClient(56789, "localhost");
		lol.send("joining big boi 56788");
		lol = new UDPClient(56789, "localhost");
		System.out.println(lol.fetchDb());
	}
	*/
}
