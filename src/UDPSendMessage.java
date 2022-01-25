import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class UDPSendMessage {
    public UDPSendMessage(String fromRouterName, String toIP, int toPort, String message) {
        try {
            DatagramSocket ds = new DatagramSocket();

            InetAddress ip = InetAddress.getLocalHost();
            byte[] buf = null;
            buf = message.getBytes();
            DatagramPacket DpSend =
                    new DatagramPacket(buf, buf.length, InetAddress.getByName(toIP), toPort);
            ds.send(DpSend);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
