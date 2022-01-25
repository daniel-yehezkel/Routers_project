import java.util.ArrayList;

public class RoutingTable {
    int numRouters;
    ArrayList<Integer> distance;
    ArrayList<Integer> next;
    public RoutingTable(int selfName, int numRouters, int firstNeighbor, int diamBound){
        this.numRouters = numRouters;
        this.distance = new ArrayList<>(numRouters + 1);
        this.next = new ArrayList<>(numRouters + 1);
        distance.add(0, -1);
        next.add(0, -1);
        for (int i = 1; i <= numRouters; i++) {
            if (i == selfName){
                distance.add(i, 0);
                next.add(i, i);
            }
            else {
                distance.add(i, diamBound);
                next.add(i, firstNeighbor);
            }
        }
    }
}