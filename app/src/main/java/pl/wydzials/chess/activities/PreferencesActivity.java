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

    Switch rotateSwitch;
    Switch lastMoveSwitch;
    Switch legalMovesSwitch;
    Spinner aiSpinner;
    Spinner languageSpinner;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.preferences_activity);

        preferences = MainActivity.getSharedPreferences();

        rotateSwitch = findViewById(R.id.rotate_black_pieces);
        lastMoveSwitch = findViewById(R.id.highlight_last_move);
        legalMovesSwitch = findViewById(R.id.highlight_legal_moves);
        aiSpinner = findViewById(R.id.ai_spinner);
        languageSpinner = findViewById(R.id.language_spinner);

        setSpinners();
        setListeners();

        rotateSwitch.setChecked(preferences.getBoolean("rotateBlackPieces", false));
        lastMoveSwitch.setChecked(preferences.getBoolean("highlightLastMove", true));
        legalMovesSwitch.setChecked(preferences.getBoolean("highlightLegalMoves", true));
    }

    private void setSpinners() {
        String[] depths = new String[]{getString(R.string.difficulty1), getString(R.string.difficulty2),
                getString(R.string.difficulty3), getString(R.string.difficulty4), getString(R.string.difficulty5)};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, depths);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aiSpinner.setAdapter(adapter1);
        aiSpinner.setSelection(preferences.getInt("aiDepth", 4) - 1);


        String[] languages = new String[]{"English", "Polski"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, languages);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter2);
        String language = preferences.getString("language", "en");
        if (language.equals("pl")) {
            languageSpinner.setSelection(1);
        } else {
            languageSpinner.setSelection(0);
        }
    }

    private void setListeners() {
        rotateSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("rotateBlackPieces", rotateSwitch.isChecked());
                editor.apply();
            }
        });

        lastMoveSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("highlightLastMove", lastMoveSwitch.isChecked());
                editor.apply();
            }
        });

        legalMovesSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("highlightLegalMoves", legalMovesSwitch.isChecked());
                editor.apply();
            }
        });

        aiSpinner.setOnItemSelectedListener(this);
        languageSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.ai_spinner) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("aiDepth", position + 1);
            editor.apply();
        } else {
            SharedPreferences.Editor editor = preferences.edit();
            if (position == 1) {
                editor.putString("language", "pl");
            } else {
                editor.putString("language", "en");
            }
            editor.apply();
            MainActivity.restart();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // do nothing
    }
}
