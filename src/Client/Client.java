package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client implements IClientStrategy {
    private final InetAddress IP;
    private final int port;
    private final IClientStrategy clientstrategy;

    public Client(InetAddress IP, int Port, IClientStrategy clientstrategy) {
        this.IP=IP;
        this.port=Port;
        this.clientstrategy=clientstrategy;

    }

    public void communicateWithServer() {
        try (Socket socketServer = new Socket(IP, port)) {
            clientstrategy.clientStrategy(socketServer.getInputStream(), socketServer.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
        try {
            throw new UnsupportedOperationException("The strategy is not negligible.");
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
}
