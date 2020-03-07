import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.io.*; 

public class Main {
	public static void main(String[] args) throws Exception {
		HashMap<String, String> lol = new HashMap<String, String>();
		lol.put("Username", "big boi");
		lol.put("Hostname", "kubernetes.docker.internal");
		lol.put("IP", "127.0.0.1");
		lol.put("Port", "50262");
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
	    ObjectOutputStream out = new ObjectOutputStream(byteOut);
	    out.writeObject(lol);
	    
	    System.out.println(byteOut.toByteArray());
	    
	    ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
	    ObjectInputStream in = new ObjectInputStream(byteIn);
	    HashMap<String, String> data2 = (HashMap<String, String>) in.readObject();
	    
		System.out.println(data2);
	}
}