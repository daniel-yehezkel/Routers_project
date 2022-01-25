public class Main {
    public static void main(String[] args) {
    Router r1 = new Router(1, "input_router_1", "", "");
    Router r2 = new Router(2, "input_router_2", "", "");

    r1.start();
    r2.start();

    r1.sendMessageToNeighbor("127.0.0.1", r2.portTCP, "Hi");
    r2.sendMessageToNeighbor("127.0.0.1", r1.portTCP, "Bye");
    }
}
