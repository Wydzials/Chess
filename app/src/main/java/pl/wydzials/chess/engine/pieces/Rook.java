package pl.wydzials.chess.engine.pieces;

import java.util.ArrayList;
import java.util.List;

import pl.wydzials.chess.engine.Board;

public class Rook extends Piece {

    public Rook(Color color) {
        super(color);
    }

    @Override
    public List<Position> getPossibleMoves(Board board, Position myPosition) {
        List<Position> moves = new ArrayList<>();

        int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

        for (int[] direction : directions) {
            int row = direction[0];
            int column = direction[1];

            while (true) {
                if (myPosition.canAdd(row, column)) {
                    Position testPosition = myPosition.add(row, column);
                    if (board.getPiece(testPosition) == null) {
                        moves.add(testPosition);
                    } else if (board.getPiece(testPosition).getColor() != color) {
                        moves.add(testPosition);
                        break;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
                row += direction[0];
                column += direction[1];
            }
        }
        return moves;
    }
}
