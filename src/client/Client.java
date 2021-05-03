package client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable {
	private Socket socket = null;
	private Thread thread = null;
	public DataOutputStream outToServer = null;
	private ClientThread clientThread = null;

	public Client(String servername, int port) {
		try {
			socket = new Socket(servername, port);
			System.out.println("Conneceted: " + socket);
			start();
		} catch (UnknownHostException e) {
			System.out.println("Unknown host exception");
		} catch (IOException ioe) {
			System.out.println("Unexpected exception: " + ioe.getMessage());
		}
	}

	public void start() throws IOException {
		outToServer = new DataOutputStream(socket.getOutputStream());
		if (this.thread == null) {
			clientThread = new ClientThread(this, this.socket);
			thread = new Thread(this);
			thread.start();
					
		}
	}

	@Override
	public void run() {

	}
}
