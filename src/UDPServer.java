import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.io.File;

public class UDPServer extends Thread{
    String serverName;
    int port;
    Router r;
    public UDPServer(String serverName, int port, Router r) {
        this.serverName = serverName;
        this.port = port;
        this.r = r;

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
                if (data(receive).toString().equals("PRINT-ROUTING-TABLE")) {
                    try {
                        String filename="text_files\\" +"tableFilePrefix"+ r.routerName+".txt";
                        FileWriter myWriter = new FileWriter(filename,true);
                        String nexthop;
                        for (int i=1; i<r.routingTable.next.size(); i++) {
                                nexthop = r.routingTable.next.get(i).toString();
                                if (Integer.parseInt(nexthop)==r.routerName)
                                    nexthop="None";
                                myWriter.write(r.routingTable.distance.get(i)+";"+nexthop);
                            if (i != r.routingTable.next.size()-1)
                                myWriter.write("\n");
                        }
                        myWriter.close();
                        System.out.println("Successfully wrote to the file.");
                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }





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
