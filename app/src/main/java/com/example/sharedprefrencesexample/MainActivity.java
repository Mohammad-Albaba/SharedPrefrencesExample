package com.example.sharedprefrencesexample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_SETTINGS_CHANGE = 1;
    private final String PREF_GAME_DATA = "PREF_GAME_DATA";
    private final String BEST_SCORE = "PREF_BEST_SCORE";
    private final String CURRENT_SCORE = "PREF_CURRENT_SCORE";

    private TextView bestScoreTextView;
    private TextView currentScoreTextView;
    private Button playButton;

    private Random random;
    private int bestScore;
    private int currentScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bestScoreTextView=findViewById(R.id.text_view_best_score);
        currentScoreTextView=findViewById(R.id.text_view_current_score);
        playButton=findViewById(R.id.button_play);

        readUserPreferences();
        restoreFromSharedPreference();

        random = new Random();
        bestScoreTextView.setText(String.valueOf(bestScore));
        currentScoreTextView.setText(String.valueOf(currentScore));

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentScore = random.nextInt(1000);
                if (currentScore > bestScore){
                    bestScore = currentScore;
                    saveToSharedPreference();
                }
                currentScoreTextView.setText(String.valueOf(currentScore));
                bestScoreTextView.setText(String.valueOf(bestScore));

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings){
            startActivityForResult(new Intent(this, SettingsActivity.class), REQUEST_SETTINGS_CHANGE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_SCORE, currentScore);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentScore = savedInstanceState.getInt(CURRENT_SCORE, 0);
        currentScoreTextView.setText(String.valueOf(currentScore));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SETTINGS_CHANGE){
            if (resultCode == RESULT_OK){
                readUserPreferences();
            }
        }
    }

    // ***   عند الخروج من التطبيق أريد حفظ قيمة أفضل سكور وذلك من خلال SharedPreference ***
    private void saveToSharedPreference(){
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_GAME_DATA , MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putInt(BEST_SCORE , bestScore);
        editor.apply();
    }
    private void restoreFromSharedPreference() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_GAME_DATA , MODE_PRIVATE);
        if (sharedPreferences.contains(BEST_SCORE)) {
            bestScore = sharedPreferences.getInt(BEST_SCORE, 0);
        }}

        private void readUserPreferences(){
          SharedPreferences sharedPreferences =  PreferenceManager.getDefaultSharedPreferences(this);
          String fontSize = sharedPreferences.getString(getString(R.string.key_font_size), getString(R.string.font_default_value));
          currentScoreTextView.setTextSize(Integer.parseInt(fontSize));
          boolean isNight = sharedPreferences.getBoolean(getString(R.string.key_night_mode),getResources().getBoolean(R.bool.night_mode_default));
          if (isNight){
              AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
          }else {
              AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

          }
    }

}