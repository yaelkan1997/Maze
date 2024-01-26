package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;

public class ServerStrategySolveSearchProblem implements IServerStrategy{
    String tempDirectoryPath = System.getProperty("java.io.tmpdir");
    int id;
    @Override
    public void applyStrategy(InputStream inputStream, OutputStream outputStream) {
        try{
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream);
            ObjectInputStream fromClient = new ObjectInputStream(inputStream);

            Maze mazeToSolve = (Maze)fromClient.readObject();

            Solution sol = findSolution(mazeToSolve);
            //checking f there is a solution
            if (sol == null) {
                SearchableMaze searchableMaze = new SearchableMaze(mazeToSolve);
                ASearchingAlgorithm solver = Configurations.getMazeSearchingAlgorithm();
                sol = solver.solve(searchableMaze);
                OutputAsFile(mazeToSolve, sol);
            }
            //returns the solution to the client
            toClient.writeObject(sol);


        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private void OutputAsFile(Maze maze, Solution sol) throws IOException {

        int temp = id++;
        FileOutputStream fileOutputStream = new FileOutputStream(tempDirectoryPath + "maze_" + temp);
        // Creates a new ObjectOutputStream object that wraps the FileOutputStream object
        ObjectOutputStream objectOutputStream =new ObjectOutputStream(fileOutputStream);
        // Writes the compressed byte array representation of the maze to the output stream
        objectOutputStream.writeObject(MyCompressorOutputStream.compress(maze.toByteArray()));
        // Writes the Solution object to the output stream
        objectOutputStream.writeObject(sol);
        // Flushes the output stream to ensure all data is written
        objectOutputStream.flush();
        // Closes the output stream and releases any resources associated with it
        objectOutputStream.close();
    }


    /**
     * Compresses the maze's byte representation and compares it to files beginning with prefix "maze_". If the length of
     * the compared arrays is equal, continues to compare both arrays.
     * If equal, returns solution stored in the file.
     * If no comparison is found, returns null.
     * @param maze - Maze to compare
     * @return - Solution if found, else null.
     */
    private Solution findSolution(Maze maze) throws IOException {
        Solution sol = null;

        byte[] compressed = MyCompressorOutputStream.compress(maze.toByteArray());


        File dir = new File(tempDirectoryPath);
        File[] foundFiles = dir.listFiles((dir1, name) -> name.startsWith("maze_"));
        if (foundFiles.length == 0) {
            id = 0;
            return null;
        }
        id = foundFiles.length;

        for (File file : foundFiles) {
            boolean isMatch = true;
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            try {
                byte[] temp = (byte[]) objectInputStream.readObject();

                if (temp.length == compressed.length)
                    for (int i = 0; i < temp.length; i++) {
                        if (temp[i] != compressed[i]) {
                            isMatch = false;
                            break;
                        }
                    }
                if (isMatch) {
                    sol = (Solution) objectInputStream.readObject();
                    break;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return sol;
    }
}
