package pl.wydzials.chess;

import java.util.HashMap;

import pl.wydzials.chess.pieces.Color;
import pl.wydzials.chess.pieces.Piece;

public class PieceImages {

    private HashMap<String, Integer> pieces;

    PieceImages() {
        pieces = new HashMap<>();

        pieces.put("BishopB", R.drawable.bishop_b);
        pieces.put("BishopW", R.drawable.bishop_w);

        pieces.put("KingB", R.drawable.king_b);
        pieces.put("KingW", R.drawable.king_w);

        pieces.put("KnightB", R.drawable.knight_b);
        pieces.put("KnightW", R.drawable.knight_w);

        pieces.put("PawnB", R.drawable.pawn_b);
        pieces.put("PawnW", R.drawable.pawn_w);

        pieces.put("QueenB", R.drawable.queen_b);
        pieces.put("QueenW", R.drawable.queen_w);

        pieces.put("RookB", R.drawable.rook_b);
        pieces.put("RookW", R.drawable.rook_w);
    }

    int getPieceImage(Piece piece) {
        if (piece != null) {
            String name = piece.getClass().getSimpleName();
            name += piece.getColor() == Color.WHITE ? 'W' : "B";

            Integer image = pieces.get(name);

            if (image != null) {
                return image;
            } else {
                throw new IllegalArgumentException("No image for piece: " + piece);
            }
        } else {
            return R.drawable.empty;
        }

    }
}
