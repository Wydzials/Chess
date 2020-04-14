package pl.wydzials.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import pl.wydzials.chess.pieces.Bishop;
import pl.wydzials.chess.pieces.Color;
import pl.wydzials.chess.pieces.Piece;

public class MainActivity extends AppCompatActivity {

    private ImageView[][] squares;
    private PieceImages images;
    private ChessEngine engine;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);

        squares = new ImageView[8][8];
        images = new PieceImages();
        initializeSquares();

        engine = new ChessEngine();
        showBoard(engine.getBoard());
        textView.setText(engine.getState().toString());
    }

    private void initializeSquares() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView square = findViewById(v.getId());
                String id = getResources().getResourceEntryName(square.getId());
                id = id.substring(id.length()-2);

                int row = Character.getNumericValue(id.charAt(0));
                int column = Character.getNumericValue(id.charAt(1));

                squareOnClick(square, row, column);
                showBoard(engine.getBoard());
                textView.setText(engine.getState().toString());
            }
        };

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                String name = "square" + row + column;
                int id = getResources().getIdentifier(name, "id", getPackageName());
                squares[row][column] = findViewById(id);
                squares[row][column].setOnClickListener(listener);
            }
        }
    }

    private void squareOnClick(ImageView square, int row, int column) {
        engine.boardClicked(row, column);
    }

    private void showBoard(Board board) {
        for(int row = 0; row < 8; row++) {
            for(int column = 0; column < 8; column++) {
                Piece piece = board.getPiece(row, column);
                int image = images.getPieceImage(piece);
                squares[row][column].setImageResource(image);
            }
        }
    }
}
