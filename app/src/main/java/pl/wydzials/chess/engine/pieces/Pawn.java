package pl.wydzials.chess.engine.pieces;

import java.util.ArrayList;
import java.util.List;

import pl.wydzials.chess.engine.Board;

public class Pawn extends Piece {

    public Pawn(Color color) {
        super(color);
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
        if ((myPosition.getRow() - rowChange) % 7 == 0) {
            Position testPosition = myPosition.add(rowChange*2, 0);
            if(board.getPiece(testPosition) == null || board.getPiece(testPosition).color != color) {
                moves.add(testPosition);
            }
        }

        return moves;
    }
}
