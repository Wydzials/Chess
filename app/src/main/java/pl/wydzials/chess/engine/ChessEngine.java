package pl.wydzials.chess.engine;

import java.util.ArrayList;
import java.util.List;

import pl.wydzials.chess.activities.MainActivity;
import pl.wydzials.chess.engine.ai.AI;
import pl.wydzials.chess.engine.pieces.Color;
import pl.wydzials.chess.engine.pieces.Piece;
import pl.wydzials.chess.engine.pieces.Position;

public class ChessEngine {

    private MoveState state;
    private GameMode gameMode;

    private Board board;
    private Piece piece;

    private List<Position> possibleMoves;
    private Position previousPosition;

    public ChessEngine(GameMode gameMode) {
        board = new Board();
        board.setOpeningPieces();
        state = MoveState.WHITE_TURN;
        possibleMoves = new ArrayList<>();
        this.gameMode = gameMode;
    }

    public List<Position> getPossibleMoves() {
        return possibleMoves;
    }

    public MoveState getState() {
        return state;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public Board getBoard() {
        return board;
    }

    public void boardClicked(int row, int column) {
        Position position = new Position(row, column);
        piece = board.getPiece(position);

        if (startedMoving()) {
            state = state.next();
            possibleMoves = piece.getPossibleMoves(board, position);
            previousPosition = position;
        } else if (state.isMoving()) {
            if (possibleMoves.contains(position)) {
                state = state.next();
                board.movePiece(previousPosition, position);
                possibleMoves.clear();

                if (nextAIMove()) {
                    Position[] move = AI.makeMove(board, Color.BLACK, MainActivity.getSharedPreferences().getInt("aiDepth", 4));
                    boardClicked(move[0].getRow(), move[0].getColumn());
                    boardClicked(move[1].getRow(), move[1].getColumn());
                }

            } else if (clickedOtherAllyPiece()) {
                possibleMoves = piece.getPossibleMoves(board, position);
                previousPosition = position;
            } else {
                state = state.previous();
                possibleMoves.clear();
            }
        }
    }

    private boolean startedMoving() {
        return piece != null && ((state == MoveState.WHITE_TURN && piece.getColor() == Color.WHITE)
                || (state == MoveState.BLACK_TURN && piece.getColor() == Color.BLACK));
    }

    private boolean nextAIMove() {
        return (state == MoveState.BLACK_TURN && gameMode == GameMode.PLAYER_VS_AI && board.getGameState() == Board.GameState.PLAYING);
    }

    private boolean clickedOtherAllyPiece() {
        return (piece != null && piece.getColor() == board.getPiece(previousPosition).getColor());
    }

    public enum MoveState {
        WHITE_TURN,
        MOVING_WHITE,
        BLACK_TURN,
        MOVING_BLACK;

        public MoveState next() {
            return values()[(ordinal() + 1) % values().length];
        }

        public MoveState previous() {
            return values()[(ordinal() + values().length - 1) % values().length];
        }

        public boolean isMoving() {
            return (this == MoveState.MOVING_WHITE || this == MoveState.MOVING_BLACK);
        }
    }

    public enum GameMode {
        PLAYER_VS_PLAYER,
        PLAYER_VS_AI
    }
}
