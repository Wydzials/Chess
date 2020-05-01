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
        List<Position> possibleMoves = new ArrayList<>();
        int[][] knightMoves = {{-1, -2}, {-2, -1}, {-2, 1}, {-1, 2}, {1, -2}, {2, -1}, {2, 1}, {1, 2}};

        for (int[] move : knightMoves) {
            if (myPosition.canAdd(move[0], move[1])) {
                Position positionAfterMove = myPosition.add(move[0], move[1]);
                Piece p = board.getPiece(positionAfterMove);
                if (p == null || p.getColor() != color) {
                    possibleMoves.add(positionAfterMove);
                }
            }
        }
        return possibleMoves;
    }
}
