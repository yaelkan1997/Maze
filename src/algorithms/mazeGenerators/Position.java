package algorithms.mazeGenerators;


import java.io.Serializable;

/**
 * class representing the position
 */
public class Position implements Serializable {

    private final int row;
    private final int column;

    /**
     * Position constructor using the current Row and Column
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Returns the row number of this positions
     */
    public int getRowIndex() {
        return row;
    }

    /**
     * Returns the column number of this positions
     */
    public int getColumnIndex() {
        return column;
    }

    /**
     * Overloads the toString method on object so that it prints position in a "{row,column}" format
     */
    public String toString() {
        return "{" + this.getRowIndex() + "," + this.getColumnIndex() + "}";
    }

    /**
     * Changes the coordinate values
     *
     * @return new Position
     */
    public Position Up() {
        return new Position(this.getRowIndex() - 1, this.getColumnIndex());
    }

    /**
     * Changes the coordinate values
     *
     * @return new Position
     */
    public Position Down() {
        return new Position(this.getRowIndex() + 1, this.getColumnIndex());
    }

    /**
     * Changes the coordinate values
     *
     * @return new Position
     */
    public Position Left() {
        return new Position(this.getRowIndex(), this.getColumnIndex() - 1);
    }

    /**
     * Changes the coordinate values
     *
     * @return new Position
     */
    public Position Right() {
        return new Position(this.getRowIndex(), this.getColumnIndex() + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return this.row == position.getRowIndex() && this.column == position.getColumnIndex();
    }

}
