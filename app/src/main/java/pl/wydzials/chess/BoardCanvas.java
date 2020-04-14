package pl.wydzials.chess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;


import pl.wydzials.chess.engine.ChessEngine;
import pl.wydzials.chess.engine.pieces.Piece;

public class BoardCanvas extends View {

    private final int PADDING = 30;
    private int pieceSize;

    Bitmaps bitmaps;
    ChessEngine engine;
    TextView textView;
    Rect[][] squares;

    public BoardCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmaps = new Bitmaps(getResources());
    }

    private void initializeRectangles() {
        pieceSize = (getWidth() - 2 * PADDING) / 8;
        squares = new Rect[8][8];

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                squares[row][column] = new Rect(PADDING + column * pieceSize, PADDING + row * pieceSize,
                        PADDING + (column + 1) * pieceSize, PADDING + (row + 1) * pieceSize);
            }
        }
    }

    void setChessEngine(ChessEngine engine) {
        this.engine = engine;
    }

    void setTextView(TextView textView) {
        this.textView = textView;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (squares == null) {
            initializeRectangles();
        }
        canvas.drawBitmap(bitmaps.getBoard(), null, new Rect(0, 0, getWidth(), getWidth()), null);

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                Piece piece = engine.getBoard().getPiece(row, column);
                Bitmap pieceBitmap = bitmaps.getPiece(piece);
                canvas.drawBitmap(pieceBitmap, null, squares[row][column], null);
            }
        }
        textView.setText(engine.getState().toString());
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            int rowClicked = (int) (((ev.getY() - PADDING) - (ev.getY() - PADDING) % pieceSize) / pieceSize);
            int columnClicked = (int) (((ev.getX() - PADDING) - (ev.getX() - PADDING) % pieceSize) / pieceSize);

            System.out.println(rowClicked + " " + columnClicked);
            if (rowClicked < 8 && columnClicked < 8) {
                engine.boardClicked(rowClicked, columnClicked);
                invalidate();
            }
        }
        return true;
    }
}
