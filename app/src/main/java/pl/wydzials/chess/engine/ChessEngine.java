package pl.wydzials.chess.engine;

import java.util.ArrayList;
import java.util.List;

import pl.wydzials.chess.engine.pieces.Color;
import pl.wydzials.chess.engine.pieces.Piece;
import pl.wydzials.chess.engine.pieces.Position;

public class ChessEngine {

    private State state;
    private Board board;

    private Position previousPosition;

    private List<Position> highlightedSquares;

    public List<Position> getHighlightedSquares() {
        return highlightedSquares;
    }

    public ChessEngine() {
        board = new Board();
        state = State.NEXT_WHITE;
        highlightedSquares = new ArrayList<>();
    }

    public State getState() {
        return state;
    }

    public Board getBoard() {
        return board;
    }

    public void boardClicked(int row, int column) {
        Position position = new Position(row, column);
        Piece piece = board.getPiece(position);

        if (piece != null && ((state == State.NEXT_WHITE && piece.getColor() == Color.WHITE)
                || (state == State.NEXT_BLACK && piece.getColor() == Color.BLACK))) {
            state = state.next();
            highlightedSquares = piece.getPossibleMoves(board, position);
            previousPosition = new Position(row, column);
        } else if ((state == State.MOVING_WHITE || state == State.MOVING_BLACK)
                && highlightedSquares.contains(position)) {
            state = state.next();
            board.movePiece(previousPosition, position);
            highlightedSquares.clear();
        } else if (state == State.MOVING_WHITE || state == State.MOVING_BLACK) {
            state = state.previous();
            highlightedSquares.clear();
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

        public State previous() {
            return values()[(ordinal() + values().length - 1) % values().length];
        }
    }

}
