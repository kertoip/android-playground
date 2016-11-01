package pl.edu.pjwstk.s7367.smb1.shoppinglist;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.SortedList;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class OptionsActivity extends AppCompatActivity {

    public final static String SHARED_PREF_FONT_SIZE = "SHARED_PREF_FONT_SIZE"; //TODO to R.string??
    public final static String SHARED_PREF_IS_DARK_MODE = "SHARED_PREF_IS_DARK_MODE";
    public final static int DEFAULT_FONT_SIZE = 14;
    public final static String SHARED_PREF_NAME = "SHARED_PREF_NAME";

    private final static List<Integer> AVAILABLE_FONT_SIZES = new LinkedList<Integer>() {{
        add(10);
        add(14);
        add(18);
        add(22);
        add(26);
        add(30);
    }};

    SeekBar fontSeekBar;

    int fontSize;

    ToggleButton isDarkMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        fontSize = getSharedPreferences().getInt(SHARED_PREF_FONT_SIZE, DEFAULT_FONT_SIZE);

        fontSeekBar = (SeekBar) findViewById(R.id.fontSizeSeekBar);
        fontSeekBar.setProgress(AVAILABLE_FONT_SIZES.get(AVAILABLE_FONT_SIZES.indexOf(fontSize)));
        fontSeekBar.setMax(AVAILABLE_FONT_SIZES.size()-1);
        fontSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                System.out.println("progress: " + progress);
                fontSize = AVAILABLE_FONT_SIZES.get(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(SHARED_PREF_FONT_SIZE, fontSize);
                if (editor.commit()) {
                    Toast.makeText(getApplicationContext(), "ustawiono rozmiar trzcionki:" + fontSize, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Błąd!!!!:" + fontSize, Toast.LENGTH_LONG).show();
                };
            }
        });

        isDarkMode = (ToggleButton) findViewById(R.id.darkModeToggle);
        isDarkMode.setChecked(getSharedPreferences().getBoolean(SHARED_PREF_IS_DARK_MODE, false));
        isDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getSharedPreferences().edit().putBoolean(SHARED_PREF_IS_DARK_MODE, isChecked).commit();
            }
        });
    }

    private SharedPreferences getSharedPreferences() {
        return getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }
}
