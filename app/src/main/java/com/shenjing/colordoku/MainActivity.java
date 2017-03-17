package com.shenjing.colordoku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

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
        if(v.getId() == R.id.button_leader_board){
            Intent intent = new Intent(MainActivity.this,LeaderBoardActivity.class);
            startActivity(intent);
        } else if(v.getId() == R.id.button_continue) {
            SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
            if(sharedPreferences.getLong("leavedTime",0)==0){
                Toast.makeText(MainActivity.this,getString(R.string.error_no_savedata),Toast.LENGTH_LONG).show();
            }else{
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("difficulty", 0);
                startActivity(intent);
            }

        }else {
            Intent intent = new Intent(MainActivity.this,GameActivity.class);
            switch (v.getId()){
                case R.id.button_easy :
                    intent.putExtra("difficulty",1);
                    break;
                case R.id.button_normal :
                    intent.putExtra("difficulty",2);
                    break;
                case R.id.button_hard :
                    intent.putExtra("difficulty",3);
                    break;
                case R.id.button_fiendish :
                    intent.putExtra("difficulty",4);
                    break;
            }
            startActivity(intent);
        }
        overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
    }

}
