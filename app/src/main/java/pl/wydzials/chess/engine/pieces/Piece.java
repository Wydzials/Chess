package pl.wydzials.chess.engine.pieces;

public abstract class Piece {
    private Color color;

    public Color getColor() {
        return color;
    }

    public Piece(Color color) {
        this.color = color;
    }
}
