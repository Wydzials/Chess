package pl.wydzials.chess;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

        ChessEngine.GameType gameType = (ChessEngine.GameType) getIntent().getSerializableExtra("gameType");
        if (gameType != null) {
            if (gameType == ChessEngine.GameType.PLAYER_VS_AI) {
                canvas.setChessEngine(new ChessEngine(ChessEngine.GameType.PLAYER_VS_AI));
            } else {
                canvas.setChessEngine(new ChessEngine(ChessEngine.GameType.PLAYER_VS_PLAYER));
            }
        }
    }
}
