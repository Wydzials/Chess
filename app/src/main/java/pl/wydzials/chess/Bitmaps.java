package pl.wydzials.chess;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;

import pl.wydzials.chess.engine.pieces.Color;
import pl.wydzials.chess.engine.pieces.Piece;

public class Bitmaps {

    private HashMap<String, Bitmap> bitmaps;
    private Resources resources;

    Bitmaps(Resources resources) {
        bitmaps = new HashMap<>();
        this.resources = resources;

        bitmaps.put("BishopB", BitmapFactory.decodeResource(resources, R.drawable.bishop_b));
        bitmaps.put("BishopW", BitmapFactory.decodeResource(resources, R.drawable.bishop_w));

        bitmaps.put("KingB", BitmapFactory.decodeResource(resources, R.drawable.king_b));
        bitmaps.put("KingW", BitmapFactory.decodeResource(resources, R.drawable.king_w));

        bitmaps.put("KnightB", BitmapFactory.decodeResource(resources, R.drawable.knight_b));
        bitmaps.put("KnightW", BitmapFactory.decodeResource(resources, R.drawable.knight_w));

        bitmaps.put("PawnB", BitmapFactory.decodeResource(resources, R.drawable.pawn_b));
        bitmaps.put("PawnW", BitmapFactory.decodeResource(resources, R.drawable.pawn_w));

        bitmaps.put("QueenB", BitmapFactory.decodeResource(resources, R.drawable.queen_b));
        bitmaps.put("QueenW", BitmapFactory.decodeResource(resources, R.drawable.queen_w));

        bitmaps.put("RookB", BitmapFactory.decodeResource(resources, R.drawable.rook_b));
        bitmaps.put("RookW", BitmapFactory.decodeResource(resources, R.drawable.rook_w));

        bitmaps.put("Board", BitmapFactory.decodeResource(resources, R.drawable.board));
        bitmaps.put("SquareB", BitmapFactory.decodeResource(resources, R.drawable.square_b));
        bitmaps.put("SquareW", BitmapFactory.decodeResource(resources, R.drawable.square_w));
        bitmaps.put("SquareH", BitmapFactory.decodeResource(resources, R.drawable.square_h));
    }

    Bitmap getBitmap(Piece piece) {
        if (piece != null) {
            String name = piece.getClass().getSimpleName();
            name += piece.getColor() == Color.WHITE ? 'W' : "B";

            Bitmap bitmap = bitmaps.get(name);

            if (bitmap != null) {
                return bitmap;
            } else {
                throw new IllegalArgumentException("No bitmap for piece: " + piece);
            }
        } else {
            return BitmapFactory.decodeResource(resources, R.drawable.empty);
        }
    }

    Bitmap getBitmap(String name) {
        Bitmap bitmap = bitmaps.get(name);
        if(bitmap == null) {
            throw new IllegalArgumentException("No bitmap for name: " + name);
        }
        return bitmap;
    }
}
