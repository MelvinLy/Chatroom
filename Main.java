import java.net.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.io.*; 

public class Main {
	public static void main(String[] args) throws Exception {
		Server s = new Server(Integer.parseInt(args[2]));
		s.start();
		while(true) {
			try {
				Client c = new Client(args[0], Integer.parseInt(args[1]));
				Scanner input = new Scanner(System.in);
				String message = input.nextLine();
				c.send(args[3] + ": " + message + "\n");
			}
			catch(Exception e) {
				
			}
		}
	}
}