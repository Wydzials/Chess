package pl.wydzials.chess.engine.pieces;

import java.util.ArrayList;
import java.util.List;

import pl.wydzials.chess.engine.Board;

public class King extends Piece {

    public King(Color color) {
        super(color);
    }

    @Override
    public List<Position> getPossibleMoves(Board board, Position myPosition) {
        List<Position> moves = new ArrayList<>();
        for(int row = -1; row <= 1; row++) {
            for(int column = -1; column <= 1; column++) {
                if(myPosition.canAdd(row, column)) {
                    Position testPosition = myPosition.add(row, column);
                    if(board.getPiece(testPosition) == null || board.getPiece((testPosition)).getColor() != color) {
                        moves.add(testPosition);
                    }
                }
            }
        }
        return moves;
    }
}
