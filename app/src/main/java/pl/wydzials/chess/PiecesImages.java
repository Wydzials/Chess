package pl.wydzials.chess;

public class PiecesImages {
    private int bishopB;
    private int bishopW;

    private int kingB;
    private int kingW;

    private int knightB;
    private int knightW;

    public int getBishopB() {
        return bishopB;
    }

    public int getBishopW() {
        return bishopW;
    }

    public int getKingB() {
        return kingB;
    }

    public int getKingW() {
        return kingW;
    }

    public int getKnightB() {
        return knightB;
    }

    public int getKnightW() {
        return knightW;
    }

    public PiecesImages() {
        bishopB = R.drawable.bishop_b;
        bishopW = R.drawable.bishop_w;

        kingB = R.drawable.king_b;
        kingW = R.drawable.king_w;

        knightB = R.drawable.knight_b;
        knightW = R.drawable.knight_w;
    }
}
