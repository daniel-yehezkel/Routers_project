import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.net.UnknownHostException;
import java.io.DataOutputStream;
import java.net.DatagramSocket;
import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.File;
import java.net.DatagramPacket;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.util.*;


public class Router extends Thread {

    private int name;
    private File myObj;

    public Router(int name, String inputFilePrefix, String tableFilePrefix, String forwardingFilePrefix) { // Router Constructor
        this.name = name;
        ServerSocket serverSocket;
        Socket clientSocket;
        ArrayList<String> lst = new ArrayList<>();
        File myObj = new File("text_files\\" + inputFilePrefix + ".txt");
        try {
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                lst.add(data);
            }
            myReader.close();
            System.out.println(lst.get(1));
            serverSocket = new ServerSocket(Integer.parseInt(lst.get(1)));
            clientSocket = serverSocket.accept();


        } catch (IOException e) {
            System.out.println("error");
        }

    }

    public void start(int port)

    in =new

    BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    String greeting = in.readLine();
        if("hello server".

    equals(greeting))

    {
        out.println("hello client");
    }
        else

    {
        out.println("unrecognised greeting");
    }

}

    public static void main(String[] args) {
        System.out.println("Hello World");
        Router R1 = new Router(1, "input_router_15", "table_file_15", "forward_router_15");
    }
}
