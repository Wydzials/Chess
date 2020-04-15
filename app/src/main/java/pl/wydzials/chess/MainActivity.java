package pl.wydzials.chess;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import pl.wydzials.chess.engine.ChessEngine;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        BoardCanvas canvas = findViewById(R.id.canvas);
        canvas.setChessEngine(new ChessEngine());

        TextView textView = findViewById(R.id.textView);
        canvas.setTextView(textView);
    }
}
