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

        Router[] routers = new Router[Constants.NETWORK_SIZE + 1];
        for (int i = 1; i <= Constants.NETWORK_SIZE; i++)
        {
            routers[i] = new Router(i, Constants.ROUTER_INPUT_FILE_PREFIX, Constants.ROUTER_TABLE_OUTPUT_FILE_PREFIX,
                    Constants.ROUTER_FORWARDING_OUTPUT_FILE_PREFIX);
            routers[i].start();
        }

//        for (int j = 1; j <=20; j++) {
//            for (int i = 2; i <= Constants.NETWORK_SIZE; i++) {
//                routers[i-1].sendMessageToNeighborUDP("127.0.0.1", routers[i].portUDP, "UPDATE-ROUTING-TABLE");
//                TimeUnit.SECONDS.sleep(5);
//            }
//            routers[2].sendMessageToNeighborUDP("127.0.0.1", routers[1].portUDP, "UPDATE-ROUTING-TABLE");
//            System.out.println("Pringting the distances:#################################################################");
//            System.out.println(routers[1].routingTable.distance);
//            System.out.println("Pringting the distances:#################################################################");
//
//        }

//        Router r1 = new Router(1, "input_router_1", "", "");
//        Router r2 = new Router(2, "input_router_2", "", "");
//        r1.start();
//        r2.start();

        String m1= "FORWARD" + ";" + 8 + ";" + 3 + ";" + "cat"+ ";"  + "127.0.0.1"+ ";"  + routers[8].portUDP;
        routers[1].sendMessageToNeighborUDP("127.0.0.1", routers[2].portUDP, m1);



        System.out.println("Here");





    }
}
