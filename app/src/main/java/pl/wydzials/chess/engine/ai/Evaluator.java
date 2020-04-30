package pl.wydzials.chess.engine.ai;

import pl.wydzials.chess.engine.Board;
import pl.wydzials.chess.engine.pieces.Bishop;
import pl.wydzials.chess.engine.pieces.Color;
import pl.wydzials.chess.engine.pieces.King;
import pl.wydzials.chess.engine.pieces.Knight;
import pl.wydzials.chess.engine.pieces.Pawn;
import pl.wydzials.chess.engine.pieces.Piece;
import pl.wydzials.chess.engine.pieces.Queen;
import pl.wydzials.chess.engine.pieces.Rook;

class Evaluator {

    private static double[][] pawnWhite;
    private static double[][] pawnBlack;

    private static double[][] knight;

    private static double[][] bishopWhite;
    private static double[][] bishopBlack;

    private static double[][] rookWhite;
    private static double[][] rookBlack;

    private static double[][] queen;

    Evaluator() {
        pawnWhite = new double[][]{
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {5.0, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0},
                {1.0, 1.0, 2.0, 3.0, 3.0, 2.0, 1.0, 1.0},
                {0.5, 0.5, 1.0, 2.5, 2.5, 1.0, 0.5, 0.5},
                {0.0, 0.0, 0.0, 2.0, 2.0, 0.0, 0.0, 0.0},
                {0.5, -0.5, -1.0, 0.0, 0.0, -1.0, -0.5, 0.5},
                {0.5, 1.0, 1.0, -2.0, -2.0, 1.0, 1.0, 0.5},
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}};

        pawnBlack = getReversedArray(pawnWhite);

        knight = new double[][]{
                {-5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0},
                {-4.0, -2.0, 0.0, 0.0, 0.0, 0.0, -2.0, -4.0},
                {-3.0, 0.0, 1.0, 1.5, 1.5, 1.0, 0.0, -3.0},
                {-3.0, 0.5, 1.5, 2.0, 2.0, 1.5, 0.5, -3.0},
                {-3.0, 0.0, 1.5, 2.0, 2.0, 1.5, 0.0, -3.0},
                {-3.0, 0.5, 1.0, 1.5, 1.5, 1.0, 0.5, -3.0},
                {-4.0, -2.0, 0.0, 0.5, 0.5, 0.0, -2.0, -4.0},
                {-5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0}};

        bishopWhite = new double[][]{
                {-2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0},
                {-1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -1.0},
                {-1.0, 0.0, 0.5, 1.0, 1.0, 0.5, 0.0, -1.0},
                {-1.0, 0.5, 0.5, 1.0, 1.0, 0.5, 0.5, -1.0},
                {-1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, -1.0},
                {-1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, -1.0},
                {-1.0, 0.5, 0.0, 0.0, 0.0, 0.0, 0.5, -1.0},
                {-2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0}};

        bishopBlack = getReversedArray(bishopWhite);

        rookWhite = new double[][]{
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {0.5, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.5},
                {-0.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.5},
                {-0.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.5},
                {-0.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.5},
                {-0.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.5},
                {-0.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.5},
                {0.0, 0.0, 0.0, 0.5, 0.5, 0.0, 0.0, 0.0}};

        rookBlack = getReversedArray(rookWhite);

        queen = new double[][]{
                {-2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0},
                {-1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -1.0},
                {-1.0, 0.0, 0.5, 0.5, 0.5, 0.5, 0.0, -1.0},
                {-0.5, 0.0, 0.5, 0.5, 0.5, 0.5, 0.0, -0.5},
                {0.0, 0.0, 0.5, 0.5, 0.5, 0.5, 0.0, -0.5},
                {-1.0, 0.5, 0.5, 0.5, 0.5, 0.5, 0.0, -1.0},
                {-1.0, 0.0, 0.5, 0.0, 0.0, 0.0, 0.0, -1.0},
                {-2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0}};
    }


    double evaluateBoard(Board board, Color maximizingColor) {
        int value = 0;
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                Piece piece = board.getPiece(row, column);
                if (piece != null) {
                    if (piece.getColor() == maximizingColor) {
                        value += evaluatePiece(piece, row, column);
                    } else {
                        value -= evaluatePiece(piece, row, column);
                    }
                }
            }
        }
        return value;
    }

    private double evaluatePiece(Piece piece, int row, int column) {
        if (piece instanceof Pawn) {
            return 10 + (piece.getColor() == Color.WHITE ? pawnWhite[row][column] : pawnBlack[row][column]);
        } else if (piece instanceof Knight) {
            return 30 + knight[row][column];
        } else if (piece instanceof Bishop) {
            return 30 + (piece.getColor() == Color.WHITE ? bishopWhite[row][column] : bishopBlack[row][column]);
        } else if (piece instanceof Rook) {
            return 50 + (piece.getColor() == Color.WHITE ? rookWhite[row][column] : rookBlack[row][column]);
        } else if (piece instanceof Queen) {
            return 90 + queen[row][column];
        } else if (piece instanceof King) {
            return 900;
        }
        throw new IllegalArgumentException("Unknown type of piece: " + piece.getClass().getName());
    }

    private double[][] getReversedArray(double[][] array) {
        int n = array.length;
        double[][] newArray = new double[n][array[0].length];
        for (int row = 0; row < n; row++) {
            newArray[n - row - 1] = array[row];
        }
        return newArray;
    }
}
