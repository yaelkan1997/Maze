package algorithms.mazeGenerators;

import java.io.Serializable;
import java.util.ArrayList;

public class Maze implements Serializable {
    private int rows;
    private int columns;
    private int[][] maze;
    private Position startPosition, goalPosition;

    /**
     * constructor
     */
    public Maze(int row, int column) {
        this.rows = row;
        this.columns = column;
        maze = new int[row][column];
    }
    public Maze(byte[] b) {
        CalcSizeMaze(b);
        int row ;
        int col ;
        int counter = 0;
        for (row = 0; row < maze.length; row++) {
            for (col = 0; col < maze[0].length; col++) {
                if(b[counter] == 1){
                    maze[row][col] = 1;
                }
                if(b[counter] == 3){
                    startPosition = new Position(row, col);
                }
                if(b[counter] == 4){
                    goalPosition = new Position(row, col);
                }
                if(b[counter] == 2){
                    col--;
                }
                counter++;

            }
        }
    }

    private void CalcSizeMaze(byte[] b) {
        int row_amount;
        int col_amount = 0;
        while (b[col_amount] != 2)
            col_amount++;
        row_amount = (b.length - 1) / (col_amount);
        rows = row_amount;
        columns = col_amount;
        maze = new int[row_amount][col_amount];
    }

    public void setVal(int row, int column, int val) {

        maze[row][column] = val;
    }


    public void setVal(Position pos, int val) {
        maze[pos.getRowIndex()][pos.getColumnIndex()] = val;
    }

    /**
     * Gets the number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Gets the number of columns
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Given the row and column numbers, return the current value within that spot. Returns -1 if value is off limits.
     */
    public int getVal(int row, int column) {
        if (row < 0 || column < 0 || row >= this.getRows() || column >= this.getColumns())
            return -1;
        return this.maze[row][column];
    }
    /**
     * Given a position,return the current value within that spot.
     */
    public int getVal(Position pos) {
        return getVal(pos.getRowIndex(), pos.getColumnIndex());
    }

    /**
     * Gets the starting position
     */
    public Position getStartPosition() {
        return startPosition;
    }

    /**
     * Sets the starting position
     */
    public void setStartPosition(Position p) {
        startPosition = p;
    }

    /**
     * Gets the goal position
     */
    public Position getGoalPosition() {
        return goalPosition;
    }

    /**
     * Sets the goal position
     */
    public void setGoalPosition(Position p) {
        goalPosition = p;
    }

    /**
     * Prints the array to the system
     */
    public void print() {
        for (int row = 0; row < rows; row++) {
            StringBuilder str = new StringBuilder("{");
            for (int col = 0; col < columns; col++) {
                if (row == startPosition.getRowIndex() && col == startPosition.getColumnIndex()) {
                    str.append("S,");
                } else if (row == this.getGoalPosition().getRowIndex() && col == this.getGoalPosition().getColumnIndex()) {
                    str.append("E,");
                } else {
                    str.append(getVal(row, col)).append(",");
                }
            }
            str.setCharAt(str.length() - 1, '}');
            System.out.println(str);
        }
    }

    /**
     * Returns byte array containing maze information. 0 - Empty space, 1 - wall, 2 - new row,
     * 3 - start position, 4 - goal position.
     * Byte 2 is only used once and the entire size is calculated via its location and the length of the array.
     * Example:
     * For the maze [1, E]
     *              [0, S]
     * return array = [1, 4, 2, 0, 3, 2].
     * @return byte array.
     */

    public byte[] toByteArray(){
        ArrayList<Byte> MazeB = new ArrayList<>();
        boolean newRowFlag = false;
        for (int[] row : maze) {
            for (int col : row) {
                MazeB.add((byte) col);
            }
            if (!newRowFlag) {
                MazeB.add((byte) 2);
                newRowFlag = true;
            }
        }
        int startRowIndex = startPosition.getRowIndex(); //Rows
        int startColIndex = startPosition.getColumnIndex(); // Columns
        //Row * column amount
        int R_c =  startRowIndex * maze[0].length;
        int check = R_c > 0 ? R_c + 1 : 0;
        check += startColIndex;
        MazeB.set(check, (byte) 3);

        int goalRowIndex = goalPosition.getRowIndex(); //Rows
        int goalColIndex = goalPosition.getColumnIndex();// Columns
        //Row * column amount
        R_c =  goalRowIndex * maze[0].length;
        check = R_c > 0 ? R_c + 1 : 0;
        check += goalColIndex;
        MazeB.set(check, (byte) 4);


        byte[] byteArray  = new byte[MazeB.size()];
        for (int i = 0; i < MazeB.size(); i++) {
            byteArray [i] = MazeB.get(i);
        }
        return byteArray ;
    }

}
