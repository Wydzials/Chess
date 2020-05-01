package pl.wydzials.chess.engine;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import pl.wydzials.chess.engine.pieces.Bishop;
import pl.wydzials.chess.engine.pieces.Color;
import pl.wydzials.chess.engine.pieces.King;
import pl.wydzials.chess.engine.pieces.Knight;
import pl.wydzials.chess.engine.pieces.Pawn;
import pl.wydzials.chess.engine.pieces.Piece;
import pl.wydzials.chess.engine.pieces.Position;
import pl.wydzials.chess.engine.pieces.Queen;
import pl.wydzials.chess.engine.pieces.Rook;

public class Board implements Cloneable {

    private Piece[] squares;
    private GameState gameState;

    Board() {
        squares = new Piece[64];
        gameState = GameState.PLAYING;
    }

    void setOpeningPieces() {
        set(0, 0, new Rook(Color.BLACK));
        set(0, 1, new Knight(Color.BLACK));
        set(0, 2, new Bishop(Color.BLACK));
        set(0, 3, new Queen(Color.BLACK));
        set(0, 4, new King(Color.BLACK));
        set(0, 5, new Bishop(Color.BLACK));
        set(0, 6, new Knight(Color.BLACK));
        set(0, 7, new Rook(Color.BLACK));

        set(7, 0, new Rook(Color.WHITE));
        set(7, 1, new Knight(Color.WHITE));
        set(7, 2, new Bishop(Color.WHITE));
        set(7, 3, new Queen(Color.WHITE));
        set(7, 4, new King(Color.WHITE));
        set(7, 5, new Bishop(Color.WHITE));
        set(7, 6, new Knight(Color.WHITE));
        set(7, 7, new Rook(Color.WHITE));

        for (int column = 0; column < 8; column++) {
            set(6, column, new Pawn(Color.WHITE));
            set(1, column, new Pawn(Color.BLACK));
        }
    }

    public Piece getPiece(Position position) {
        return get(position.getRow(), position.getColumn());
    }

    public Piece getPiece(int row, int column) {
        return get(row, column);
    }

    private Piece get(int row, int column) {
        return squares[row * 8 + column];
    }

    private void set(int row, int column, @Nullable Piece piece) {
        squares[row * 8 + column] = piece;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void movePiece(Position posA, Position posB) {
        checkEnPassant(posA, posB);
        checkCastling(posA, posB);

        if (getPiece(posB) instanceof King) {
            gameState = getPiece(posB).getColor() == Color.WHITE ? GameState.BLACK_WON : GameState.WHITE_WON;
        }

        set(posB.getRow(), posB.getColumn(), get(posA.getRow(), posA.getColumn()));
        set(posA.getRow(), posA.getColumn(), null);
        get(posB.getRow(), posB.getColumn()).madeMove(this, posA, posB);

        // promotion
        if (getPiece(posB) instanceof Pawn && (posB.getRow() == 0 || posB.getRow() == 7)) {
            set(posB.getRow(), posB.getColumn(), new Queen(getPiece(posB).getColor()));
        }
    }

    private void checkEnPassant(Position posA, Position posB) {
        if (getPiece(posA) instanceof Pawn && (posA.getColumn() != posB.getColumn()) && getPiece(posB) == null) {
            set(posA.getRow(), posB.getColumn(), null);
        }
        clearEnPassantFlags(getPiece(posA).getColor());
    }

    private void checkCastling(Position posA, Position posB) {
        if (getPiece(posA) instanceof King && Math.abs(posA.getColumn() - posB.getColumn()) > 1) {
            if (posB.getColumn() == 6) {
                set(posA.getRow(), 5, get(posA.getRow(), 7));
                set(posA.getRow(), 7, null);
            } else if (posB.getColumn() == 2) {
                set(posA.getRow(), 3, get(posA.getRow(), 0));
                set(posA.getRow(), 0, null);
            }
        }
    }

    private void clearEnPassantFlags(Color color) {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                Piece piece = getPiece(new Position(row, column));
                if (piece instanceof Pawn && piece.getColor() != color) {
                    ((Pawn) piece).enPassantReady = false;
                }
            }
        }
    }

    public List<Position> getPiecesOfColor(Color color) {
        List<Position> pieces = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                Piece piece = get(row, column);
                if (piece != null && piece.getColor() == color) {
                    pieces.add(new Position(row, column));
                }
            }
        }
        return pieces;
    }

    public enum GameState {
        PLAYING,
        WHITE_WON,
        BLACK_WON
    }

    public Board clone() {
        try {
            Board board = (Board) super.clone();
            board.squares = new Piece[64];

            for (int row = 0; row < 8; row++) {
                for (int column = 0; column < 8; column++) {
                    if (get(row, column) != null)
                        board.set(row, column, (Piece) get(row, column).clone());
                }
            }

            return board;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}