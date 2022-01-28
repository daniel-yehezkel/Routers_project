import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServerThread extends Thread {
    private final String _serverName;
    private final Socket _socket;
    Router r;
    public TCPServerThread(String serverName, Socket socket, Router r) {
        _serverName = serverName;
        _socket = socket;
        this.r = r;
    }

    @Override
    public void run() {
        super.run();
        try {
            handleRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleRead() throws Exception {
        DataInputStream input = new DataInputStream(_socket.getInputStream());
        String msg = input.readUTF();
        //System.out.println(_serverName + "_TCP Received message: " + msg);

        //System.out.println(_serverName + "got: " + msg);
        //System.out.println("Sending current vector");
        try {
            DataOutputStream output = new DataOutputStream(_socket.getOutputStream());
            output.writeUTF(this.r.distVecTime.get(Integer.parseInt(msg)).toString());
        } catch (Exception e) {
            //System.out.println("ERROR sending vector");
            e.printStackTrace();
        }

        input.close();
        _socket.close();
    }
}
