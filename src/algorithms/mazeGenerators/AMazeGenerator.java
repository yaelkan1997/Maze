package algorithms.mazeGenerators;

import java.util.Random;

public  abstract class AMazeGenerator implements IMazeGenerator {

    public long measureAlgorithmTimeMillis(int row, int column) {
        long StartTime = System.currentTimeMillis();
        generate(row, column);
        return System.currentTimeMillis() - StartTime;
    }

    /**
     * Generates a random Start position and a random Goal.
     */
    protected void GenerateStartGoalPosition(Maze maze) {

        // Random start / goal generation:
        Position start, goal;
        do {
            start = RandomPosition(maze);
            goal = RandomPosition(maze);
        }
        while (start.equals(goal) || maze.getVal(start) != 0 || maze.getVal(goal) != 0);
        maze.setStartPosition(start);
        maze.setGoalPosition(goal);
    }

    /**
     * Creates a position on a random wall.
     */
    private Position RandomPosition(Maze maze){
        int row;
        int col;
        Random random = new Random();
        boolean randomBoolean = random.nextBoolean();
        if (randomBoolean) {
            row = (int) (Math.random() * maze.getRows());
            if (randomBoolean){
              col = 0;
            }
            else
                col = maze.getColumns() - 1;
        } else {
            col = (int) (Math.random() * maze.getColumns());
            if (randomBoolean){
                row = 0;
            }
            else
                row = maze.getColumns() - 1;
        }
        return new Position(row, col);
    }

}
