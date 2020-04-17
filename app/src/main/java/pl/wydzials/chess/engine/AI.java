package pl.wydzials.chess.engine;

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

public class AI {

    private final static int DEPTH = 2;
    private static Color maximizingColor;
    private static int minimaxCalls;

    public static Position[] makeMove(Board board, Color color) {
        minimaxCalls = 0;
        maximizingColor = color;
        List<Position> myPieces = board.getPiecesOfColor(color);

        int bestMoveValue = Integer.MIN_VALUE;
        Position bestMoveA = null;
        Position bestMoveB = null;

        for (Position piece : myPieces) {
            List<Position> moves = board.getPiece(piece).getPossibleMoves(board, piece);
            for (Position move : moves) {
                Board childBoard = board.clone();
                childBoard.movePiece(piece, move);

                int value = minimax(childBoard, DEPTH, color.other());
                if (value > bestMoveValue) {
                    bestMoveA = piece;
                    bestMoveB = move;
                    bestMoveValue = value;
                }
            }
        }
        System.out.println("Minimax calls: " + minimaxCalls);
        return new Position[]{bestMoveA, bestMoveB};
    }

    private static int minimax(Board board, int depth, Color color) {
        minimaxCalls++;
        if (depth == 0 || board.getGameState() != Board.GameState.PLAYING) {
            return evaluateBoard(board);
        }

        List<Position> myPieces = board.getPiecesOfColor(color);

        if (color == maximizingColor) {
            int maxEvaluation = Integer.MIN_VALUE;

            for (Position piece : myPieces) {
                List<Position> moves = board.getPiece(piece).getPossibleMoves(board, piece);
                for (Position move : moves) {
                    Board childBoard = board.clone();
                    childBoard.movePiece(piece, move);

                    int evaluation = minimax(childBoard, depth - 1, color.other());
                    maxEvaluation = Math.max(evaluation, maxEvaluation);
                }
            }
            return maxEvaluation;
        } else {
            int minEvaluation = Integer.MAX_VALUE;

            for (Position piece : myPieces) {
                List<Position> moves = board.getPiece(piece).getPossibleMoves(board, piece);
                for (Position move : moves) {
                    Board childBoard = board.clone();
                    childBoard.movePiece(piece, move);

                    int evaluation = minimax(childBoard, depth - 1, color.other());
                    minEvaluation = Math.min(evaluation, minEvaluation);
                }
            }
            return minEvaluation;
        }
    }

    private static int evaluateBoard(Board board) {
        List<Position> whitePieces = board.getPiecesOfColor(maximizingColor);
        List<Position> blackPieces = board.getPiecesOfColor(maximizingColor.other());

        int value = 0;

        for (Position position : whitePieces) {
            value += evaluatePiece(board.getPiece(position));
        }
        for (Position position : blackPieces) {
            value -= evaluatePiece(board.getPiece(position));
        }

        return value;
    }

    private static int evaluatePiece(Piece piece) {
        if (piece instanceof Pawn) {
            return 10;
        } else if (piece instanceof Knight) {
            return 30;
        } else if (piece instanceof Bishop) {
            return 30;
        } else if (piece instanceof Rook) {
            return 50;
        } else if (piece instanceof Queen) {
            return 90;
        } else if (piece instanceof King) {
            return 900;
        }
        return 0;
    }
}
