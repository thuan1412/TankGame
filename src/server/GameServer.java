package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer implements Runnable {
	private int MAX_CLIENTS_COUNT = 4;
	private GameServerThread clients[] = new GameServerThread[MAX_CLIENTS_COUNT];
	private ServerSocket server = null;
	private Thread thread = null;
	private int clientCount = 0;

	public GameServer(int port) {
		try {
			System.out.println("Listen of port " + port);
			server = new ServerSocket(port);
			System.out.println("Server started: " + server);
			this.start();
		} catch (IOException ioe) {
			System.out.println("Cannot bind to port....");
		}
	}
	
	@Override
	public void run() {
		while (thread != null) {
			System.out.println("Waiting for a client...");
			try {
				addClient(server.accept());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void addClient(Socket socket) {
		if (clientCount < MAX_CLIENTS_COUNT) {
			System.out.println("New client accepted: " + socket);
			clients[clientCount] = new GameServerThread(this, socket);
			try {
				clients[clientCount].open();
				clients[clientCount].start();
				clientCount++;
			} catch (IOException ioe) {
				System.out.println("Error on adding client");
			}
		}
	}
	
	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}
	
	private int findClient(int id) {
		for (int i = 0; i < clientCount; i++) {
			if (clients[i].getId() == id) {
				return i;
			}
		}
		return -1;
	}
	
	public synchronized void handle(int id, String input) {
		System.out.println(id + input);
		System.out.println(clientCount);
		for (int i = 0; i < clientCount; i++) {
			System.out.println("Send to client: " + id + input);
//			if (id != clients[i].getId()) {
				clients[i].sendToClient(id + input + '\n');
//			}
		}
	}
	
	public static void main(String[] args) {
		GameServer server = new GameServer(4321);
	}

}
