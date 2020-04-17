package pl.wydzials.chess.engine.pieces;

import java.util.ArrayList;
import java.util.List;

import pl.wydzials.chess.engine.Board;

public class Pawn extends Piece {

    public boolean enPassantReady;

    public Pawn(Color color) {
        super(color);
        enPassantReady = false;
    }

    @Override
    public List<Position> getPossibleMoves(Board board, Position myPosition) {
        List<Position> moves = new ArrayList<>();

        int rowChange = (color == Color.WHITE) ? -1 : 1;
        for (int column = -1; column <= 1; column++) {
            if (myPosition.canAdd(rowChange, column)) {
                Position testPosition = myPosition.add(rowChange, column);
                if ((board.getPiece(testPosition) != null && board.getPiece(testPosition).color != color && column != 0) ||
                        board.getPiece(testPosition) == null && column == 0) {
                    moves.add(testPosition);
                }
            }
        }

        // move two squares
        if ((myPosition.getRow() - rowChange) % 7 == 0) {
            Position testPosition = myPosition.add(rowChange * 2, 0);
            Position emptyPosition = myPosition.add(rowChange, 0);

            if (board.getPiece(testPosition) == null && board.getPiece(emptyPosition) == null) {
                moves.add(testPosition);
            }
        }

        // en passant
        int[] columns = {-1, 1};
        for (int column : columns) {
            if (myPosition.canAdd(0, column)) {
                Position testPosition = myPosition.add(0, column);

                Piece piece = board.getPiece(testPosition);
                if (piece instanceof Pawn && piece.color != color && ((Pawn) piece).enPassantReady) {
                    int rowsToAdd = (color == Color.WHITE) ? -1 : 1;
                    moves.add(testPosition.add(rowsToAdd, 0));
                }
            }
        }
        return moves;
    }

    public void madeMove(Board board, Position posA, Position posB) {
        if (Math.abs(posA.getRow() - posB.getRow()) == 2) {
            enPassantReady = true;
        }
    }
}
