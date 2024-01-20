package com.shenjing.colordoku;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.shenjing.colordoku.util.SpHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_continue).setOnClickListener(this);
        findViewById(R.id.button_easy).setOnClickListener(this);
        findViewById(R.id.button_normal).setOnClickListener(this);
        findViewById(R.id.button_hard).setOnClickListener(this);
        findViewById(R.id.button_fiendish).setOnClickListener(this);
        findViewById(R.id.button_leader_board).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_leader_board) {
            Intent intent = new Intent(MainActivity.this, LeaderBoardActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        } else if (v.getId() == R.id.button_continue) {
            if (!SpHelper.INSTANCE.hasGameData()) {
                showNoGameDataDialog();
            } else {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("difficulty", 0);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        } else {
            if (SpHelper.INSTANCE.hasGameData()) {
                showGameDataOverrideWarningDialog(v.getId());
            } else {
                startGame(v.getId());
            }
        }
    }


    void showNoGameDataDialog() {
        new AlertDialog.Builder(this)
                .setMessage(getResources().getString(R.string.error_no_game_data))
                .setPositiveButton(R.string.btn_confirm, (dialog, which) -> {})
                .show();
    }

    void showGameDataOverrideWarningDialog(int viewId) {
        new AlertDialog.Builder(this)
                .setMessage(getResources().getString(R.string.waring_lost_game_data))
                .setNegativeButton(R.string.btn_cancel, (dialog, which) -> {
                })
                .setPositiveButton(R.string.btn_confirm, (dialog, which) -> startGame(viewId))
                .show();
    }

    void startGame(int viewId) {
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        if (viewId == R.id.button_easy) {
            intent.putExtra("difficulty", 1);
        } else if (viewId == R.id.button_normal) {
            intent.putExtra("difficulty", 2);
        } else if (viewId == R.id.button_hard) {
            intent.putExtra("difficulty", 3);
        } else if (viewId == R.id.button_fiendish) {
            intent.putExtra("difficulty", 4);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
}
