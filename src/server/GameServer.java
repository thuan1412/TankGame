package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer implements Runnable {
	private int MAX_CLIENTS_COUNT = 4;
	private GameServerThread clients[] = new GameServerThread[MAX_CLIENTS_COUNT];
	private int clientCount = 0;
	private ServerSocket server = null;
	private Thread thread = null;

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
				this.sendMessageToAClient(clients[clientCount - 1].getId(), "CONNECTION");
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

	public void sendMessageToAClient(long id, String input) {
		for (int i = 0; i < clientCount; i++) {
			if (id == clients[i].getId()) {
				clients[i].sendToClient(input + " " + id + " " + this.clientCount + " " + this.getClientIds() + '\n');
			}
		}
	}

	public synchronized void handle(int id, String input) throws IOException, InterruptedException {
		System.out.println(id + input);
		System.out.println(clientCount);
		if  (input.equals("DISCONNECT")) {
			// remove this client from client array;
			GameServerThread tempClients[] = new GameServerThread[MAX_CLIENTS_COUNT];
			int clientCountTemp = 0;
			for (int j = 0; j < clientCount; j++) {
				if (clients[j].getId() != id) {
					tempClients[clientCountTemp++] = clients[j];
				} else {
					clients[j].close();
				}
			}
			clientCount--;
			clients = tempClients;
			for (int k = 0; k < clientCount; k++) {
				clients[k].sendToClient(input + " " + id + '\n');
			}
			return;
		}
		if  (input.equals("END")) {
			Thread.sleep(3000);

			for (int k = 0; k < clientCount; k++) {
				clients[k].sendToClient(input + " " + id + '\n');
			}
		}
		for (int i = 0; i < clientCount; i++) {
			System.out.println("Send to client: " + clients[i].getId() + " " + input);
			if (id != clients[i].getId()) {
				switch (input) {
					case "NEW_PLAYER": {
						clients[i].sendToClient(input + " " + id + " " + clientCount + " " + this.getClientIds() + '\n');
						break;
					}
					default:
						clients[i].sendToClient(input + " " + id + '\n');
				}
			}
		}
	}

	public String getClientIds() {
		String ids = "";
		for (int i = 0; i < clientCount - 1; i++) {
			ids += this.clients[i].getId() + "-";
		}
		ids += this.clients[clientCount - 1].getId();
		return ids;
	}

	public static void main(String[] args) {
		GameServer server = new GameServer(4321);
	}

}
