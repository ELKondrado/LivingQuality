package com.example.livingquality;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LiveActivity extends AppCompatActivity {
    private Button backButton;
    private ProgressBar progressBar1;
    private ProgressBar progressBar2;
    private ProgressBar progressBar3;
    private ProgressBar progressBar4;
    private TextView humidityVal;
    private TextView lightVal;
    private TextView airVal;
    private TextView soundVal;
    private TextView scoreVal;
    private int humidity;
    private int air;
    private int light;
    private int sound;

    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);

        backButton = findViewById(R.id.backButtonLive);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToHomeActivity();
            }
        });

        progressBar1 = findViewById(R.id.progressBar1);
        progressBar2 = findViewById(R.id.progressBar2);
        progressBar3 = findViewById(R.id.progressBar3);
        progressBar4 = findViewById(R.id.progressBar4);

        humidityVal = findViewById(R.id.humidityVal);
        lightVal = findViewById(R.id.lightVal);
        airVal = findViewById(R.id.airVal);
        soundVal = findViewById(R.id.soundVal);
        scoreVal = findViewById(R.id.scoreVal);

        reference = FirebaseDatabase.getInstance().getReference().child("LiveInformation");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Information info = dataSnapshot.getValue(Information.class);
                humidity = info.getHumidity();
                light = info.getLight();
                air = info.getAir();
                sound = info.getSound();

                progressBar2.setProgress(humidity*10);
                progressBar3.setProgress(light*10);
                progressBar1.setProgress(air*10);
                progressBar4.setProgress(sound*10);

                humidityVal.setText(humidity+"");
                lightVal.setText(light+"");
                airVal.setText(air+"");
                soundVal.setText(sound+"");

                int score = (humidity + light + air + sound)*100/30;
                scoreVal.setText(score+"%");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void goToHomeActivity(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}