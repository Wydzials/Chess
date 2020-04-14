package pl.wydzials.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

import pl.wydzials.chess.engine.ChessEngine;

public class MainActivity extends AppCompatActivity {

    private ChessEngine engine;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        BoardCanvas canvas = findViewById(R.id.canvas);

        engine = new ChessEngine();
        textView.setText(engine.getState().toString());

        canvas.setChessEngine(engine);
        canvas.setTextView(textView);
    }
}
