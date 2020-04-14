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
}
