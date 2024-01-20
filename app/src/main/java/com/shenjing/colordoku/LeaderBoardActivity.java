package com.shenjing.colordoku;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LeaderBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        SharedPreferences sharedPreferences = getSharedPreferences("leader board", MODE_PRIVATE);
        TextView bestTimeEasy       = findViewById(R.id.best_time_easy);
        TextView bestTimeNormal     = findViewById(R.id.best_time_normal);
        TextView bestTimeHard       = findViewById(R.id.best_time_hard);
        TextView bestTimeFiendish   = findViewById(R.id.best_time_fiendish);
        TextView winsEasy           = findViewById(R.id.wins_easy);
        TextView winsNormal         = findViewById(R.id.wins_normal);
        TextView winsHard           = findViewById(R.id.wins_hard);
        TextView winsFiendish       = findViewById(R.id.wins_fiendish);

        long time = sharedPreferences.getLong("best time easy", -1000) / 1000;
        bestTimeEasy.setText(getResources().getString(R.string.best_time, longTime2String(time)));
        time = sharedPreferences.getLong("best time normal", -1000) / 1000;
        bestTimeNormal.setText(getResources().getString(R.string.best_time, longTime2String(time)));
        time = sharedPreferences.getLong("best time hard", -1000) / 1000;
        bestTimeHard.setText(getResources().getString(R.string.best_time, longTime2String(time)));
        time = sharedPreferences.getLong("best time fiendish", -1000) / 1000;
        bestTimeFiendish.setText(getResources().getString(R.string.best_time, longTime2String(time)));

        int wins = sharedPreferences.getInt("wins easy", 0);
        winsEasy.setText(getResources().getString(R.string.wins, wins));
        wins = sharedPreferences.getInt("wins normal", 0);
        winsNormal.setText(getResources().getString(R.string.wins, wins));
        wins = sharedPreferences.getInt("wins hard", 0);
        winsHard.setText(getResources().getString(R.string.wins, wins));
        wins = sharedPreferences.getInt("wins fiendish", 0);
        winsFiendish.setText(getResources().getString(R.string.wins, wins));
    }

    public static String longTime2String(long time) {
        if (time == -1) {
            return "--:--";
        }
        long minute = time / 60;
        long second = time % 60;
        return minute + ":" + second;
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
