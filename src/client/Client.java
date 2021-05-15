package client;

import cn.qingyun.domain.*;

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
    public int clientId;
    private int NUMBER_CLIENTS = 3;

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
        System.out.println("RECEIVE MESSAGE: " + message);

        switch (messageType) {
            case "CONNECTION": {
                this.clientId = Integer.parseInt(params[1]);
                int clientCount = Integer.parseInt(params[2]);
                System.out.println(this.startPanel);
                while (this.startPanel == null) {
                    System.out.println("start panel null");
                }
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
                int clientCount = Integer.parseInt(params[2]);
//                EnemyTank enemyTank = new EnemyTank((this.mainPanel.enemyTanks.size() + 1) * 50, 50);
//                enemyTank.setEnemyTanks(this.mainPanel.enemyTanks);
//
//                enemyTank.setColor(0);
//                enemyTank.setDirect(1);
//                Thread thread = new Thread(enemyTank);
//                thread.start();
//                Shot shot = new Shot(enemyTank.getX() + 10, enemyTank.getY() + 30, enemyTank.direct);
//                enemyTank.shots.add(shot);
//                Thread threadShot = new Thread(shot);
//                threadShot.start();

//                this.mainPanel.enemyTanks.add(enemyTank);
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
                this.startPanel.updateUserCount(clientCount);
                break;
            }
            case "ENEMY_MOVE": {
                int senderId = Integer.parseInt(params[2]);
                String direction = params[1];
                this.mainPanel.onEnemyMove(senderId, direction);
                break;
            }
            case "ENEMY_SHOT":
                int senderId = Integer.parseInt(params[1]);
                this.mainPanel.onEnemyShot(senderId);
                break;
            default:
                System.out.println("DEFAULT EVENT " + message);
                break;
        }
    }

    @Override
    public void run() {

    }
}
