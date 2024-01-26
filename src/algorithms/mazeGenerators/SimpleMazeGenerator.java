package algorithms.mazeGenerators;

public class SimpleMazeGenerator extends AMazeGenerator {

    /**
     * Randomly puts numbers in different places
     */

    public Maze generate(int row, int column) {
        // Ensure that the maze has at least 2 rows and 2 columns.
        row = Math.max(row, 2);
        column = Math.max(column, 2);
        Maze maze = new Maze(row, column);
        // Create a new Maze object and fill it with random values.
        randomizeMaze(maze, row, column);
        GenerateStartGoalPosition(maze);
        connectStartAndGoal(maze);
        return maze;
    }

    /**
     * Ensures that there is a path from the start to the goal in the given maze.
     */
    private void connectStartAndGoal(Maze maze) {
        int curr_row = maze.getStartPosition().getRowIndex();
        int curr_col = maze.getStartPosition().getColumnIndex();
        int goal_row = maze.getGoalPosition().getRowIndex();
        int goal_column = maze.getGoalPosition().getColumnIndex();
        while (curr_row != goal_row || curr_col != goal_column) {
            // Using random, we will decide if to get closer by column or row
            if (Math.random() < 0.5) {
                // Move towards the goal column.
                if (curr_col < goal_column) {
                    maze.setVal(curr_row, ++curr_col, 0);
                }
                if (curr_col > goal_column) {
                    maze.setVal(curr_row, --curr_col, 0);
                }
            } else {
                // Move towards the goal row.
                if (curr_row < goal_row) {
                    maze.setVal(++curr_row, curr_col, 0);
                }
                if (curr_row > goal_row) {
                    maze.setVal(--curr_row, curr_col, 0);
                }
            }
        }

    }

    /**
     * Fills the given maze with random 0s and 1s.
     */
    public void randomizeMaze(Maze maze, int row, int column) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                int randomValue = Math.random() < 0.5 ? 0 : 1;
                maze.setVal(i, j, randomValue);
            }
        }
    }

}
