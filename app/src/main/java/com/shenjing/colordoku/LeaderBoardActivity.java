package com.shenjing.colordoku;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LeaderBoardActivity extends AppCompatActivity {
    private TextView bestTimeEasy;
    private TextView bestTimeNormal;
    private TextView bestTimeHard;
    private TextView bestTimeFiendish;
    private TextView winsEasy;
    private TextView winsNormal;
    private TextView winsHard;
    private TextView winsFiendish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        SharedPreferences sharedPreferences = getSharedPreferences("leader board", MODE_PRIVATE);
        if (sharedPreferences.getBoolean("first run", true)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("first run", false);
            editor.putLong("best time easy", (59 * 60 + 59) * 1000);
            editor.putLong("best time normal", (59 * 60 + 59) * 1000);
            editor.putLong("best time hard", (59 * 60 + 59) * 1000);
            editor.putLong("best time fiendish", (59 * 60 + 59) * 1000);
            editor.putInt("wins easy", 0);
            editor.putInt("wins normal", 0);
            editor.putInt("wins hard", 0);
            editor.putInt("wins fiendish", 0);
            editor.apply();
        }
        bestTimeEasy = (TextView) findViewById(R.id.best_time_easy);
        bestTimeNormal = (TextView) findViewById(R.id.best_time_normal);
        bestTimeHard = (TextView) findViewById(R.id.best_time_hard);
        bestTimeFiendish = (TextView) findViewById(R.id.best_time_fiendish);
        winsEasy = (TextView) findViewById(R.id.wins_easy);
        winsNormal = (TextView) findViewById(R.id.wins_normal);
        winsHard = (TextView) findViewById(R.id.wins_hard);
        winsFiendish = (TextView) findViewById(R.id.wins_fiendish);

        long time = sharedPreferences.getLong("best time easy", (59 * 60 + 59) * 1000) / 1000;
        bestTimeEasy.setText(getResources().getString(R.string.best_time) + longTime2String(time));
        time = sharedPreferences.getLong("best time normal", (59 * 60 + 59) * 1000) / 1000;
        bestTimeNormal.setText(getResources().getString(R.string.best_time) + longTime2String(time));
        time = sharedPreferences.getLong("best time hard", (59 * 60 + 59) * 1000) / 1000;
        bestTimeHard.setText(getResources().getString(R.string.best_time) + longTime2String(time));
        time = sharedPreferences.getLong("best time fiendish", (59 * 60 + 59) * 1000) / 1000;
        bestTimeFiendish.setText(getResources().getString(R.string.best_time) + longTime2String(time));

        int wins = sharedPreferences.getInt("wins easy", 0);
        winsEasy.setText(getResources().getString(R.string.wins) + Integer.toString(wins));
        wins = sharedPreferences.getInt("wins normal", 0);
        winsNormal.setText(getResources().getString(R.string.wins) + Integer.toString(wins));
        wins = sharedPreferences.getInt("wins hard", 0);
        winsHard.setText(getResources().getString(R.string.wins) + Integer.toString(wins));
        wins = sharedPreferences.getInt("wins fiendish", 0);
        winsFiendish.setText(getResources().getString(R.string.wins) + Integer.toString(wins));
    }

    public static String longTime2String(long time) {
        long minute = time / 60;
        long second = time % 60;
        return Long.toString(minute) + ":" + Long.toString(second);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
