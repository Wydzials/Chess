package pl.wydzials.chess;

import pl.wydzials.chess.pieces.*;

public class Board {
    private Piece[][] squares;

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

        for(int column = 0; column < 8; column++) {
            squares[6][column] = new Pawn(Color.WHITE);
            squares[1][column] = new Pawn(Color.BLACK);
        }
    }

    Piece getPiece(int row, int column) {
        return squares[row][column];
    }

    void movePiece(int rowA, int columnA, int rowB, int columnB) {
        squares[rowB][columnB] = squares[rowA][columnA];
        squares[rowA][columnA] = null;
    }
}
