package client;

import cn.qingyun.domain.*;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable {
    private Socket socket = null;
    private Thread thread = null;
    public DataOutputStream outToServer = null;
    private ClientThread clientThread = null;
    public MainPanel mainPanel = null;
    public StartPanel startPanel = null;
    public MainFrame mainFrame = null;
    public int clientCount;
    public int clientId;
    private int NUMBER_CLIENTS = 4;

    public Client(String servername, int port) {
        try {
            socket = new Socket(servername, port);
            System.out.println("Conneceted: " + socket);
            start();
        } catch (UnknownHostException e) {
            System.out.println("Unknown host exception");
            System.exit(1);
        } catch (IOException ioe) {
            System.out.println("Unexpected exception: " + ioe.getMessage());
            System.exit(1);
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

    public void sendMessage(String message) {
        try {
            this.outToServer.writeBytes(message + '\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handle(String message) {
        if (this.mainPanel != null && this.mainPanel.enemyTanks.size() == NUMBER_CLIENTS) {
//			this.mainPanel.flag = 1;
        }
        String[] params = message.split(" ");
        String messageType = params[0];
//        int senderId = Integer.parseInt(params[2]);

        switch (messageType) {
            case "CONNECTION": {
                this.clientId = Integer.parseInt(params[1]);
                clientCount = Integer.parseInt(params[2]);

                while (this.startPanel == null) {}
                for (int i = 0; i < 5000; i++) {int a = 100*100;}
                this.startPanel.updateUserCount(clientCount);

                // change
                if (clientCount >= NUMBER_CLIENTS) {
                    int[] clientIds = new int[4];
                    String[] clientIdsStr = params[3].split("-");
                    for (int i = 0 ; i < NUMBER_CLIENTS; i++) {
                        clientIds[i] = Integer.parseInt(clientIdsStr[i]);
                    }
                    this.mainFrame.clientIds = clientIds;
                    this.mainFrame.enterGame();
                }
                break;
            }
            case "NEW_PLAYER": {
                System.out.println("New player handler");
                clientCount = Integer.parseInt(params[2]);
                if (clientCount >= NUMBER_CLIENTS) {
                    int[] clientIds = new int[4];
                    String[] clientIdsStr = params[3].split("-");
                    for (int i = 0 ; i < NUMBER_CLIENTS; i++) {
                        clientIds[i] = Integer.parseInt(clientIdsStr[i]);
                    }
                    System.out.println("Enter game");
                    this.mainFrame.clientIds = clientIds;
                    this.mainFrame.enterGame();
                }

                while (this.startPanel == null) {}
                for (int i = 0; i < 5000; i++) {int a = 100*100;}
                this.startPanel.updateUserCount(clientCount);
                break;
            }
            case "ENEMY_MOVE": {
                int senderId = Integer.parseInt(params[2]);
                String direction = params[1];
                this.mainPanel.onTankMove(senderId, direction);
                break;
            }
            case "ENEMY_SHOT": {
                int senderId = Integer.parseInt(params[1]);
                this.mainPanel.onTankShot(senderId);
                break;
            }
            case "DISCONNECT": {
                int senderId = Integer.parseInt(params[1]);
                if (this.mainPanel == null) {
                    System.out.println("client count 1: " + clientCount);
                    this.startPanel.updateUserCount(--clientCount);
                } else {
                    this.mainPanel.userDisconnect(senderId);
                }
                break;
            }
            case "END": {
                System.out.println(params[1] + this.clientId);
                if (params[1].contains(Integer.toString(this.clientId))) {
                    JOptionPane.showMessageDialog(this.mainPanel,
                            "You lose");
                } else {
                    JOptionPane.showMessageDialog(this.mainPanel,
                            "You won.");
                }

                this.mainFrame.dispatchEvent(new WindowEvent(this.mainFrame, WindowEvent.WINDOW_CLOSING));
                break;
            }
            default:
                System.out.println("DEFAULT EVENT " + message);
                break;
        }
    }

    @Override
    public void run() {

    }
}
