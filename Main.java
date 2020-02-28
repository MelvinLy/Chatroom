import java.net.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.io.*; 

public class Main {
	public static void main(String[] args) throws Exception {
		String hostname = args[0];
		String connecttoport = args[1];
		String serverport = args[2];
		String username = args[3];
		Server s = new Server(Integer.parseInt(serverport));
		s.start();
		while(true) {
			Scanner input = new Scanner(System.in);
			try {
				Client c = new Client(hostname, Integer.parseInt(connecttoport));
				String message = input.nextLine();
				c.send(/*username + ": " + */ message + "\n");
			}
			catch(Exception e) {
				
			}
		}
	}
}