package pl.wydzials.chess;

import pl.wydzials.chess.pieces.Color;
import pl.wydzials.chess.pieces.Piece;

public class ChessEngine {

    private State state;
    private Board board;

    private int previousRow;
    private int previousColumn;

    ChessEngine() {
        board = new Board();
        state = State.NEXT_WHITE;
    }

    public State getState() {
        return state;
    }

    Board getBoard() {
        return board;
    }

    void boardClicked(int row, int column) {
        Piece piece = board.getPiece(row, column);
        if (state == State.NEXT_WHITE && piece != null && piece.getColor() == Color.WHITE) {
            state = State.MOVING_WHITE;
            previousRow = row;
            previousColumn = column;
        } else if (state == State.NEXT_BLACK && piece != null && piece.getColor() == Color.BLACK) {
            state = State.MOVING_BLACK;
            previousRow = row;
            previousColumn = column;
        } else if(state == State.MOVING_WHITE && (piece == null || piece.getColor() != Color.WHITE)) {
            state = State.NEXT_BLACK;
            board.movePiece(previousRow, previousColumn, row, column);
        } else if(state == State.MOVING_BLACK && (piece == null ||piece.getColor() != Color.BLACK)) {
            state = State.NEXT_WHITE;
            board.movePiece(previousRow, previousColumn, row, column);
        }
    }

    public enum State {
        NEXT_WHITE,
        MOVING_WHITE,
        NEXT_BLACK,
        MOVING_BLACK;

        public State next() {
            return values()[(ordinal() + 1) % values().length];
        }
    }

}
