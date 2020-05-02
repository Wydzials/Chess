package pl.wydzials.chess.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import pl.wydzials.chess.R;
import pl.wydzials.chess.engine.ChessEngine;

public class MainActivity extends AppCompatActivity {

    private static SharedPreferences preferences;
    private static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("preferences", 0);
        setAppLocale();
        instance = this;

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.menu_activity);


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

    private void setAppLocale() {
        String code = preferences.getString("language", "en");
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.setLocale(new Locale(code));
        resources.updateConfiguration(config, dm);
    }

    public static SharedPreferences getSharedPreferences() {
        return preferences;
    }

    public static void restart() {
        instance.recreate();
    }
}
