package pl.wydzials.chess.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import pl.wydzials.chess.R;
import pl.wydzials.chess.engine.ChessEngine;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.game_activity);

        BoardCanvas canvas = findViewById(R.id.canvas);
        TextView textView = findViewById(R.id.textView);
        canvas.setTextView(textView);

        ChessEngine.GameMode gameMode = (ChessEngine.GameMode) getIntent().getSerializableExtra("gameMode");
        if (gameMode != null) {
            if (gameMode == ChessEngine.GameMode.PLAYER_VS_AI) {
                canvas.setChessEngine(new ChessEngine(ChessEngine.GameMode.PLAYER_VS_AI));
            } else {
                canvas.setChessEngine(new ChessEngine(ChessEngine.GameMode.PLAYER_VS_PLAYER));
            }
        }
    }
}
