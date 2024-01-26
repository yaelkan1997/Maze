package algorithms.mazeGenerators;

import java.util.Stack;

public class MyMazeGenerator extends AMazeGenerator{
    private Maze MMaze;

    // This is an implementation of Randomized depth-first search.
    @Override
    public Maze generate(int row, int col) {
        //default val (2), if the num of  rows and  columns less than 2
        row = Math.max(row,2);
        col = Math.max(col,2);

        MMaze = new EmptyMazeGenerator().generate(row, col);
        Position Cell = new Position(0, 0);
        MMaze.setVal(Cell, 1);
        Stack<Position> cellStack = new Stack<>();
        cellStack.push(Cell);

        while (!cellStack.isEmpty()) {
            Stack<Position> unvisitedNeighbors = unvisited(Cell);
            if (!unvisitedNeighbors.isEmpty()) {
                cellStack.push(Cell);
                Position temp = Cell;
                Cell = unvisitedNeighbors.elementAt((int) (Math.random() * unvisitedNeighbors.size()));
                //connect wall temp to cell and changes the value of cell to temp wall as well.
                MMaze.setVal(Cell, 1);
                int rowTemp = temp.getRowIndex();
                int rowCell = Cell.getRowIndex();
                int colTemp = temp.getColumnIndex();
                int colCell = Cell.getColumnIndex();
                if (rowTemp == rowCell)
                    MMaze.setVal(rowTemp, Math.max(colTemp, colCell) - 1, 1);
                else
                    MMaze.setVal(Math.max(rowTemp, rowCell) - 1, colTemp, 1);
            } else if (!cellStack.isEmpty())
                Cell = cellStack.pop();
        }


        if (!(row == 2 && col == 2)){
            InvertMaze(MMaze);
        }

        GenerateStartGoalPosition(MMaze);
        return MMaze;
    }

    /**
     invert the given maze
     */
    private void InvertMaze(Maze maze){
        for (int i = 0; i < maze.getRows(); i++) {
            for (int j = 0; j < maze.getColumns(); j++) {
                int val = maze.getVal(i, j);
                maze.setVal(i, j, val == 0 ? 1 : 0);

            }
        }
    }

    /**
     * @param cell given cell
     * @return Stack of unvisited, valid cells, null if there are none.
     */
    private Stack<Position> unvisited(Position cell) {
        Stack<Position> unvisited = new Stack<>();
        //get neighbors
        Position[] neighbors = new Position[4];
        int rows = cell.getRowIndex();
        int cols = cell.getColumnIndex();
        neighbors[0] = new Position(rows, cols + 2); // Up
        neighbors[1] = new Position(rows, cols - 2); // Down
        neighbors[2] = new Position(rows + 2, cols); // Right
        neighbors[3] = new Position(rows - 2, cols); // Left
        for (Position neighbor : neighbors) {
            int row = neighbor.getRowIndex();
            int col = neighbor.getColumnIndex();
            if (row < 0 || row > MMaze.getRows() || col < 0 || col > MMaze.getColumns())
                continue;
            if (MMaze.getVal(row, col) != 0)
                continue;
            unvisited.push(neighbor);
        }
        return unvisited;
    }

}
