package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy {

    /**
     * applying maze generation on clients input - array with the dimensions required
     * @param inputStream ,client input
     * @param outputStream ,server response
     */
    @Override
    public void applyStrategy(InputStream inputStream, OutputStream outputStream) {
        try {

            ObjectInputStream fromClient = new ObjectInputStream(inputStream);
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream);

            int[] clientInfo = (int[]) (fromClient.readObject());
            AMazeGenerator mazeGenerator = Configurations.getMazeGeneratingAlgorithm();
            int rows = clientInfo[0];
            int columns = clientInfo[1];
            Maze maze = mazeGenerator.generate(rows, columns);
            byte[] maze_b = maze.toByteArray();

            toClient.writeObject(MyCompressorOutputStream.compress(maze_b));


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}

