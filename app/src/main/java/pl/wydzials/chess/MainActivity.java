package pl.wydzials.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;

public class MainActivity extends AppCompatActivity {

    private ImageView[][] squares;
    private PiecesImages piecesImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        squares = new ImageView[8][8];
        piecesImages = new PiecesImages();
        initializeSquares();

        //TableLayout boardLayout = findViewById(R.id.boardLayout);
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
        squares[row][column].setImageResource(piecesImages.getKingW());
        //squares[row][column].setVisibility(View.INVISIBLE);
    }
}
