import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws Exception {
        Random random = new Random();
        random.setSeed(Constants.SEED);

        // create the input for the routers
        CreateInput.createRouterInputAndWeights(Constants.ROUTER_INPUT_FILE_PREFIX,
                Constants.SELECT_NEIGHBOR_PROBABILITY, Constants.CHANGE_WEIGHT_PROBABILITY, Constants.FIRST_UPD_PORT,
                Constants.FIRST_TCP_PORT, Constants.MAXIMUM_WEIGHT, Constants.NETWORK_SIZE,
                Constants.TEST_SIZE + 1, random);


        TimeUnit.SECONDS.sleep(1);

        Router r1 = new Router(1, "input_router_1", "", "");
        Router r2 = new Router(2, "input_router_2", "", "");

        r1.start();
        r2.start();

        String a = r1.sendMessageToNeighborTCP("127.0.0.1", r2.portTCP, "2");
        String b = r2.sendMessageToNeighborTCP("127.0.0.1", r1.portTCP, "1");

        System.out.println(a);
        System.out.println(b);

        r2.sendMessageToNeighborUDP("127.0.0.1", r1.portUDP, "SHUT-DOWN");
        r2.sendMessageToNeighborUDP("127.0.0.1", r2.portUDP, "SHUT-DOWN");

        r2.sendMessageToNeighborUDP("127.0.0.1", r1.portUDP, "HELLO");
        r2.sendMessageToNeighborTCP("127.0.0.1", r1.portTCP, "HELLO");

    }
}
