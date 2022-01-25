import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class myTCPServer {
    public myTCPServer(String serverName, int port) {
        ServerSocket serverSocket;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println(serverName + ": " + serverSocket.getInetAddress() + ":" + serverSocket.getLocalPort() +
                    " is up and waiting for connections on port " + port + "...");
            while (true) {
                socket = serverSocket.accept();
                new myTCPServerThread(serverName, socket).start();
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
