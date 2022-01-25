import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class UDPSendMessage {
    private final String _fromRouterName;
    private final String _toIP;
    private final int _toPort;
    private final String _message;

    public UDPSendMessage(String fromRouterName, String toIP, int toPort, String message) {
        _fromRouterName = fromRouterName;
        _toIP = toIP;
        _toPort = toPort;
        _message = message;

        // Step 1:Create the socket object for
        // carrying the data.
        try {
            DatagramSocket ds = new DatagramSocket();

            InetAddress ip = InetAddress.getLocalHost();
            byte[] buf = null;
            buf = _message.getBytes();
            DatagramPacket DpSend =
                    new DatagramPacket(buf, buf.length, InetAddress.getByName(toIP), toPort);
            ds.send(DpSend);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
