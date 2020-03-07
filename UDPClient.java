import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPClient extends Thread {
	private DatagramSocket udp;
	private InetAddress address;
	private int port;
	private byte[] buf;
	
	public UDPClient(int port, String hostname) throws SocketException, UnknownHostException {
		udp = new DatagramSocket();
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
	
	public static void main(String args[]) throws IOException {
		UDPClient lol = new UDPClient(56789, "localhost");
		lol.send("joining big boi 56788");
		System.out.println(lol.receive());
	}
}
