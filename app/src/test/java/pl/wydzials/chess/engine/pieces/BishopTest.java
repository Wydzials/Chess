package pl.wydzials.chess.engine.pieces;

import org.junit.Test;

import static org.junit.Assert.*;

public class BishopTest {

    @Test
    public void cloneTest() {
        Bishop a = new Bishop(Color.WHITE);
        Bishop b = (Bishop) a.clone();

        a.color = Color.BLACK;
        assertSame(b.color, Color.WHITE);
    }
}