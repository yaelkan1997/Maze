

package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

public class SearchableMaze implements ISearchable {
    Maze maze;
    private  MazeState start;
    private  MazeState goal;

    /**
     * SearchableMaze Constructor.
     *
     * @param maze Given maze to store along its starting and goal positions.
     */
    public SearchableMaze(Maze maze) {
        this.maze = maze;
        start = new MazeState(maze.getStartPosition());
        goal = new MazeState(maze.getGoalPosition());
    }

    /**
     * @return Stored starting State.
     */

    @Override
    public MazeState getStart() {
        return start;
    }

    /**
     * @return Stored goal State.
     */
    @Override
    public MazeState getGoal() {
        return goal;
    }
    /**
     * Checks if traversal from state prev to state current is possible.
     *
     * @param removeDiagonal boolean whether to check diagonal movement.
     * @return True if there's a valid traversal, else false.
     */

    @Override
    public boolean validTraversal(AState curr, AState prev, boolean removeDiagonal) {
        int diffRow = Math.abs(curr.getState().getRowIndex() - prev.getState().getRowIndex());
        int diffCol = Math.abs(curr.getState().getColumnIndex() - prev.getState().getColumnIndex());
        boolean tf=false;
        if (diffRow >= 2 || diffCol >= 2)
            return tf;
        else{
            tf= !removeDiagonal || diffRow + diffCol != 2;
        }
        return tf;
    }

    /**
     * Returns an array of four neighboring States to a given State.
     *
     * @param state Given State.
     * @return array of neighbors to state.
     */
    @Override
    public AState[] getAllPossibleStates(AState state) {
        AState[] states = new AState[4];
        Position pos = state.getState();
        states[0] = new MazeState(pos.Up());
        states[1] = new MazeState(pos.Right());
        states[2] = new MazeState(pos.Down());
        states[3] = new MazeState(pos.Left());
        return states;
    }


    /**
     * @param state Given State
     * @return True if given State is an empty space in the maze, and is within its limits.
     */
    @Override
    public boolean isIn(AState state) {
        int row = state.getState().getRowIndex(), col = state.getState().getColumnIndex();
        if ( row >= maze.getRows()|| row < 0  || col < 0 || col >= maze.getColumns())
            return false;
        return maze.getVal(row, col) == 0;
    }



}
