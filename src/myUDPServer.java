import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class myUDPServer {
    public myUDPServer(String serverName, int port) {
        DatagramSocket dataSocket;
        try {
            dataSocket = new DatagramSocket(port);
            byte[] receive = new byte[4096];
            while (true) {
                // create a DatgramPacket to receive the data.
                DatagramPacket DpReceive = new DatagramPacket(receive, receive.length);

                // revive the data in byte buffer.
                dataSocket.receive(DpReceive);

                System.out.println(serverName + "got message:" + data(receive));

                if (data(receive).toString().equals("message")) {
                    System.out.println("Client sent bye.....EXITING");
                    break;
                }
                receive = new byte[4096];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static StringBuilder data(byte[] a) {
        if (a == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0) {
            ret.append((char) a[i]);
            i++;
        }
        return ret;
    }
}
