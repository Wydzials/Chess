package pl.wydzials.chess.engine;

import org.junit.Test;

import pl.wydzials.chess.engine.pieces.Color;
import pl.wydzials.chess.engine.pieces.Pawn;
import pl.wydzials.chess.engine.pieces.Position;
import pl.wydzials.chess.engine.pieces.Rook;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void basicTest() {
        Board board = new Board();
        board.movePiece(new Position(6, 7), new Position(4, 7));
        board.movePiece(new Position(1, 6), new Position(3, 6));
        board.movePiece(new Position(7, 7), new Position(5, 7));
        assertTrue(board.getPiece(new Position(5, 7)) instanceof Rook);

        board.movePiece(new Position(4, 7), new Position(3, 6));
        assertSame(board.getPiece(new Position(3, 6)).getColor(), Color.WHITE);
    }

    @Test
    public void cloneTest() {
        Board a = new Board();
        Board b = a.clone();

        a.movePiece(new Position(1, 0), new Position(2, 0));
        assertTrue(a.getPiece(new Position(2, 0)) instanceof Pawn);

        assertTrue(b.getPiece(new Position(1, 0)) instanceof Pawn);
        assertNull(b.getPiece(new Position(2, 0)));
    }
}