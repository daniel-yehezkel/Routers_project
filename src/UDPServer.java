import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer extends Thread{
    String serverName;
    int port;
    public UDPServer(String serverName, int port) {
        this.serverName = serverName;
        this.port = port;
    }

    public void run(){
        super.run();
        DatagramSocket dataSocket;

        try {
            dataSocket = new DatagramSocket(port);
            byte[] receive = new byte[4096];
            while (true) {
                // create a DatgramPacket to receive the data.
                DatagramPacket DpReceive = new DatagramPacket(receive, receive.length);

                // revive the data in byte buffer.
                dataSocket.receive(DpReceive);

                System.out.println(serverName + "_UDP got message:" + data(receive));

                String message = data(receive).toString();
                String[] messageParts = message.split(";");
                if (messageParts.length == 6) {
                    System.out.println("Client send message of type 6");

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
