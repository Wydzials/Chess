package pl.wydzials.chess.engine.pieces;

public enum Color {
    WHITE,
    BLACK;

    public Color other() {
        return this.equals(Color.BLACK) ? Color.WHITE : Color.BLACK;
    }
}
