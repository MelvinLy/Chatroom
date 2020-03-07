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
	
	public void send(String message) {
		buf = message.getBytes();
		DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
	}
	
	public static void main(String args[]) throws SocketException, UnknownHostException {
		new UDPClient(56789, "localhost");
	}
}
