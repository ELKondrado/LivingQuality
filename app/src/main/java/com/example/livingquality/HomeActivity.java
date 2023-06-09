package com.example.livingquality;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    private Button buttonHistory;
    private Button buttonLive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        declareFields();
        historyListener();
        liveListener();
    }

    public void declareFields(){
        buttonHistory = findViewById(R.id.qualityHistory);
        buttonLive = findViewById(R.id.qualityLive);
    }

    public void historyListener(){
        buttonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToHistoryActivity();
            }
        });
    }

    public void liveListener(){
        buttonLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLiveActivity();
            }
        });
    }

    public void goToHistoryActivity(){
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    public void goToLiveActivity(){
        Intent intent = new Intent(this, LiveActivity.class);
        startActivity(intent);
    }
}