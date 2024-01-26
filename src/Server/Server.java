package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private IServerStrategy strategy;
    private int port;

    private int timeOut;
    private volatile boolean stop;

    private ExecutorService ThreadPool;

    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {

        this.timeOut=listeningIntervalMS;
        this.strategy=strategy;
        this.port=port;
        this.ThreadPool= Executors.newFixedThreadPool(Configurations.getThreadPoolSize());
    }

    public void start() {
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(timeOut);
            new Thread(()-> handleMultipleClients(serverSocket)).start();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleMultipleClients(ServerSocket serverSocket)  {
        try {
            while (!stop){
                try{
                    Socket clientSocket=serverSocket.accept();
                    // Insert the new task into the thread pool:
                    ThreadPool.submit(()->handleClient(clientSocket));

                }
                catch (SocketTimeoutException ignored){
                }
            }
            serverSocket.close();
            ThreadPool.shutdownNow();
        }
        catch (IOException e){
            e.printStackTrace();
        }


    }

    private void handleClient(Socket socket) {
        try {
            strategy.applyStrategy(socket.getInputStream(),socket.getOutputStream());

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void stop() {
        stop = true;
    }
}
