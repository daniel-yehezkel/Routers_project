import java.io.DataInputStream;
import java.net.Socket;
import java.util.Arrays;

public class myTCPServerThread extends Thread {
    private final String _serverName;
    private final Socket _socket;

    public myTCPServerThread(String serverName, Socket socket) {
        _serverName = serverName;
        _socket = socket;
    }

    @Override
    public void run() {
        super.run();
        try {
            handleRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleRead() throws Exception {
        DataInputStream input = new DataInputStream(_socket.getInputStream());

        String msg = new String(input.readAllBytes());
        System.out.println(_serverName + "_TCP Received message: " + msg);

        input.close();
        _socket.close();
    }
}
