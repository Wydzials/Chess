package pl.wydzials.chess.activities;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import pl.wydzials.chess.R;

public class PreferencesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    boolean stateRotateBlackPieces;
    Switch switchRotateBlackPieces;
    Spinner aiSpinner;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.preferences_activity);

        preferences = MainActivity.getSharedPreferences();

        switchRotateBlackPieces = findViewById(R.id.rotate_black_pieces);
        aiSpinner = findViewById(R.id.ai_spinner);

        setSpinner();
        setListeners();

        stateRotateBlackPieces = preferences.getBoolean("rotateBlackPieces", false);
        switchRotateBlackPieces.setChecked(stateRotateBlackPieces);
    }

    private void setSpinner() {
        String[] s = new String[]{"1 - too easy", "2 - easy", "3 - medium", "4 - hard", "5 - long thinking"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, s);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aiSpinner.setAdapter(adapter);
        aiSpinner.setSelection(preferences.getInt("aiDepth", 1) - 1);
    }

    private void setListeners() {
        switchRotateBlackPieces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateRotateBlackPieces = !stateRotateBlackPieces;
                switchRotateBlackPieces.setChecked(stateRotateBlackPieces);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("rotateBlackPieces", stateRotateBlackPieces);
                editor.apply();
            }
        });

        aiSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("aiDepth", position + 1);
        editor.apply();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
