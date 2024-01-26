package algorithms.mazeGenerators;
import java.util.Random;

public class EmptyMazeGenerator extends AMazeGenerator{
    public Maze generate(int row, int column){

        // Default values if needed:
        row = Math.max(row, 2);
        column = Math.max(column, 2);

        Maze maze = new Maze(row, column);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++)
                maze.setVal(i, j, 0);
        }
        GenerateStartGoalPosition(maze);
        return maze;
    }
}
