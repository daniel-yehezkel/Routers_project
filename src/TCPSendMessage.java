import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class TCPSendMessage extends Thread {
    private final String _fromRouterName;
    private final String _toIP;
    private final int _toPort;
    private final String _message;

    public TCPSendMessage(String fromRouterName, String toIP, int toPort, String message) {
        _fromRouterName = fromRouterName;
        _toIP = toIP;
        _toPort = toPort;
        _message = message;
    }

    @Override
    public void run() {
        super.run();
        try {
            handleWrite();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleWrite() throws Exception {
        Socket socket = new Socket(_toIP, _toPort);
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        System.out.println(_fromRouterName + " Sending " + _message + " to server: " + socket.getInetAddress().toString()
                + ":" + socket.getPort());
        output.writeUTF(_message);

        // Receive Vector
        DataInputStream input = new DataInputStream(socket.getInputStream());
        String msg = input.readUTF();
        System.out.println("_TCP Received message: " + msg);

        output.close();
        socket.close();
    }
}
