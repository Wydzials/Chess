package pl.wydzials.chess.engine;

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
    private Piece[][] squares;

    private GameState gameState;

    Board() {
        squares = new Piece[8][8];

        squares[0][0] = new Rook(Color.BLACK);
        squares[0][1] = new Knight(Color.BLACK);
        squares[0][2] = new Bishop(Color.BLACK);
        squares[0][3] = new Queen(Color.BLACK);
        squares[0][4] = new King(Color.BLACK);
        squares[0][5] = new Bishop(Color.BLACK);
        squares[0][6] = new Knight(Color.BLACK);
        squares[0][7] = new Rook(Color.BLACK);

        squares[7][0] = new Rook(Color.WHITE);
        squares[7][1] = new Knight(Color.WHITE);
        squares[7][2] = new Bishop(Color.WHITE);
        squares[7][3] = new Queen(Color.WHITE);
        squares[7][4] = new King(Color.WHITE);
        squares[7][5] = new Bishop(Color.WHITE);
        squares[7][6] = new Knight(Color.WHITE);
        squares[7][7] = new Rook(Color.WHITE);

        for (int column = 0; column < 8; column++) {
            squares[6][column] = new Pawn(Color.WHITE);
            squares[1][column] = new Pawn(Color.BLACK);
        }
        gameState = GameState.PLAYING;
    }

    public Piece getPiece(Position position) {
        return squares[position.getRow()][position.getColumn()];
    }

    public GameState getGameState() {
        return gameState;
    }

    void movePiece(Position posA, Position posB) {
        // en passant
        if (getPiece(posA) instanceof Pawn && (posA.getColumn() != posB.getColumn()) && getPiece(posB) == null) {
            squares[posA.getRow()][posB.getColumn()] = null;
        }
        clearEnPassantFlags(getPiece(posA).getColor());

        // castling
        if (getPiece(posA) instanceof King && Math.abs(posA.getColumn() - posB.getColumn()) > 1) {
            if (posB.getColumn() == 6) {
                squares[posA.getRow()][5] = squares[posA.getRow()][7];
                squares[posA.getRow()][7] = null;
            } else if (posB.getColumn() == 2) {
                squares[posA.getRow()][3] = squares[posA.getRow()][0];
                squares[posA.getRow()][0] = null;
            }
        }

        // win check
        if (getPiece(posB) instanceof King) {
            gameState = getPiece(posB).getColor() == Color.WHITE ? GameState.BLACK_WON : GameState.WHITE_WON;
        }

        squares[posB.getRow()][posB.getColumn()] = squares[posA.getRow()][posA.getColumn()];
        squares[posA.getRow()][posA.getColumn()] = null;
        squares[posB.getRow()][posB.getColumn()].madeMove(this, posA, posB);

        // promotion
        if (getPiece(posB) instanceof Pawn && (posB.getRow() == 0 || posB.getRow() == 7)) {
            squares[posB.getRow()][posB.getColumn()] = new Queen(getPiece(posB).getColor());
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
                Piece piece = squares[row][column];
                if (piece != null && piece.getColor() == color) {
                    pieces.add(new Position(row, column));
                }
            }
        }
        return pieces;
    }

    public enum GameState {
        PLAYING,
        DRAW,
        WHITE_WON,
        BLACK_WON
    }

    public Board clone() {
        try {
            Board board = (Board) super.clone();
            board.squares = new Piece[8][8];

            for (int row = 0; row < 8; row++) {
                for (int column = 0; column < 8; column++) {
                    if(squares[row][column] != null)
                        board.squares[row][column] = (Piece) squares[row][column].clone();
                }
            }

            return board;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}