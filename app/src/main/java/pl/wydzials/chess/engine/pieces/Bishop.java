package pl.wydzials.chess.engine.pieces;

import java.util.List;

import pl.wydzials.chess.engine.Board;

public class Bishop extends Piece {

    public Bishop(Color color) {
        super(color);
    }

    @Override
    public List<Position> getPossibleMoves(Board board, Position myPosition) {
        int[][] directions = {{1, 1}, {-1, 1}, {1, -1}, {-1, -1}};
        return checkMovesInDirections(board, myPosition, directions);
    }
}
