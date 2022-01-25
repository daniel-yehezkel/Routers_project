public class Main {
    public static void main(String[] args) {
        Router r1 = new Router(1,8888);
        Router r2 = new Router(2,7777);

        r1.start();
        r2.start();

        r1.sendMessageToNeighbor("127.0.0.1", 7777, 30);
        r2.sendMessageToNeighbor("127.0.0.1", 8888, 20);
    }
}
