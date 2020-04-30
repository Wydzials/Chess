package pl.wydzials.chess;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class PreferencesActivity extends AppCompatActivity {

    boolean stateRotateBlackPieces;
    Switch rotateBlackPieces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.preferences_activity);

        rotateBlackPieces = findViewById(R.id.rotate_black_pieces);

        final SharedPreferences preferences = MainActivity.getSharedPreferences();
        stateRotateBlackPieces = preferences.getBoolean("rotateBlackPieces", false);
        rotateBlackPieces.setChecked(stateRotateBlackPieces);

        rotateBlackPieces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateRotateBlackPieces = !stateRotateBlackPieces;
                rotateBlackPieces.setChecked(stateRotateBlackPieces);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("rotateBlackPieces", stateRotateBlackPieces);
                editor.apply();
            }
        });
    }
}
