public class Router extends Thread {
    TCPServer _tcpServer;
    int _tcpPortListen;
    int _routerName;
    // TODO: UDPServer _udpServer;

    public Router(int routerName, int port) {
        _tcpServer = null;
        _routerName = routerName;
        _tcpPortListen = port;
    }

    @Override
    public void run() {
        super.run();
        _tcpServer = new TCPServer("Router " + _routerName, _tcpPortListen);
    }

    public void sendMessageToNeighbor(String ip, int port, int message) {
        new TCPSendMessage("Router " + _routerName, ip, port, message).start();
    }
}
