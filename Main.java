import java.net.*; 
import java.io.*; 

public class Main {
	public static void main(String[] args) throws Exception {
		Server server = new Server(56789);
		while(true) {
			server.listen();
		}
	}
}