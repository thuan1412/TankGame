package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class GameServerThread extends Thread {
	private GameServer server = null;
	private Socket socket = null;
	private int id = -1;
	private DataOutputStream outToClient;
	private BufferedReader inFromClient;
	public GameServerThread(GameServer server, Socket socket) {
		this.server = server;
		this.socket = socket;
		this.id = socket.getPort();
	}
	
	public void run() {
		System.out.println("Server thread " + this.id + " running");
		while (true) {
			try {
				inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String inputStr = inFromClient.readLine();
				if (inputStr == null) {
					continue;
				}
				server.handle(id, inputStr);
			} catch (IOException ioe) {
			}
		}
	}
	
	public void sendToClient(String msg) {
		try {
			this.outToClient = new DataOutputStream(socket.getOutputStream());
			this.outToClient.writeBytes(msg);
		} catch (IOException ioe) {
			System.out.println(this.id + " ERROR sending: " + ioe.getMessage());
		}
	}
	public void open() throws IOException {
//		this.inFromClient = new BufferedInputStream(socket.getInputStream());
	}

	public void close() throws IOException {
		if (socket != null)
			socket.close();
		if (this.inFromClient != null)
			this.inFromClient.close();
		if (outToClient != null)
			this.outToClient.close();
	}
	
	public long getId() {
		return this.id;
	}
}
