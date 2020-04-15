package pl.wydzials.chess.engine.pieces;

import java.util.ArrayList;
import java.util.List;

import pl.wydzials.chess.engine.Board;

public abstract class Piece {
    protected Color color;

    public Color getColor() {
        return color;
    }

    public Piece(Color color) {
        this.color = color;
    }

    public List<Position> getPossibleMoves(Board board, Position myPosition) {
        return new ArrayList<>();
    }

    List<Position> checkMovesInDirections(Board board, Position myPosition, int[][] directions) {
        List<Position> moves = new ArrayList<>();
        for (int[] direction : directions) {
            int row = direction[0];
            int column = direction[1];

            while (true) {
                if (myPosition.canAdd(row, column)) {
                    Position testPosition = myPosition.add(row, column);
                    if (board.getPiece(testPosition) == null) {
                        moves.add(testPosition);
                    } else if (board.getPiece(testPosition).getColor() != color) {
                        moves.add(testPosition);
                        break;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
                row += direction[0];
                column += direction[1];
            }
        }
        return moves;
    }

    public void madeMove(Board board, Position posA, Position posB) {
        // needed only for some pieces
    }
}
