package pl.wydzials.chess.engine;

import java.util.ArrayList;
import java.util.Collections;
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

    private final static int DEPTH = 4;
    private static Color maximizingColor;
    private static int minimaxCalls;

    private static PieceEvaluator pieceEvaluator = new PieceEvaluator();

    public static Position[] makeMove(Board board, Color color) {
        long start = System.nanoTime();
        minimaxCalls = 0;
        maximizingColor = color;
        List<Position> myPieces = board.getPiecesOfColor(color);

        double bestMoveValue = Integer.MIN_VALUE;
        Position bestMoveA = null;
        Position bestMoveB = null;

        for (Position piece : myPieces) {
            List<Position> moves = board.getPiece(piece).getPossibleMoves(board, piece);
            for (Position move : moves) {
                Board childBoard = board.clone();
                childBoard.movePiece(piece, move);

                double value = minimax(childBoard, DEPTH - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, color.other());
                if (value > bestMoveValue) {
                    bestMoveA = piece;
                    bestMoveB = move;
                    bestMoveValue = value;
                }
            }
        }
        System.out.println("Minimax calls: " + minimaxCalls);
        System.out.println("Time: " + (System.nanoTime() - start) / 1_000_000);
        return new Position[]{bestMoveA, bestMoveB};
    }

    private static double minimax(Board board, double depth, double alpha, double beta, Color color) {
        minimaxCalls++;
        if (depth <= 0 || board.getGameState() != Board.GameState.PLAYING) {
            return evaluateBoard(board);
        }

        List<Position> myPieces = board.getPiecesOfColor(color);

        if (color == maximizingColor) {
            double maxEvaluation = Integer.MIN_VALUE;

            ArrayList<EvaluatedBoard> allPossibleMoves = new ArrayList<>();
            for (Position piece : myPieces) {
                List<Position> moves = board.getPiece(piece).getPossibleMoves(board, piece);
                for (Position move : moves) {
                    Board childBoard = board.clone();
                    childBoard.movePiece(piece, move);
                    int evaluation = -evaluateBoard(childBoard);
                    allPossibleMoves.add(new EvaluatedBoard(evaluation, childBoard));
                }
            }
            Collections.sort(allPossibleMoves);

            for (EvaluatedBoard evaluatedBoard : allPossibleMoves) {
                double evaluation = minimax(evaluatedBoard.getBoard(), depth - 1, alpha, beta, color.other());
                maxEvaluation = Math.max(evaluation, maxEvaluation);

                alpha = Math.max(evaluation, alpha);
                if (beta <= alpha) {
                    break;
                }
            }

            return maxEvaluation;
        } else {
            double minEvaluation = Integer.MAX_VALUE;

            ArrayList<EvaluatedBoard> allPossibleMoves = new ArrayList<>();
            for (Position piece : myPieces) {
                List<Position> moves = board.getPiece(piece).getPossibleMoves(board, piece);
                for (Position move : moves) {
                    Board childBoard = board.clone();
                    childBoard.movePiece(piece, move);
                    int evaluation = evaluateBoard(childBoard);
                    allPossibleMoves.add(new EvaluatedBoard(evaluation, childBoard));
                }
            }
            Collections.sort(allPossibleMoves);

            for (EvaluatedBoard evaluatedBoard : allPossibleMoves) {
                double evaluation = minimax(evaluatedBoard.getBoard(), depth - 1, alpha, beta, color.other());
                minEvaluation = Math.min(evaluation, minEvaluation);

                beta = Math.min(evaluation, beta);
                if (beta <= alpha) {
                    break;
                }
            }

            return minEvaluation;
        }
    }

    private static int evaluateBoard(Board board) {
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

    private static double evaluatePiece(Piece piece, int row, int column) {
        if (piece instanceof Pawn) {
            return pieceEvaluator.evaluate(piece, row, column);
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
