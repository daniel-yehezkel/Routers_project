import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer extends Thread {
    String serverName;
    int port;
    Router r;
    boolean serverWorks;

    public TCPServer(String serverName, int port, Router r) {
        this.serverName = serverName;
        this.port = port;
        this.r = r;
        this.serverWorks = true;
    }

    public void run() {
        super.run();
        ServerSocket serverSocket;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println(serverName + ": " + serverSocket.getInetAddress() + ":" + serverSocket.getLocalPort() +
                    " is up and waiting for connections on port " + port + "...");
            while (true) {
                socket = serverSocket.accept();
                new TCPServerThread(serverName, socket, this.r).start();

                if (!this.serverWorks){break;}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
