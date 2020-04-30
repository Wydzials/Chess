package pl.wydzials.chess.engine.ai;

import pl.wydzials.chess.engine.Board;

public class EvaluatedBoard implements Comparable<EvaluatedBoard> {

    private double evaluation;
    private Board board;

    EvaluatedBoard(double evaluation, Board board) {
        this.evaluation = evaluation;
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    @Override
    public int compareTo(EvaluatedBoard o) {
        return Double.compare(evaluation, o.evaluation);
    }
}
