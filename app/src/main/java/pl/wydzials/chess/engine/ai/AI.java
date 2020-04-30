package pl.wydzials.chess.engine.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.wydzials.chess.engine.Board;
import pl.wydzials.chess.engine.pieces.Color;
import pl.wydzials.chess.engine.pieces.Position;

public class AI {

    private final static int DEPTH = 4;
    private static Color maximizingColor;
    private static int minimaxCalls;

    private static Evaluator evaluator = new Evaluator();

    public static Position[] makeMove(Board board, Color color) {
        long startTime = System.nanoTime();
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
        System.out.println("Time: " + (System.nanoTime() - startTime) / 1_000_000);
        return new Position[]{bestMoveA, bestMoveB};
    }

    private static double minimax(Board board, double depth, double alpha, double beta, Color color) {
        minimaxCalls++;
        if (depth <= 0 || board.getGameState() != Board.GameState.PLAYING) {
            return evaluator.evaluateBoard(board, maximizingColor);
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
                    double evaluation = -evaluator.evaluateBoard(childBoard, maximizingColor);
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
                    double evaluation = evaluator.evaluateBoard(childBoard, maximizingColor);
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

}
