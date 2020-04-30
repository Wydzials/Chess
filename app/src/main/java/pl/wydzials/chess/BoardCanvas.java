package pl.wydzials.chess;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import pl.wydzials.chess.engine.Board;
import pl.wydzials.chess.engine.ChessEngine;
import pl.wydzials.chess.engine.pieces.Color;
import pl.wydzials.chess.engine.pieces.Piece;
import pl.wydzials.chess.engine.pieces.Position;

public class BoardCanvas extends View {

    private final int PADDING = 30;
    private int squareSize;

    private Bitmaps bitmaps;
    private ChessEngine engine;
    private TextView textView;
    private Rect[][] squares;
    private Rect board;

    public BoardCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmaps = new Bitmaps(getResources());
    }

    void setChessEngine(ChessEngine engine) {
        this.engine = engine;
    }

    void setTextView(TextView textView) {
        this.textView = textView;
    }

    private void initializeSquares() {
        squares = new Rect[8][8];
        squareSize = (getWidth() - 2 * PADDING) / 8;

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                squares[row][column] = new Rect(PADDING + column * squareSize, PADDING + row * squareSize,
                        PADDING + (column + 1) * squareSize, PADDING + (row + 1) * squareSize);
            }
        }
        board = new Rect(0, 0, getWidth(), getWidth());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (squares == null) {
            initializeSquares();
        }

        canvas.drawBitmap(bitmaps.getBitmap("Board"), null, board, null);
        drawSquaresAndPieces(canvas);

        for (Position position : engine.getPossibleMoves()) {
            canvas.drawBitmap(bitmaps.getBitmap("SquareH"), null, squares[position.getRow()][position.getColumn()], null);
        }

        textView.setText(String.format("%s, %s", engine.getState(), engine.getBoard().getGameState()));
    }

    private void drawSquaresAndPieces(Canvas canvas) {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                Bitmap square = (row + column) % 2 == 0 ? bitmaps.getBitmap("SquareW") : bitmaps.getBitmap("SquareB");

                canvas.drawBitmap(square, null, squares[row][column], null);

                Piece piece = engine.getBoard().getPiece(row, column);

                Bitmap pieceBitmap;
                SharedPreferences preferences = MainActivity.getSharedPreferences();
                if (engine.getGameMode() == ChessEngine.GameMode.PLAYER_VS_PLAYER && piece != null
                        && piece.getColor() == Color.BLACK && preferences.getBoolean("rotateBlackPieces", false)) {
                    pieceBitmap = bitmaps.getBitmap(piece, 180);
                } else {
                    pieceBitmap = bitmaps.getBitmap(piece);
                }
                canvas.drawBitmap(pieceBitmap, null, squares[row][column], null);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (waitingForAI() || gameOver()) {
                return true;
            }

            final int rowClicked = (int) (((ev.getY() - PADDING) - (ev.getY() - PADDING) % squareSize) / squareSize);
            final int columnClicked = (int) (((ev.getX() - PADDING) - (ev.getX() - PADDING) % squareSize) / squareSize);

            if (rowClicked < 8 && columnClicked < 8) {
                new Thread(new Runnable() {
                    public void run() {
                        engine.boardClicked(rowClicked, columnClicked);
                        invalidate();
                    }
                }).start();
                invalidate();
            }
        }
        return true;
    }

    private boolean gameOver() {
        return engine.getBoard().getGameState() != Board.GameState.PLAYING;
    }

    private boolean waitingForAI() {
        return engine.getGameMode() == ChessEngine.GameMode.PLAYER_VS_AI
                && engine.getState() == ChessEngine.MoveState.NEXT_BLACK;
    }
}
