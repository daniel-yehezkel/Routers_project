import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Router extends Thread {
    int routerName;
    int portUDP;
    int portTCP;
    int numNetRouters;
    Map<Integer, Neighbor> neighborsProperties = new HashMap<>();
    RoutingTable routingTable;
    myTCPServer myTcpServer;

    public Router(int name, String inputFilePrefix, String tableFilePrefix, String
            forwardingFilePrefix) {
        this.myTcpServer = null;
        this.routerName = name;

        File inputFile = new File("text_files\\" + inputFilePrefix + ".txt"); //TODO: no 'text_files' in production
        try {
            Scanner myReader = new Scanner(inputFile);
            this.portUDP = Integer.parseInt(myReader.nextLine());
            this.portTCP = Integer.parseInt(myReader.nextLine());
            this.numNetRouters = Integer.parseInt(myReader.nextLine());

            int counter = 0;
            int firstNeighbor = 0;
            String nextLine = myReader.nextLine();
            while (!Objects.equals(nextLine, "*")) {
                int neighborName = Integer.parseInt(nextLine);
                if (counter == 0) {
                    firstNeighbor = neighborName;
                }
                String neighborIP = myReader.nextLine();
                int neighborPortUDP = Integer.parseInt(myReader.nextLine());
                int neighborPortTCP = Integer.parseInt(myReader.nextLine());
                int neighborWeight = Integer.parseInt(myReader.nextLine());
                neighborsProperties.put(neighborName, new Neighbor(neighborName, neighborIP, neighborPortUDP,
                        neighborPortTCP, neighborWeight));
                nextLine = myReader.nextLine();
                counter++;
            }

            int diamBound = Integer.parseInt(myReader.nextLine());
            this.routingTable = new RoutingTable(name, this.numNetRouters, firstNeighbor, diamBound);
            myReader.close();

        } catch (IOException e) {
            System.out.println("Router reading input file ERROR");
        }
    }

    @Override
    public void run() {
        super.run();
        this.myTcpServer = new myTCPServer("Router " + this.routerName, this.portTCP);
    }

    public void sendMessageToNeighbor(String ip, int port, int message) {
        new myTCPSendMessage("Router " + this.routerName, ip, port, message).start();
    }
}

