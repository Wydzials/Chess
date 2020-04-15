package pl.wydzials.chess.engine.pieces;

import java.util.ArrayList;
import java.util.List;

import pl.wydzials.chess.engine.Board;

public class Knight extends Piece {

    public Knight(Color color) {
        super(color);
    }

    @Override
    public List<Position> getPossibleMoves(Board board, Position myPosition) {
        List<Position> moves = new ArrayList<>();
        int[][] possibleMoves = {{-1, -2}, {-2, -1}, {-2, 1}, {-1, 2}, {1, -2}, {2, -1}, {2, 1}, {1, 2}};

        for (int[] possibleMove : possibleMoves) {
            if (myPosition.canAdd(possibleMove[0], possibleMove[1])) {
                Position tmpPosition = myPosition.add(possibleMove[0], possibleMove[1]);
                Piece p = board.getPiece(tmpPosition);
                if (p == null || p.getColor() != color) {
                    moves.add(tmpPosition);
                }
            }
        }
        return moves;
    }
}
