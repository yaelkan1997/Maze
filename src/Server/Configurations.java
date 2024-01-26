package Server;

import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;
import algorithms.search.ASearchingAlgorithm;
import algorithms.search.BestFirstSearch;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.DepthFirstSearch;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configurations {
    private static Properties prop;

    public static Properties getProp() {
        if (prop == null){
            try (InputStream input = Configurations.class.getClassLoader().getResourceAsStream("config.properties")) {
                prop = new Properties();
                prop.load(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return prop;
    }
    public static int getThreadPoolSize() {
        prop = getProp();
        String thread_size = prop.getProperty("threadPoolSize");
        int size = Integer.parseInt(thread_size);
        return size > 0 ? size : 1;
    }

    public static ASearchingAlgorithm getMazeSearchingAlgorithm() {
        prop = getProp();
        String maze_search_name = prop.getProperty("mazeSearchingAlgorithm");
        if (maze_search_name.equals("BreadthFirstSearch")) {
            return new BreadthFirstSearch();
        } else {
            return (ASearchingAlgorithm)(maze_search_name.equals("DepthFirstSearch") ? new DepthFirstSearch() : new BestFirstSearch());
        }
    }

    public static AMazeGenerator getMazeGeneratingAlgorithm() {
        prop = getProp();
        String maze_generate_name = prop.getProperty("mazeGeneratingAlgorithm");
        return (AMazeGenerator)(maze_generate_name.equals("SimpleMazeGenerator") ? new SimpleMazeGenerator() : new MyMazeGenerator());
    }
}