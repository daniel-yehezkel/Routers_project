import java.util.ArrayList;

public class Neighbor {
    public ArrayList<Integer> neighborDistanceVector;
    int neighborName;
    String neighborIP;
    int neighborPortUDP;
    int neighborPortTCP;
    int neighborWeight;

    public Neighbor(int neighborName, String neighborIP, int neighborPortUDP, int neighborPortTCP, int neighborWeight) {
        this.neighborName = neighborName;
        this.neighborIP = neighborIP;
        this.neighborPortUDP = neighborPortUDP;
        this.neighborPortTCP = neighborPortTCP;
        this.neighborWeight = neighborWeight;
        this.neighborDistanceVector = null;
    }
}
