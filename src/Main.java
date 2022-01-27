import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws Exception {
        TimeUnit.SECONDS.sleep(1);

        Router r1 = new Router(1, "input_router_1", "", "");
        Router r2 = new Router(2, "input_router_2", "", "");

        r1.start();
        r2.start();

        String a = r1.sendMessageToNeighborTCP("127.0.0.1", r2.portTCP, "2");
        String b = r2.sendMessageToNeighborTCP("127.0.0.1", r1.portTCP, "1");

        System.out.println(a);
        System.out.println(b);

//        r2.sendMessageToNeighborUDP("127.0.0.1", r1.portUDP, "PRINT-ROUTING-TABLE");
//        r2.sendMessageToNeighborUDP("127.0.0.1", r1.portUDP, "fdgsfg");
    }
}
