import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class myTCPSendMessage extends Thread {
    private final String _fromRouterName;
    private final String _toIP;
    private final int _toPort;
    private final int _message;

    public myTCPSendMessage(String fromRouterName, String toIP, int toPort, int message) {
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
//        PrintWriter output = new PrintWriter(socket.getOutputStream());

        System.out.println(_fromRouterName + " Sending " + _message + " to server: " + socket.getInetAddress().toString()
                + ":" + socket.getPort());
//        output.println(_message);
        output.writeInt(_message);

        output.close();
        socket.close();
    }
}
