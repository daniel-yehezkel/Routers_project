import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer extends Thread{
    String serverName;
    int port;

    public TCPServer(String serverName, int port) {
        this.serverName = serverName;
        this.port = port;
    }
    public void run(){
        super.run();
        ServerSocket serverSocket;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println(serverName + ": " + serverSocket.getInetAddress() + ":" + serverSocket.getLocalPort() +
                    " is up and waiting for connections on port " + port + "...");
            while (true) {
                socket = serverSocket.accept();
                new TCPServerThread(serverName, socket).start();
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
