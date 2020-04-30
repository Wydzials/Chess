package pl.wydzials.chess;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import pl.wydzials.chess.engine.ChessEngine;

public class MainActivity extends AppCompatActivity {

    private static SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.menu_activity);

        preferences = getSharedPreferences("preferences", 0);

        Button onePlayerButton = findViewById(R.id.one_player);
        Button twoPlayersButton = findViewById(R.id.two_players);
        Button preferencesButton = findViewById(R.id.preferences);

        onePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("gameMode", ChessEngine.GameMode.PLAYER_VS_AI);
                startActivity(intent);
            }
        });

        twoPlayersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("gameMode", ChessEngine.GameMode.PLAYER_VS_PLAYER);
                startActivity(intent);
            }
        });

        preferencesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PreferencesActivity.class);
                startActivity(intent);
            }
        });
    }

    public static SharedPreferences getSharedPreferences() {
        return preferences;
    }
}
