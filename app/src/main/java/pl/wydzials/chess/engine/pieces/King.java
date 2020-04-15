package pl.wydzials.chess.engine.pieces;

import java.util.ArrayList;
import java.util.List;

import pl.wydzials.chess.engine.Board;

public class King extends Piece {
    public boolean castlingReady;

    public King(Color color) {
        super(color);
        castlingReady = true;
    }

    @Override
    public List<Position> getPossibleMoves(Board board, Position myPosition) {
        List<Position> moves = new ArrayList<>();
        for (int row = -1; row <= 1; row++) {
            for (int column = -1; column <= 1; column++) {
                if (myPosition.canAdd(row, column)) {
                    Position testPosition = myPosition.add(row, column);
                    if (board.getPiece(testPosition) == null || board.getPiece((testPosition)).getColor() != color) {
                        moves.add(testPosition);
                    }
                }
            }
        }

        if (castlingReady) {
            int[] directions = {-1, 1};
            for (int direction : directions) {
                Position testPosition = myPosition.add(0, 0);
                while (testPosition.canAdd(0, direction)) {
                    testPosition = testPosition.add(0, direction);
                    if (board.getPiece(testPosition) instanceof Rook && ((Rook) board.getPiece(testPosition)).castlingReady) {
                        moves.add(myPosition.add(0, direction * 2));
                    } else if (board.getPiece(testPosition) != null) {
                        break;
                    }
                }
            }
        }
        return moves;
    }

    public void madeMove(Board board, Position posA, Position posB) {
        castlingReady = false;
    }
}
