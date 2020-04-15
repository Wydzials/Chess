package pl.wydzials.chess.engine;

import java.util.ArrayList;
import java.util.List;

import pl.wydzials.chess.engine.pieces.Color;
import pl.wydzials.chess.engine.pieces.Piece;
import pl.wydzials.chess.engine.pieces.Position;

public class ChessEngine {

    private MoveState state;
    private Board board;
    private Position previousPosition;
    private List<Position> highlightedSquares;

    public ChessEngine() {
        board = new Board();
        state = MoveState.NEXT_WHITE;
        highlightedSquares = new ArrayList<>();
    }

    public List<Position> getHighlightedSquares() {
        return highlightedSquares;
    }

    public MoveState getState() {
        return state;
    }

    public Board getBoard() {
        return board;
    }

    public void boardClicked(int row, int column) {
        Position position = new Position(row, column);
        Piece piece = board.getPiece(position);

        if (piece != null && ((state == MoveState.NEXT_WHITE && piece.getColor() == Color.WHITE)
                || (state == MoveState.NEXT_BLACK && piece.getColor() == Color.BLACK))) {
            state = state.next();
            highlightedSquares = piece.getPossibleMoves(board, position);
            previousPosition = new Position(row, column);
        } else if ((state == MoveState.MOVING_WHITE || state == MoveState.MOVING_BLACK)) {
            if (highlightedSquares.contains(position)) {
                state = state.next();
                board.movePiece(previousPosition, position);
                highlightedSquares.clear();
            } else if (piece != null && piece.getColor() == board.getPiece(previousPosition).getColor()) {
                highlightedSquares = piece.getPossibleMoves(board, position);
                previousPosition = new Position(row, column);
            } else {
                state = state.previous();
                highlightedSquares.clear();
            }
        }
    }

    public enum MoveState {
        NEXT_WHITE,
        MOVING_WHITE,
        NEXT_BLACK,
        MOVING_BLACK;

        public MoveState next() {
            return values()[(ordinal() + 1) % values().length];
        }

        public MoveState previous() {
            return values()[(ordinal() + values().length - 1) % values().length];
        }
    }

}
