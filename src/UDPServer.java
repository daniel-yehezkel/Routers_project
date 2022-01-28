import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class UDPServer extends Thread {
    String serverName;
    int port;
    Router r;
    boolean serverWorks;
    TCPServer routerTCPServer;

    public UDPServer(String serverName, int port, Router r, TCPServer routerTCPServer) {
        this.serverName = serverName;
        this.port = port;
        this.r = r;
        this.serverWorks = true;
        this.routerTCPServer = routerTCPServer;
    }

     public void run() {
        super.run();
        DatagramSocket dataSocket;

        try {
            dataSocket = new DatagramSocket(port);
            byte[] receive = new byte[4096];
            while (this.serverWorks) {
                // create a DatgramPacket to receive the data.
                DatagramPacket DpReceive = new DatagramPacket(receive, receive.length);

                // revive the data in byte buffer.
                dataSocket.receive(DpReceive);

                //System.out.println(serverName + "_UDP got message:" + data(receive));

                String message = data(receive).toString();
                String[] messageParts = message.split(";");
                if (messageParts.length == 6) {
//                    System.out.println("Client send message of type 6");
                    try {
                        String filename = r.tableFilePrefix + r.routerName + ".txt";
                        FileWriter myWriter = new FileWriter(filename, true);
                        myWriter.write(message);
                        myWriter.write(System.lineSeparator());
                        myWriter.close();
                        // if belong to me or hop out
                        if (Integer.parseInt((messageParts)[2]) == 0 || Integer.parseInt((messageParts)[1]) == r.routerName) {
                            System.out.println("last hop i am " + r.routerName);
//                            r.sendMessageToNeighborUDP(messageParts[4], Integer.parseInt(messageParts[5]), messageParts[3]);
//                            r.sendMessageToNeighborUDP("127.0.0.1", 30159, "diediedie" );
                        }
                        //navigate message
                        else {
                            String next_hop_router = r.routingTable.next.get(Integer.parseInt(messageParts[1])).toString();
                            int next_hop_port = r.neighborsProperties.get(Integer.parseInt(next_hop_router)).neighborPortUDP;
                            String next_hop_ip = r.neighborsProperties.get(Integer.parseInt(next_hop_router)).neighborIP;
                            String m = messageParts[0] + ";" + messageParts[1] + ";" + (Integer.parseInt((messageParts)[2]) - 1) + ";" + messageParts[3] + ";" + messageParts[4] + ";" + messageParts[5];
                            r.sendMessageToNeighborUDP(next_hop_ip, next_hop_port, m);
                            System.out.println("from "+ r.routerName+" to next hop -> sent to "+ next_hop_router);
                        }
                    } catch (IOException e) {
                        //System.out.println("An error occurred.");
                        e.printStackTrace();
                    }

                }
                if (message.equals("UPDATE-ROUTING-TABLE")) {
                    String neighborDistanceVectorStr;
                    for (Map.Entry<Integer, Neighbor> set : r.neighborsProperties.entrySet()) {
                        String ip = set.getValue().neighborIP;
                        int sendToPort = set.getValue().neighborPortTCP;
                        neighborDistanceVectorStr = r.sendMessageToNeighborTCP(ip, sendToPort, this.r.currentRound.toString());
                        String replace = neighborDistanceVectorStr.replace("[", "").replace("]", "").replace(" ", "");
                        ArrayList<String> myList = new ArrayList<>(Arrays.asList(replace.split(",")));
                        ArrayList<Integer> neighborDistanceVector = new ArrayList<>();
                        for (int i = 0; i < myList.size(); i++)
                            neighborDistanceVector.add(i, Integer.valueOf(myList.get(i)));

                        r.neighborsProperties.get(set.getKey()).neighborDistanceVector = neighborDistanceVector;
                    }

                    for (int index = 1; index <= this.r.numNetRouters; index++) {
                        if (index != this.r.routerName) {
                            int minVal = Integer.MAX_VALUE;
                            int zKey = -1;
                            ArrayList<Integer> zDistance = null;
                            for (Map.Entry<Integer, Neighbor> entry : r.neighborsProperties.entrySet()) {
                                if (minVal > entry.getValue().neighborDistanceVector.get(index) + entry.getValue().oldWeight) {
                                    minVal = entry.getValue().neighborDistanceVector.get(index) + entry.getValue().oldWeight;
                                    zKey = entry.getKey();
                                    zDistance = entry.getValue().neighborDistanceVector;
                                }
                            }
                            this.r.routingTable.next.set(index, zKey);
                            this.r.routingTable.distance.set(index, zDistance.get(index) + this.r.routingTable.distance.get(zKey));
                        }
                    }
                    this.r.currentRound++;
                    this.r.updateRoutingTable(this.r.currentRound);
                }


                if (data(receive).toString().equals("PRINT-ROUTING-TABLE")) {
                    try {
                        String filename = r.tableFilePrefix + r.routerName + ".txt";
                        FileWriter myWriter = new FileWriter(filename, true);
                        String nexthop;
                        for (int i = 1; i < r.routingTable.next.size(); i++) {
                            if(r.routerName==i)
                                nexthop = "None";
                            else
                            nexthop = r.routingTable.next.get(i).toString();
//                            if (Integer.parseInt(nexthop) == r.routerName)
//                                nexthop = "None";
                            myWriter.write(r.routingTable.distance.get(i) + ";" + nexthop);
                            if (i != r.routingTable.next.size() - 1)
                                myWriter.write("\n");
                        }
                        myWriter.close();
                        System.out.println("Successfully wrote to the file."+filename);
                    } catch (IOException e) {
                        //System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                }

                if (data(receive).toString().equals("SHUT-DOWN")) {
                    System.out.println("inside shutdown");
                    this.routerTCPServer.serverWorks = false;
                    this.serverWorks = false;
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
