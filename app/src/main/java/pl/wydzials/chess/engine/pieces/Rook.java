package pl.wydzials.chess.engine.pieces;

import java.util.List;

import pl.wydzials.chess.engine.Board;

public class Rook extends Piece {

    public boolean castlingReady;

    public Rook(Color color) {
        super(color);
        castlingReady = true;
    }

    @Override
    public List<Position> getPossibleMoves(Board board, Position myPosition) {
        int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        return checkMovesInDirections(board, myPosition, directions);
    }

    public void madeMove(Board board, Position posA, Position posB) {
        castlingReady = false;
    }
}
