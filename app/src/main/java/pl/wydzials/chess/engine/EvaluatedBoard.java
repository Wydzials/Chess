package pl.wydzials.chess.engine;

public class EvaluatedBoard implements Comparable<EvaluatedBoard> {

    private int evaluation;
    private Board board;

    EvaluatedBoard(int evaluation, Board board) {
        this.evaluation = evaluation;
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    @Override
    public int compareTo(EvaluatedBoard o) {
        return evaluation - o.evaluation;
    }
}
