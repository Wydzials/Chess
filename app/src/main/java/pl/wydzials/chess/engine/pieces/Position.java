package pl.wydzials.chess.engine.pieces;

public class Position {
    private final int row;
    private final int column;

    public Position(int row, int column) {
        if (row >= 0 && row <= 7 && column >= 0 && column <= 7) {
            this.row = row;
            this.column = column;
        } else {
            throw new IllegalArgumentException("Position out of bounds: " + row + " " + column);
        }
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    Position add(int rows, int columns) {
        return new Position(row + rows, column + columns);
    }

    boolean canAdd(int rows, int columns) {
        int checkRow = row + rows;
        int checkColumn = column + columns;

        return checkRow >= 0 && checkRow <= 7 && checkColumn >= 0 && checkColumn <= 7;
    }

    @Override
    public String toString() {
        return "(" + row + "," + column + ")";
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Position && (row == ((Position) o).row && column == ((Position) o).column));
    }

    @Override
    public int hashCode() {
        return 8 * row + column;
    }
}
