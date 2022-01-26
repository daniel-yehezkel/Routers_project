public class Main {
    public static void main(String[] args) {
        Router r1 = new Router(1, "input_router_1", "", "");
        Router r2 = new Router(2, "input_router_2", "", "");

        r1.start();
        r2.start();

        r1.sendMessageToNeighborTCP("127.0.0.1", r2.portTCP, "Hi");
        r2.sendMessageToNeighborTCP("127.0.0.1", r1.portTCP, "Bye");

        r2.sendMessageToNeighborUDP("127.0.0.1", r1.portUDP, "PRINT-ROUTING-TABLE");
        r2.sendMessageToNeighborUDP("127.0.0.1", r1.portUDP, "fdgsfg");
    }
}
