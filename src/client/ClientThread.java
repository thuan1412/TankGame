package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread extends Thread {
	private Socket socket = null;
	private Client client = null;
	private BufferedReader inFromServer = null;
	
	public ClientThread(Client client ,Socket socket) {
		this.client = client;
		this.socket = socket;
		open();
		start();
	}
	
	public void open() {
		try {
			inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
//			client.stop();
			e.printStackTrace();
		}
	}
	
	public void run() {
		while (true) {
			try {
				String inputStr = inFromServer.readLine();
				if (inputStr == null) continue;
				System.out.println("Message: " + inputStr);
				client.handle(inputStr);
			} catch (IOException ioe) {
				System.out.println("Listening error: " + ioe.getMessage());
//				client.stop();
			}
		}
	}
}
