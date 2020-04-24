package pl.wydzials.chess;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import pl.wydzials.chess.engine.ChessEngine;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.menu_activity);

        Button onePlayerButton = findViewById(R.id.one_player);
        Button twoPlayersButton = findViewById(R.id.two_players);

        onePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("gameType", ChessEngine.GameType.PLAYER_VS_AI);
                startActivity(intent);
            }
        });

        twoPlayersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("gameType", ChessEngine.GameType.PLAYER_VS_PLAYER);
                startActivity(intent);
            }
        });
    }
}
